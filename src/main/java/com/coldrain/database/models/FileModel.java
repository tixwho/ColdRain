package com.coldrain.database.models;

import com.coldrain.database.generic.DatabasePOJO;
import com.coldrain.database.utils.DbHelper;
import com.coldrain.database.utils.InitSessionFactory;
import com.coldrain.exception.DatabaseException;
import com.coldrain.exception.ErrorCodes;
import com.coldrain.playlist.generic.MetaSong;
import com.coldrain.toolkit.AudioMd5Helper;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "File", indexes = {@Index(name = "index_fileSrc",columnList = "src ASC")})
@SequenceGenerator(name = "file_seq", sequenceName = "file_id_seq", initialValue = 1,
    allocationSize = 1)
public class FileModel extends DatabasePOJO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5287801736649202280L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_seq")
    private Integer fileid;
    private String src;
    private Long lastModified;
    private String md5; // nullable, can be null if not scanned or not supported filetype.

    // MetaModel mid, key in com.coldrain.database. Many to One.
    @ManyToOne
    @JoinColumn(name = "metaid")
    private MetaModel metaM;
    // fileInfo 1to1, use the same id

    private FileInfoComp fileInfoC;

    public FileModel() {

    }

    /**
     * Set src, lastModified, and md5 (flac only, read from header) based on metadata.
     * @param meta Input Metadata.
     */
    public FileModel(MetaSong meta) {
        this.src = meta.getSrc();
        this.lastModified = DbHelper.calcLastModTimestamp(meta);
        this.md5 = AudioMd5Helper.getAudioMd5Force(meta); // supports mp3 and flac;
    }


    /* Getters & Setters */
    public Integer getFileid() {
        return fileid;
    }

    public void setFileid(Integer fid) {
        this.fileid = fid;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }


    public Long getLastModified() {
        return lastModified;
    }

    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public MetaModel getMetaM() {
        return metaM;
    }

    public void setMetaM(MetaModel metaM) {
        this.metaM = metaM;
    }


    public FileInfoComp getFileInfoC() {
        return fileInfoC;
    }

    public void setFileInfoC(FileInfoComp fileInfoM) {
        this.fileInfoC = fileInfoM;
    }


    public static FileModel createFileModel(MetaSong meta) {
        FileModel file = new FileModel(meta);
        FileInfoComp fileInfo = new FileInfoComp(meta);
        file.setFileInfoC(fileInfo);
        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        session.save(file);
        tx.commit();
        session.close();
        System.out.println("Created File");
        return file;
    }

    /**
     * Use src in MetaSong to find corresponding filemodel in com.coldrain.database.
     * @param meta 
     * @return
     * @throws DatabaseException when cannot find such FileModel in com.coldrain.database.
     */
    public static FileModel findFileModel(MetaSong meta) throws DatabaseException {
        return findFileModel(meta.getSrc(), true);
    }


    /**
     * Find an a FileModel already within com.coldrain.database based on src.
     * @param src
     * @param oughtFlag if true, empty query will throw an DatabaseException
     * @return found FileModel or null
     * @throws DatabaseException when outFlat set to true and empty query
     */
    public static FileModel findFileModel(String src, boolean oughtFlag) throws DatabaseException {
        FileModel returnFileM = null;
        // just need to guarantee SongModel, AlbumModel and title,
        // since SongModel includes ArtistModel, AlbumModel includes AlbumArtist
        logger.debug("Finding FileModel for " + src);
        Session session = InitSessionFactory.getNewSession();
        session.beginTransaction();
        // Query: src @ fileModel
        Query<FileModel> q =
            session.createQuery("from FileModel f where f.src=?1", FileModel.class);
        q.setParameter(1, src);
        FileModel toCheckFileM = q.uniqueResult();
        session.close();
        if (toCheckFileM == null) {

            if (oughtFlag) {
                logger.warn("File should found with src but NOT FOUND");
                throw new DatabaseException("Does not found corresponding FileModel",
                    ErrorCodes.DATABASE_NOT_FOUND);
            }
            logger.info("fileModel not found with given src, return null");
        } else {
            logger.debug("File FOUND");
            returnFileM = toCheckFileM;
        }
        return returnFileM;
    }

    public static FileModel findFileModelByMd5(String md5, boolean oughtFlag)
        throws DatabaseException {
        FileModel returnFileM = null;
        // try to find fileModel already within com.coldrain.database with md5
        // possible: 0, or 1, or multiple
        logger.debug("Finding FIleModel with md5:" + md5);
        Session session = InitSessionFactory.getNewSession();
        session.beginTransaction();
        // Query: md5 @ fileModel
        Query<FileModel> q =
            session.createQuery("from FileModel f where f.md5=?1", FileModel.class);
        q.setParameter(1,md5);
        List<FileModel> md5ResList = q.getResultList();
        // check: how many FileModels with same md5 are there?
        switch (md5ResList.size()) {
            case 0:
                // no result; throw com.coldrain.exception or ignore& return null;
                if (oughtFlag) {
                    logger.warn("should've found fileModel but not found! throwing com.coldrain.exception");
                    throw new DatabaseException("Does not found corresponding FileModel",
                        ErrorCodes.DATABASE_NOT_FOUND);
                }
                logger.info("fileModel not found in md5, return null");
                // if not, returnFileM is null and will be returned.
                break;
            case 1:
                // unique result: return that fileModel;
                returnFileM = md5ResList.get(0);
                break;
            default:
                // not unique result: select fileModel with highest quality
                int qualityNow = 0;
                for (FileModel aFileM : md5ResList) {
                    if (aFileM.getFileInfoC().getQuality() > qualityNow) {
                        qualityNow = aFileM.getFileInfoC().getQuality();
                        returnFileM = aFileM;
                    }
                }
        }
        return returnFileM;
    }

    public void attachMetaModel(MetaModel metaModel) {
        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        System.out.println("Refreshing...");
        session.refresh(this);
        session.refresh(metaModel);

        this.setMetaM(metaModel);
        logger.trace("Meta Model binded to FileModel!");
        metaModel.getFileModels().add(this);
        logger.trace("File Model binded to MetaModel!");
        session.saveOrUpdate(metaModel);
        logger.trace("MetaModel updated!");
        session.saveOrUpdate(this);
        logger.trace("FileModel updated!");
        tx.commit();
        logger.debug("All change commited!");
        session.close();
    }

    public void updateTimeStamp() {
        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        session.refresh(this);
        this.lastModified = DbHelper.calcLastModTimestamp(this.getSrc());

        session.saveOrUpdate(this);
        logger.trace("FileModel timestamp updated!");
        tx.commit();
        logger.debug("All change commited! Timestamp updated.");
        session.close();

    }

    @Override
    public int hashCode() {
        return Objects.hash(fileInfoC, fileid, lastModified, md5, src);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FileModel other = (FileModel) obj;
        return Objects.equals(fileInfoC, other.fileInfoC) && Objects.equals(fileid, other.fileid)
            && Objects.equals(lastModified, other.lastModified) && Objects.equals(md5, other.md5)
            && Objects.equals(src, other.src);
    }



}
