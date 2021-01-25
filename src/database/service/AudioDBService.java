package database.service;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
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
import playlist.generic.MetaSong;
import playlist.generic.SupportedAudioFormat;
import toolkit.AudioMd5Helper;

public class AudioDBService {
    private static final Logger logger = LoggerFactory.getLogger(AudioDBService.class);
    public static HashSet<ArtistModel> toValidateArtists = new HashSet<ArtistModel>();
    public static HashSet<AlbumModel> toValidateAlbums = new HashSet<AlbumModel>();
    public static HashSet<SongModel> toValidateSongs = new HashSet<SongModel>();

    /* Functional Methods Below */

    // Called Once ColdRain Launched. Requires considerable database I/O resource.
    /**
     *  Load new file, modify any changed, and delete missing. 
     * @param inDir Directory to be scanned for audioFiles.
     * @throws DatabaseException When it is not a directory
     */
    public void fullScanAudioFiles(File inDir) throws DatabaseException {


        // check this is a directory
        if (!inDir.isDirectory()) {
            throw new DatabaseException("Not a Directory to apply fullScan",
                ErrorCodes.BASE_IO_ERROR);
        }

        // filter audiofiles with SuffixFileFilter (from commonsIO)
        String[] acceptedAudioFormat = SupportedAudioFormat.getSupportedAudioArray();
        Collection<File> allAudios = FileUtils.listFiles(inDir, acceptedAudioFormat, true);


        // try find file in database; if not found, insert;
        for (File audio : allAudios) {
            String audioStr = audio.getAbsolutePath();
            MetaSong theMeta;
            try {
                theMeta = new MetaSong(audioStr);
            } catch (MetaIOException me) {
                // should not happen if all audiofile is valid
                continue;
            }

            if (FileModel.findFileModel(audioStr, false) == null) {
                // load a brand new file with possibly existing metadata
                loadNewFile(theMeta);
            }
            // update file if timestamp later.
            updateMetaForFile(theMeta);
        }

        // finally delete unused fileM once
        fullFileModelCleanse();
        //validation service-level clean up
        cleanValidationSets(); 
    }


    /**
     * Standard Procedure for loading a new audioFile.
     * Create a FileM and guarantee a MetaM for it, attached.
     * @param meta MetaSong instance to be loaded.
     * @return fileM, attached with functional MetaM.
     */
    public static FileModel loadNewFile(MetaSong meta) {
        FileModel fileM = FileModel.createFileModel(meta);
        MetaModel metaM = MetaModel.guaranteeMetaModel(meta);
        fileM.attachMetaModel(metaM);
        return fileM;
    }

    /**
     * Standard Procedure for Deleting a fileM from database. Automatic clean-up.
     * If it's the only file that shares corresponding metaM, metaM will also be disposed.
     * @param meta MetaSong instance to be disposed.
     * @return status
     */
    public static boolean deleteFileFromDB(MetaSong meta) {
        try {
            FileModel fileM = FileModel.findFileModel(meta);
            return deleteFileFromDB(fileM,true);
        } catch (DatabaseException de) {
            // try to delete a fileM not within database, ignore
            logger.warn("Tried to discard an fileM not within databse!", de);
        }
        return false;
    }
    
    /**
     * Standard Procedure for Deleting a fileM from database. Automatic clean-up.
     * If it's the only file that shares corresponding metaM, metaM will also be disposed.
     * @param fileM fileM to be disposed
     * @return  status. always true in this case.
     */
    public static boolean deleteFileFromDB(FileModel fileM) {
        return deleteFileFromDB(fileM,true);
    }

    /**
     * Standard Procedure for Deleting a fileM from database.
     * If it's the only file that shares corresponding metaM, metaM will also be disposed.
     * @param fileM fileM to be disposed
     * @param autoCleanFlag in-class, service level use can set false for performance
     * @return  status. always true in this case.
     */
    private static boolean deleteFileFromDB(FileModel fileM, boolean autoCleanFlag) {
        fileM.getMetaM().getFileModels().remove(fileM);
        if (fileM.getMetaM().getFileModels().size() == 0) {
            safelyDisposeMetaM(fileM.getMetaM());
        }
        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        session.delete(fileM);
        logger.info("Deleted empty fileM entry:" + fileM);
        tx.commit();
        session.close();
        if(autoCleanFlag) {
            cleanValidationSets();
        }
        return true;
    }

    // logic: query:all fileM -> check if File valid -> delete any invalid fileM (heavy work)
    /**
     * Cleanup the whole database and delete any unused fileM
     * Any unattached metaM will also be disposed. Requires considerable database I/O resource.
     */
    public void fullFileModelCleanse() {
        logger.info("Start FileM Cleansing Process!");
        Session session = InitSessionFactory.getNewSession();
        session.beginTransaction();
        // retrieve all fileModels
        Query<FileModel> q = session.createQuery("from FileModel f", FileModel.class);
        List<FileModel> fList = q.getResultList();
        session.close();
        logger.info("List Check: " + fList.size() + " FileM in total!");
        int delCount = 0;
        int updCount = 0;
        for (FileModel aFile : fList) {
            File toCheckFile = new File(aFile.getSrc());
            if (!toCheckFile.exists()) {
                deleteFileFromDB(aFile,false);
                delCount += 1;
            } else {
                if (aFile.getMd5().isBlank()) {
                    try {
                        aFile.setMd5(AudioMd5Helper.getAudioMd5Force(new MetaSong(aFile.getSrc())));
                    } catch (MetaIOException e) {
                        e.printStackTrace();
                    }
                    session = InitSessionFactory.getNewSession();
                    Transaction tx = session.beginTransaction();
                    session.update(aFile);
                    logger.info("UpdatedfileM entry:" + aFile);
                    tx.commit();
                    session.close();
                    updCount += 1;
                }
            }
        }
        //cleanup
        cleanValidationSets();
        logger.info("Deleted " + delCount + " unused FileM in cleansing!");
        logger.info("Updated " + updCount + " FileM without Md5 info!");
    }

    /**
     * Stantard procedure for undating a metaM for file.
     * @param meta MetaSong instance that *might* need to be updated. automatically clean-up
     * @return An always up-to-date FileM
     * @throws DatabaseException When unable to find FileM in database.
     */
    public static FileModel updateMetaForFile(MetaSong meta) throws DatabaseException {
        return updateMetaForFile(meta, true);
    }

    // logic: check timestamp first, if local files newer, modify(fileM=1)/(fileM>1)initiate
    // metadata
    /**
     * Stantard procedure for undating a metaM for file.
     * @param meta MetaSong instance that *might* need to be updated.
     * @param autoCleanFlag automatic cleanup validation set. could be disabled for private use
     * but BE SURE TO CLEAN in service level.
     * @return An always up-to-date FileM
     * @throws DatabaseException When unable to find FileM in database.
     */
    private static FileModel updateMetaForFile(MetaSong meta, boolean autoCleanFlag)
        throws DatabaseException {
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

                        toHandleMetaM.getAlbumM().getMetaModels().remove(toHandleMetaM);

                        toValidateAlbums.add(toHandleMetaM.getAlbumM());
                        // set later to avoid different hashcode;
                        setNewAlbum = true;
                    }
                    SongModel toCheckSongM = SongModel.guaranteeSongModel(meta);
                    boolean setNewSong = false;
                    if (!toHandleMetaM.getSongM().equals(toCheckSongM)) {

                        toHandleMetaM.getSongM().getMetaModels().remove(toHandleMetaM);

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

                    toHandleMetaM.getFileModels().remove(reFileModel);
                    // only a single file and a current meta exist, attach new meta & delete
                    // set new relation for File
                    reFileModel.attachMetaModel(toCompareMetaM);

                    // delete if no other files use this meta
                    if (toHandleMetaM.getFileModels().size() == 0) {
                        safelyDisposeMetaM(toHandleMetaM);
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
        if (autoCleanFlag) {
            cleanValidationSets(); // could repeat for multiple times if called many times
            // but since this is a static method, place it here for safe.
        }
        return reFileModel;
    }


    /* Support Methods Below */

    /**
     * Delete albumM, songM relation with target metaM (delete when necessary), dispose metaM.
     * @param toDisposeMetaM metaM to be safely disposed.
     */
    private static void safelyDisposeMetaM(MetaModel toDisposeMetaM) {
        toDisposeMetaM.getAlbumM().getMetaModels().remove(toDisposeMetaM);
        toValidateAlbums.add(toDisposeMetaM.getAlbumM());
        toDisposeMetaM.getSongM().getMetaModels().remove(toDisposeMetaM);
        toValidateSongs.add(toDisposeMetaM.getSongM());
        // double check: no fileM should use this MetaM now.
        if (toDisposeMetaM.getFileModels().size() != 0) {
            logger.warn("Disposing MetaModel " + toDisposeMetaM + "but FileM NOT CLEARED");
        }
        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        session.delete(toDisposeMetaM);
        tx.commit();
        session.close();
        logger.info("Deleted MetaM " + toDisposeMetaM);

    }


    // REMEMBER TO CALL IT ON EVERY SERVICE LEVEL DELETION!!!
    public static void cleanValidationSets() {
        logger.debug("Start Cleaning up!");
        if (toValidateAlbums != null) {
            logger.debug("Validating Albums!");
            for (AlbumModel toValidateAlbumM : toValidateAlbums) {
                if (toValidateAlbumM.getMetaModels().size() == 0) {
                    logger.debug("Cleaning Album:" + toValidateAlbumM.toString());
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
            logger.debug("Validating Songs!");
            for (SongModel toValidateSongM : toValidateSongs) {
                if (toValidateSongM.getMetaModels().size() == 0) {
                    logger.debug("Cleaning Song:" + toValidateSongM.toString());
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
            logger.debug("Validating Artists!");
            for (ArtistModel toValidateArtistM : toValidateArtists) {
                boolean noArtist = false;
                boolean noAlbumArtist = false;
                if (toValidateArtistM.getSongModels().size() == 0) {
                    logger.debug("No Song use Artist:" + toValidateArtistM.toString());
                    noArtist = true;
                }
                if (toValidateArtistM.getAlbumModels().size() == 0) {
                    logger.debug("No Album use Artist:" + toValidateArtistM.toString());
                    noAlbumArtist = true;
                }

                if (noArtist && noAlbumArtist) {
                    logger.info("Cleaning Artist:" + toValidateArtistM.toString());
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
