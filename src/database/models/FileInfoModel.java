package database.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.Session;
import org.hibernate.Transaction;
import database.generic.DatabasePOJO;
import database.testDatabaseModels3.File2;
import database.utils.InitSessionFactory;
import local.generic.MetaSong;

@Entity
@Table(name="FileInfo")
@SequenceGenerator(name = "fileinfo_seq", sequenceName = "fileinfo_id_seq")
public class FileInfoModel extends DatabasePOJO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 2241804180052986786L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="fileinfo_seq")
    private Integer fileInfoId;
    
    @OneToOne
    @JoinColumn
    private FileModel fileM;
    
    private String format;
    private String sampleRate;
    private String bitRate;
    private String length;
    
    public FileInfoModel() {
        
    }
    
    public FileInfoModel(MetaSong meta) {
        this.format= meta.getFORMAT();
        this.sampleRate= meta.getSAMPLERATE();
        this.bitRate=meta.getBITRATE();
        this.length=meta.getLENGTH();
    }

    public Integer getFileInfoId() {
        return fileInfoId;
    }

    public void setFileInfoId(Integer fileInfoId) {
        this.fileInfoId = fileInfoId;
    }

    public FileModel getFileM() {
        return fileM;
    }

    public void setFileM(FileModel fileM) {
        this.fileM = fileM;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(String sampleRate) {
        this.sampleRate = sampleRate;
    }

    public String getBitRate() {
        return bitRate;
    }

    public void setBitRate(String bitRate) {
        this.bitRate = bitRate;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
    
    public static FileInfoModel createFileInfoModel(MetaSong meta) {
        FileInfoModel fileInfo = new FileInfoModel(meta);

        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        session.save(fileInfo);
        tx.commit();
        session.close();
        System.out.println("Created File");
        return fileInfo;
    }
    
    public void attachFileModel(FileModel fileModel) {
        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        System.out.println("Refreshing...");
        session.refresh(this);
        session.refresh(fileModel);
        this.setFileM(fileModel);
        logger.trace("File Model binded!");
        fileModel.setFileInfoM(this);
        logger.trace("FileInfo Model binded!");
        session.saveOrUpdate(fileModel);
        logger.trace("FileModel updated!");
        session.saveOrUpdate(this);
        logger.trace("FileInfoModel updated!");
        tx.commit();
        logger.debug("All change commited!");
        session.close();
    }
    
    
    
    

}
