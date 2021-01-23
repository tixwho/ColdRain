package ncm;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import exception.ColdRainException;
import exception.DatabaseException;
import exception.ErrorCodes;
import ncm.jsonSupp.PlayListBean;
import ncm.models.NcmAudioInfoComp;
import ncm.models.NcmPlaylistComp;
import ncm.utils.NcmPropertiesUtil;

public class NcmService {

    private static final Logger logger = LoggerFactory.getLogger(NcmService.class);
    private static Connection conn = null;
    private static Statement stmt = null;

    public void test() throws ColdRainException {
        String propStr =
            "E:\\lzx\\Discovery\\NeteaseMusicDBExport-master\\NeteaseMusicDBExport-master\\ncm.properties";
        NcmPropertiesUtil.readNcmProperties(new File(propStr));
        initDB();
        List<PlayListBean> plListBeans = getPlaylists();
        for (PlayListBean bean : plListBeans) {
            NcmPlaylistComp aComp = collectAPlaylistInfo(bean);
            System.out.println(
                "PlaylistName:" + aComp.getPlaylistName() + ";id:" + aComp.getPlaylist_id());
            System.out.println("size:" + aComp.getPlaylistRecordList().size());
        }
        closeDB();

    }


    private NcmPlaylistComp collectAPlaylistInfo(PlayListBean plBean) {
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
            // should not happen if ncm does not change its database structure
            logger.warn("NCM query failed in selecting playlist.");
            return null;
        }
        return rtComp;
    }

    private List<PlayListBean> getPlaylists() {
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery("SELECT playlist FROM web_playlist;");
            List<PlayListBean> pids = new ArrayList<PlayListBean>();
            Gson gson = new Gson();
            while (rs.next()) {
                pids.add(gson.fromJson(rs.getString(1), PlayListBean.class));
            }
            return pids;
        } catch (SQLException se) {
            // should not happen if ncm does not change its database structure
            logger.warn("NCM query failed in selecting playlist.");
            return null;
        }
    }

    private void initDB() throws ColdRainException {
        // first test if ncm is initialized
        if (NcmPropertiesUtil.ncmCacheDirPath.isBlank()
            || NcmPropertiesUtil.ncmMusicDirPath.isBlank()) {
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
                .getConnection("jdbc:sqlite:" + NcmPropertiesUtil.ncmCacheDirPath + "/webdb.dat");
            stmt = conn.createStatement();
            logger.info("NCM sqlite database connected!");
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
            logger.warn("Failed Closing ncm sql connection!");
            throw new DatabaseException("Sqlite driver connection failed!", sqle,
                ErrorCodes.DATABASE_DRIVER_FAILED);
        }
    }


}

