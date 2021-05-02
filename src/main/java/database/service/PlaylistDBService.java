package database.service;

import database.models.PlaylistModel;
import database.models.PlaylistRecordModel;
import database.utils.DbHelper;
import database.utils.InitSessionFactory;
import exception.DatabaseException;
import exception.ErrorCodes;
import exception.NativeReflectionException;
import exception.PlaylistIOException;
import org.apache.commons.io.FileUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import playlist.generic.AbstractPlaylistSong;
import playlist.generic.AbstractPlaylistTable;
import playlist.generic.PlaylistFileIO;
import playlist.generic.SupportedPlaylistFormat;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class PlaylistDBService {
    private static final Logger logger = LoggerFactory.getLogger(PlaylistDBService.class);


    public static void fullScanPlaylistFiles(File inDir) throws DatabaseException {


        // check this is a directory
        if (!inDir.isDirectory()) {
            throw new DatabaseException("Not a Directory to apply fullScan",
                ErrorCodes.BASE_IO_ERROR);
        }

        // filter audiofiles with SuffixFileFilter (from commonsIO)
        String[] acceptedPlaylistFormat = SupportedPlaylistFormat.getSupportedPlaylistArray();
        Collection<File> allPlaylists = FileUtils.listFiles(inDir, acceptedPlaylistFormat, true);


        // try find file in database; if not found, insert;
        for (File playlist : allPlaylists) {
            AbstractPlaylistTable theTable;
            try {
                theTable = PlaylistFileIO.readPlaylist(playlist);
                loadOrUpdatePlaylistFile(theTable);
            } catch (PlaylistIOException | NativeReflectionException e) {
                // should not happen for a fine playlist
                continue;
            }


        }
    }


    public static PlaylistModel loadOrUpdatePlaylistFile(AbstractPlaylistTable unknownTable) {
        PlaylistModel playlistM;
        try {
            playlistM = PlaylistModel.findPlaylistModel(unknownTable.getPlaylistSrc());
        } catch (DatabaseException e) {
            // does not find playlistModel in database
            logger.info("Creating new Playlist Record!");
            playlistM = PlaylistModel.guaranteePlaylistModel(unknownTable);
            playlistM = createAllRecords(playlistM, unknownTable);
            return playlistM;
        }
        // update if timestamp is different
        if (playlistM.getLastModified() != DbHelper
            .calcLastModTimestamp(unknownTable.getPlaylistSrc())) {
            playlistM = createAllRecords(playlistM, unknownTable);
            playlistM.updateTimeStamp();
        } else {
            logger.info("No need to update Playlist Record");
        }
        return playlistM;

    }

    private static PlaylistModel createAllRecords(PlaylistModel playlistM,
        AbstractPlaylistTable unknownTable) {
        logger.info("Rescanning Playlist Records");
        deleteAllRecordForPlaylist(playlistM);
        ArrayList<AbstractPlaylistSong> songArrList = unknownTable.getSongArrList();
        int orderCount = 0;
        for (AbstractPlaylistSong eachSong : songArrList) {
            orderCount += 1;
            PlaylistRecordModel recordM =
                PlaylistRecordModel.createPlaylistRecordModel(orderCount, eachSong);
            recordM.attachPlaylistModel(playlistM);
        }
        return playlistM;
    }


    public static PlaylistModel deleteAllRecordForPlaylist(PlaylistModel playlistModel) {
        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        @SuppressWarnings({"rawtypes"})
        Query q = session.createQuery("delete from PlaylistRecordModel p where p.playlistM=?1");
        q.setParameter(1, playlistModel);
        q.executeUpdate();
        playlistModel.getPlaylistRecordModels().clear();
        session.saveOrUpdate(playlistModel);
        tx.commit();
        session.close();
        logger.info("DELETED ALL RECORDS!");
        return playlistModel;
    }

}
