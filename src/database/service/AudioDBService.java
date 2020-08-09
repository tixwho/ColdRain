package database.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import org.apache.commons.io.FileUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import database.models.AlbumModel;
import database.models.ArtistModel;
import database.models.FileModel;
import database.models.MetaModel;
import database.models.SongModel;
import database.utils.DbHelper;
import database.utils.InitSessionFactory;
import exception.DatabaseException;
import exception.ErrorCodes;
import exception.MetaIOException;
import local.generic.MetaSong;
import local.generic.SupportedAudioFormat;

public class AudioDBService {
    public static Logger logger = LoggerFactory.getLogger(AudioDBService.class);
    public static HashSet<ArtistModel> toValidateArtists;
    public static HashSet<AlbumModel> toValidateAlbums;
    public static HashSet<SongModel> toValidateSongs;


    public static void fullScanAudioFiles(File inDir) throws DatabaseException {


        // check this is a directory
        if (!inDir.isDirectory()) {
            throw new DatabaseException("Not a Directory to apply fullScan",
                ErrorCodes.BASE_IO_ERROR);
        }

        // filter audiofiles with SuffixFileFilter (from commonsIO)
        String[] acceptedAudioFormat = SupportedAudioFormat.getSupportedAudioArray();
        Collection<File> allAudios= FileUtils.listFiles(inDir, acceptedAudioFormat, true);

        
        //try find file in database; if not found, insert;
        for (File audio : allAudios) {
            String audioStr = audio.getAbsolutePath();
            MetaSong theMeta;
            try {
                theMeta = new MetaSong(audioStr);
            } catch (MetaIOException me) {
                // should not happen if all audiofile is valid
                continue;
            }
            try {
                 FileModel.findFileModel(audioStr);
            } catch (DatabaseException de) {
                loadNewFile(theMeta);
            }
            //update file
            updateMetaForFile(theMeta);
        }
    }


    public static FileModel loadNewFile(MetaSong meta) {
        FileModel fileM = FileModel.createFileModel(meta);
        MetaModel metaM = MetaModel.guaranteeMetaModel(meta);
        fileM.attachMetaModel(metaM);
        return fileM;
    }


    public static FileModel updateMetaForFile(MetaSong meta) throws DatabaseException {
        initializeValidationSets();
        FileModel reFileModel = FileModel.findFileModel(meta);
        // check timeStamp
        long timestampNow = DbHelper.calcLastModTimestamp(meta);
        if (timestampNow != reFileModel.getLastModified()) {
            logger.info("Start Updating new Meta for File " + reFileModel.getSrc() + "("
                + reFileModel.getFileid() + ")");

            // get MetaModel
            MetaModel toHandleMetaM = reFileModel.getMetaM();
            logger.trace("MetaModel Info Before:" + toHandleMetaM.toString());

            // if only one file use this meta, directly modify current MetaModel
            if (toHandleMetaM.getFileModels().size() == 1) {
                logger.warn("Only a single file use this meta.");


                if (MetaModel.checkMetaCount(meta) == 0) {
                    logger.info("Single File&New Meta, directly modify current meta.");
                    logger.warn("toHandleMetaM" + toHandleMetaM.toString());
                    // no current meta exist for the file & only one file share this Meta, update
                    // check album
                    AlbumModel toCheckAlbumM = AlbumModel.guaranteeAlbumModel(meta);
                    boolean setNewAlbum = false;
                    if (!toHandleMetaM.getAlbumM().equals(toCheckAlbumM)) {
                        logger.warn("toHandle's AlbumM:" + toHandleMetaM.getAlbumM().toString());
                        logger.warn("toReplace AlbumM:" + toCheckAlbumM.toString());
                        logger.warn("current size of metaModels in albumM:"
                            + toHandleMetaM.getAlbumM().getMetaModels().size());
                        logger.warn("contains?"
                            + toHandleMetaM.getAlbumM().getMetaModels().contains(toHandleMetaM));
                        toHandleMetaM.getAlbumM().getMetaModels().remove(toHandleMetaM);
                        logger.warn("current size of metaModels in albumM:"
                            + toHandleMetaM.getAlbumM().getMetaModels().size());
                        toValidateAlbums.add(toHandleMetaM.getAlbumM());
                        // set later to avoid different hashcode;
                        setNewAlbum = true;
                    }
                    SongModel toCheckSongM = SongModel.guaranteeSongModel(meta);
                    boolean setNewSong = false;
                    if (!toHandleMetaM.getSongM().equals(toCheckSongM)) {
                        logger.warn("current size of metaModels in songM:"
                            + toHandleMetaM.getSongM().getMetaModels().size());
                        logger.warn("contains?"
                            + toHandleMetaM.getSongM().getMetaModels().contains(toHandleMetaM));
                        toHandleMetaM.getSongM().getMetaModels().remove(toHandleMetaM);
                        logger.warn("current size of metaModels in songM:"
                            + toHandleMetaM.getSongM().getMetaModels().size());
                        toValidateSongs.add(toHandleMetaM.getSongM());
                        // set later to avoid different hashcode;
                        setNewSong = true;

                    }
                    if (setNewAlbum) {
                        toHandleMetaM.setAlbumM(toCheckAlbumM);
                    }
                    if (setNewSong) {
                        toHandleMetaM.setSongM(toCheckSongM);
                    }

                    String toCheckDiscNo = meta.getDiscNo();
                    if (toHandleMetaM.getDiscNo() != toCheckDiscNo) {
                        toHandleMetaM.setDiscNo(toCheckDiscNo);
                    }
                    String toCheckTrackNo = meta.getTrackNo();
                    if (toHandleMetaM.getTrackNo() != toCheckTrackNo) {
                        toHandleMetaM.setTrackNo(toCheckTrackNo);
                    }
                    // attach to file
                    Session session = InitSessionFactory.getNewSession();
                    Transaction tx = session.beginTransaction();
                    session.saveOrUpdate(toHandleMetaM);
                    tx.commit();
                    session.close();
                    logger.info("Updated MetaModel!");
                    logger.warn("MetaModel Info Now:" + toHandleMetaM.toString());
                } else {
                    MetaModel toCompareMetaM = MetaModel.guaranteeMetaModel(meta);
                    logger.info(
                        "Single File& Current Meta: Using current Meta and try to delete old.");
                    // delete first since new relation will make collection unable to find FileModel
                    logger.warn("Contains reFileModel?"
                        + toHandleMetaM.getFileModels().contains(reFileModel));
                    toHandleMetaM.getFileModels().remove(reFileModel);
                    // only a single file and a current meta exist, attach new meta & delete
                    // set new relation for File
                    reFileModel.attachMetaModel(toCompareMetaM);
                    // set artist and album in current MetaModel to deleteTest
                    toHandleMetaM.getAlbumM().getMetaModels().remove(toHandleMetaM);
                    toValidateAlbums.add(toHandleMetaM.getAlbumM());
                    toHandleMetaM.getSongM().getMetaModels().remove(toHandleMetaM);
                    toValidateSongs.add(toHandleMetaM.getSongM());

                    // delete if no other files use this meta
                    if (toHandleMetaM.getFileModels().size() == 0) {
                        Session session = InitSessionFactory.getNewSession();
                        Transaction tx = session.beginTransaction();
                        session.delete(toHandleMetaM);
                        tx.commit();
                        session.close();
                        logger.info("Deleted unattached MetaModel!");
                    } else {
                        // debugging
                        Iterator<FileModel> it = toHandleMetaM.getFileModels().iterator();
                        while (it.hasNext()) {
                            FileModel fl = it.next();
                            logger.warn("FL:" + fl.toString());
                        }

                    }

                }
            } else {
                // multiple files share same meta.create/retrieve new meta and attach
                logger.warn("Multiple Files use this meta");
                // delink original Meta
                toHandleMetaM.getFileModels().remove(reFileModel);
                // find new meta to attach
                MetaModel toAttachNewMeta = MetaModel.guaranteeMetaModel(meta);
                // updated to database
                reFileModel.attachMetaModel(toAttachNewMeta);



            }
            reFileModel.updateTimeStamp();

        } else {
            logger.info("No need to update");
        }
        return reFileModel;
    }

    private static void initializeValidationSets() {
        if (toValidateAlbums == null) {
            toValidateAlbums = new HashSet<AlbumModel>();
        }
        if (toValidateSongs == null) {
            toValidateSongs = new HashSet<SongModel>();
        }
        if (toValidateArtists == null) {
            toValidateArtists = new HashSet<ArtistModel>();
        }
    }

    public static void cleanValidationSets() {
        logger.warn("Start Cleaning up!");
        if (toValidateAlbums != null) {
            logger.warn("Validating Albums!");
            for (AlbumModel toValidateAlbumM : toValidateAlbums) {
                if (toValidateAlbumM.getMetaModels().size() == 0) {
                    logger.warn("Cleaning Album:" + toValidateAlbumM.toString());
                    toValidateAlbumM.getAlbumArtistM().getAlbumModels().remove(toValidateAlbumM);
                    toValidateArtists.add(toValidateAlbumM.getAlbumArtistM());
                    Session session = InitSessionFactory.getNewSession();
                    Transaction tx = session.beginTransaction();
                    session.delete(toValidateAlbumM);
                    tx.commit();
                    session.close();
                    logger.info("Deleted AlbumModel!");
                }
            }
            toValidateAlbums.clear();
        }
        if (toValidateSongs != null) {
            logger.warn("Validating Songs!");
            for (SongModel toValidateSongM : toValidateSongs) {
                if (toValidateSongM.getMetaModels().size() == 0) {
                    logger.warn("Cleaning Song:" + toValidateSongM.toString());
                    toValidateSongM.getArtistM().getSongModels().remove(toValidateSongM);
                    toValidateArtists.add(toValidateSongM.getArtistM());
                    Session session = InitSessionFactory.getNewSession();
                    Transaction tx = session.beginTransaction();
                    session.delete(toValidateSongM);
                    tx.commit();
                    session.close();
                    logger.info("Deleted SongModel!");
                }
            }
            toValidateSongs.clear();
        }
        if (toValidateArtists != null) {
            logger.warn("Validating Artists!");
            for (ArtistModel toValidateArtistM : toValidateArtists) {
                boolean noArtist = false;
                boolean noAlbumArtist = false;
                if (toValidateArtistM.getSongModels().size() == 0) {
                    logger.warn("No Song use Artist:" + toValidateArtistM.toString());
                    noArtist = true;
                }
                if (toValidateArtistM.getAlbumModels().size() == 0) {
                    logger.warn("No Album use Artist:" + toValidateArtistM.toString());
                    noAlbumArtist = true;
                }

                if (noArtist && noAlbumArtist) {
                    logger.warn("Cleaning Artist:" + toValidateArtistM.toString());
                    Session session = InitSessionFactory.getNewSession();
                    Transaction tx = session.beginTransaction();
                    session.delete(toValidateArtistM);
                    tx.commit();
                    session.close();
                    logger.info("Deleted ArtistModel!");
                }
            }
            toValidateArtists.clear();
        }
    }

}
