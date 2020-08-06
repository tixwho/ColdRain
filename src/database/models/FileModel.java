package database.models;

import java.io.File;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import database.generic.DatabasePOJO;
import database.utils.DbHelper;
import database.utils.InitSessionFactory;
import exception.ColdRainException;
import exception.DatabaseException;
import exception.ErrorCodes;
import local.generic.MetaSong;


@Entity
@Table(name="File")
@SequenceGenerator(name = "file_seq", sequenceName = "file_id_seq", initialValue = 1, allocationSize = 1)
public class FileModel extends DatabasePOJO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -5287801736649202280L;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="file_seq")
    private Integer fileid;
    private String src;
    private Long lastModified;
    //notice: use album to test relation first. later it will be replaced by mid.
    @ManyToOne
    @JoinColumn(name = "metaid")
    private MetaModel metaM;
    //fileInfo 1to1, use the same id
 
    private FileInfoComp fileInfoC;
    
    public FileModel() {
        
    }
    
    public FileModel(MetaSong meta) {
        this.src=meta.getSrc();
        this.lastModified = DbHelper.calcLastModTimestamp(meta);
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
    
    public static FileModel findFileModel(MetaSong meta) throws DatabaseException {
        FileModel returnFileM;
        // just need to guarantee SongModel, AlbumModel and title,
        // since SongModel includes ArtistModel, AlbumModel includes AlbumArtist
        logger.debug("Finding FileModel for "+meta.getSrc());
        Session session = InitSessionFactory.getNewSession();
        session.beginTransaction();
        @SuppressWarnings("unchecked")
        Query<FileModel> q = (Query<FileModel>) session
            .createQuery("from FileModel f where f.src=?0");
        q.setParameter(0, meta.getSrc());
        FileModel toCheckFileM = q.uniqueResult();
        session.close();
        if (toCheckFileM == null) {
            logger.debug("File NOT FOUND");
            throw new DatabaseException("Does not found corresponding FileModel",ErrorCodes.DATABASE_NOT_FOUND_ERROR);
        } else {
            logger.debug("File FOUND");
            returnFileM = toCheckFileM;
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
    


    
    

}
