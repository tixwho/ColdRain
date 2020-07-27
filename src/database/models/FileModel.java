package database.models;

import java.io.File;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.Session;
import org.hibernate.Transaction;
import database.generic.DatabasePOJO;
import database.utils.InitSessionFactory;
import local.generic.MetaSong;


@Entity
@Table(name="File")
@SequenceGenerator(name = "file_seq", sequenceName = "file_id_seq")
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
    @OneToOne(mappedBy="fileM")
    private FileInfoModel fileInfoM;
    
    public FileModel() {
        
    }
    
    public FileModel(MetaSong meta) {
        this.src=meta.getSrc();
        File f = new File(meta.getSrc());
        this.lastModified = f.lastModified();
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
    

    public FileInfoModel getFileInfoM() {
        return fileInfoM;
    }

    public void setFileInfoM(FileInfoModel fileInfoM) {
        this.fileInfoM = fileInfoM;
    }
    
    
    public static FileModel createFileInfoModel(MetaSong meta) {
        FileModel file = new FileModel(meta);

        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        session.save(file);
        tx.commit();
        session.close();
        System.out.println("Created File");
        return file;
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


    
    

}
