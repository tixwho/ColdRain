package database.testDatabaseModels3;

import java.io.Serializable;
import java.text.MessageFormat;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import playlist.generic.MetaSong;


@Entity
@Table(name="File2")
@SequenceGenerator(name = "file_seq", sequenceName = "file_id_seq")
public class File2 implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -5287801736649202280L;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="file_seq")
    private Integer fid;
    private String src;
    //notice: use album to test relation first. later it will be replaced by mid.
    @ManyToOne
    @JoinColumn
    private Album2 album;
    
    public File2() {
        
    }
    
    public File2(MetaSong meta) {
        this.src=meta.getSrc();
    }
    
    
    /* Getters & Setters */
    public Integer getFid() {
        return fid;
    }
    public void setFid(Integer fid) {
        this.fid = fid;
    }
    public String getSrc() {
        return src;
    }
    public void setSrc(String src) {
        this.src = src;
    }
    public Album2 getAlbum() {
        return album;
    }
    public void setAlbum(Album2 album) {
        this.album = album;
    }
    
    @Override public String toString() {
        return MessageFormat.format("{0}[src={1}, album={2}, fid={3}]", new Object[] {
            getClass().getSimpleName(), src, album, fid});
    }
    
    

}
