package com.coldrain.ncm;

import static com.coldrain.ncm.utils.NcmPropertiesUtil.ncmCacheDirPath;
import static com.coldrain.ncm.utils.NcmPropertiesUtil.ncmMusicDirPath;

import com.coldrain.exception.ColdRainException;
import com.coldrain.exception.DatabaseException;
import com.coldrain.exception.ErrorCodes;
import com.coldrain.exception.MetaIOException;
import com.coldrain.ncm.annotations.NcmTask;
import com.coldrain.ncm.jsonSupp.JsonRootBean;
import com.coldrain.ncm.jsonSupp.OfflineRootBean;
import com.coldrain.ncm.jsonSupp.PlayListBean;
import com.coldrain.ncm.models.NcmAudioInfoComp;
import com.coldrain.ncm.models.NcmNegatedAudioInfo;
import com.coldrain.ncm.models.NcmPlaylistComp;
import com.coldrain.ncm.utils.NcmConfig;
import com.coldrain.ncm.utils.NcmPropertiesUtil;
import com.coldrain.ncm.utils.NcmTempFileUtil;
import com.coldrain.playlist.generic.MetaSong;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class NcmService {

    private static final Logger logger = LoggerFactory.getLogger(NcmService.class);
    private static Connection conn = null;
    private static Statement stmt = null;

    @Qualifier("ncmJdbcTemplate")
    @Autowired
    JdbcTemplate ncmJdbcTemplate;
    
    @Autowired
    NcmConfig ncmConfig;

    @NcmTask("TestRead")
    public void test() throws ColdRainException {
        String propStr =
            "E:\\lzx\\Discovery\\NeteaseMusicDBExport-master\\NeteaseMusicDBExport-master\\ncm.properties";
        NcmPropertiesUtil.readNcmProperties(new File(propStr));
        initDB();
        try {
            List<PlayListBean> plListBeans = getPlaylists();
            assert plListBeans != null;
            for (PlayListBean bean : plListBeans) {
                NcmPlaylistComp aComp = collectAPlaylistInfo_webTrack(bean);
                assert aComp != null;
                System.out.println(
                    "PlaylistName:" + aComp.getPlaylistName() + ";id:" + aComp.getPlaylist_id());
                System.out.println("size:" + aComp.getPlaylistRecordList().size());
            }
        } finally {
            closeDB();
        }

    }

    public void optimizeOfflineDJCoverArts() throws ColdRainException {
        List<NcmAudioInfoComp> offlineDjProgramList;
        try {
            initDB();
            offlineDjProgramList = collectAllDJProgram_offlineOnly();
        }finally{
            closeDB();
        }
        for (NcmAudioInfoComp aComp : offlineDjProgramList) {
            if (aComp.getDj_coverUrl() == null) {
                continue;
            }
            updateDJAlbumArt(aComp);
        }

    }

    // todo
    // also join web_track first, and if not found in com.coldrain.playlist table, try to concat com.coldrain.old files in search
    // of original (not copyrighted) files.

    private NcmPlaylistComp collectAPlaylistInfo_webTrack(PlayListBean plBean) {
        NcmPlaylistComp rtComp = new NcmPlaylistComp();
        rtComp.setPlaylistName(plBean.getName());
        rtComp.setPlaylist_id(plBean.getId());
        String queryStr =
            "SELECT w_pl_track.tid,w_pl_track.\"order\", track, relative_path, type_extra, real_suffix, file_type\r\n"
                + "FROM web_playlist_track AS w_pl_track\r\n"
                + "LEFT OUTER JOIN web_track AS w_track ON w_pl_track.tid = w_track.tid\r\n"
                + "LEFT OUTER JOIN web_offline_track AS w_off_track ON w_pl_track.tid = w_off_track.track_id\r\n"
                + "WHERE w_pl_track.pid=\"" + plBean.getId() + "\"";
        ResultSet rs;
        Gson gson = new Gson();
        try {
            rs = stmt.executeQuery(queryStr);
            while (rs.next()) {
                // json from webtrack:track could be null if the song is negated.
                if (rs.getString("track")==null) {
                    NcmNegatedAudioInfo negatedAudio = new NcmNegatedAudioInfo(rs.getLong("tid"),
                        plBean.getId(), plBean.getName(), rs.getInt("order"));
                    logger.info("Found a negated audioRecord:"+negatedAudio);
                    rtComp.getNegatedPlaylistRecordList().add(negatedAudio);
                    continue;
                }
                // setup json, tid, title, album
                NcmAudioInfoComp aComp =
                    new NcmAudioInfoComp(gson.fromJson(rs.getString("track"), JsonRootBean.class));
                // check if it's downloaded; if it isn't, add to the list and jump to next loop
                if (rs.getString("relative_path")==null) {
                    aComp.setDownloaded_flag(false);
                    rtComp.getPlaylistRecordList().add(aComp);
                    continue;
                }
                aComp.setDownloaded_flag(true); // else, set true
                aComp.setRelative_path(rs.getString("relative_path"));
                aComp.setReal_suffix(rs.getString("real_suffix"));
                aComp.setNcm_flag(rs.getBoolean("file_type"));
                // check if it's an DJ track. If it is, set the status to true
                // NCM cannot add djtrack to com.coldrain.playlist currently, so it should be false always.
                if (Long.valueOf(rs.getLong("type_extra")) != null) {
                    aComp.setIs_DJ(true);
                }
                aComp.setIs_DJ(false);
                rtComp.getPlaylistRecordList().add(aComp);
            }
        } catch (SQLException se) {
            // should not happen if com.coldrain.ncm does not change its com.coldrain.database structure
            logger.warn("NCM query failed in selecting com.coldrain.playlist.");
            return null;
        }
        return rtComp;
    }


    //prototype, used a different join logic.
    @SuppressWarnings("unused")
    private NcmPlaylistComp collectAPlaylistInfo_offlineOnly(PlayListBean plBean) {
        NcmPlaylistComp rtComp = new NcmPlaylistComp();
        rtComp.setPlaylistName(plBean.getName());
        rtComp.setPlaylist_id(plBean.getId());
        String queryStr = "SELECT track_id, relative_path, track_name, real_suffix, file_type\r\n"
            + "FROM web_offline_track\r\n"
            + "LEFT OUTER JOIN web_playlist_track ON web_offline_track.track_id=web_playlist_track.tid\r\n"
            + "WHERE pid=\"" + plBean.getId() + "\"";
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(queryStr);
            while (rs.next()) {
                NcmAudioInfoComp aComp = new NcmAudioInfoComp();
                aComp.setTrack_id(rs.getLong("track_id"));
                aComp.setRelative_path(rs.getString("relative_path"));
                aComp.setReal_suffix(rs.getString("real_suffix"));
                aComp.setNcm_flag(rs.getBoolean("file_type"));
                rtComp.getPlaylistRecordList().add(aComp);
            }
        } catch (SQLException se) {
            // should not happen if com.coldrain.ncm does not change its com.coldrain.database structure
            logger.warn("NCM query failed in selecting com.coldrain.playlist.");
            return null;
        }
        return rtComp;
    }
    
    private List<NcmAudioInfoComp> collectAllDJProgram_offlineOnly(){
        
        String queryStr = "SELECT track_name, relative_path, detail\r\n"
            + "FROM web_offline_track\r\n"
            + "WHERE type_extra NOT NULL";
        ResultSet rs = null;
        Gson gson = new Gson();
        List<NcmAudioInfoComp> offlineDJList = new ArrayList<>();
        try {
            rs = stmt.executeQuery(queryStr);
            while(rs.next()) {
                NcmAudioInfoComp aComp = new NcmAudioInfoComp(gson.fromJson(rs.getString("detail"), OfflineRootBean.class));
                aComp.setRelative_path(rs.getString("relative_path"));
                offlineDJList.add(aComp);
            }
        } catch (SQLException se) {
            // should not happen if com.coldrain.ncm does not change its com.coldrain.database structure
            logger.warn("NCM query failed in selecting offline DJ program.");
            return null;
        }
        return offlineDJList;
    }

    private List<PlayListBean> getPlaylists() {
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("SELECT playlist FROM web_playlist;");
            List<PlayListBean> pids = new ArrayList<PlayListBean>();
            Gson gson = new Gson();
            while (rs.next()) {
                PlayListBean plb = gson.fromJson(rs.getString(1), PlayListBean.class);
                pids.add(plb);
            }
            return pids;
        } catch (SQLException se) {
            // should not happen if com.coldrain.ncm does not change its com.coldrain.database structure
            logger.warn("NCM query failed in selecting com.coldrain.playlist.");
            return null;
        }
    }

    @Transactional(transactionManager = "ncmTxManager")
    public List<PlayListBean> getPlaylists_publicTest(){
        Gson gson = new Gson();
        String sql = "SELECT playlist FROM web_playlist;";
        List<PlayListBean> playlistBeans = this.ncmJdbcTemplate.query(sql,
            new RowMapper<PlayListBean>() {
                @Override
                public PlayListBean mapRow(ResultSet resultSet, int i) throws SQLException {
                    PlayListBean plb = gson.fromJson(resultSet.getString(1),PlayListBean.class);
                    return plb;
                }
            });
        return playlistBeans;
    }

    private void updateDJAlbumArt(NcmAudioInfoComp toModifyDJProgram){
        if(!toModifyDJProgram.isIs_DJ()||toModifyDJProgram.getDj_coverUrl()==null){
            //not a DJ program or no coverUrl
            return;
        }
        try {
            File tempImgFile = NcmTempFileUtil.cacheDJImg(toModifyDJProgram);

            //if no image detected, skip
            if(tempImgFile==null){
                return;
            }
            logger.info("Cached DJImg for "+toModifyDJProgram.getTitle()+"("+toModifyDJProgram.getTrack_id()+")");
            MetaSong djProgramAudioFileMeta = new MetaSong(new File(ncmMusicDirPath,toModifyDJProgram.getRelative_path()));
            djProgramAudioFileMeta.forceUpdateAlbumArt(tempImgFile);
            logger.info("Updated DJImg for "+toModifyDJProgram.getTitle()+"("+toModifyDJProgram.getTrack_id()+")");
            tempImgFile.delete(); //clean up tempFile

        } catch (IOException e) {
            logger.warn("Failure caching DJProgram imageUrl:"+toModifyDJProgram.getRelative_path());
            e.printStackTrace();

        } catch (MetaIOException e) {
            logger.warn("Unable to create MetaSong for DJProgram:"+toModifyDJProgram.getRelative_path());
        }


    }

    private void initDB() throws ColdRainException {
        // first com.coldrain.test if com.coldrain.ncm is initialized
        if ((ncmCacheDirPath==null||ncmCacheDirPath.isEmpty())
            || (ncmMusicDirPath==null||ncmMusicDirPath.isEmpty())) {
            logger.warn("DB initialization cancelled. Plz initialize NCM properties first.");
            throw new ColdRainException(
                "Using NCM functions without initializing related properties",
                ErrorCodes.CLOUDMUSIC_NOT_INITIALIZED);
        }
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ce) {
            logger.warn("cannot found sqlite driver!");
            throw new DatabaseException("Sqlite driver intialization failed!",
                ErrorCodes.DATABASE_DRIVER_FAILED);
        }

        try {
            conn = DriverManager
                .getConnection("jdbc:sqlite:" + ncmCacheDirPath + "/webdb.dat");
            stmt = conn.createStatement();
            logger.info("NCM sqlite com.coldrain.database connected!");
        } catch (SQLException se) {
            logger.warn("sqlite connection failed!");
            throw new DatabaseException("Sqlite driver connection failed!", se,
                ErrorCodes.DATABASE_DRIVER_FAILED);
        }

    }

    private void closeDB() throws DatabaseException {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException sqle) {
            logger.warn("Failed Closing com.coldrain.ncm sql connection!");
            throw new DatabaseException("Sqlite driver connection failed!", sqle,
                ErrorCodes.DATABASE_DRIVER_FAILED);
        }
        logger.info("NCM sqlite com.coldrain.database closed!");
    }


}

