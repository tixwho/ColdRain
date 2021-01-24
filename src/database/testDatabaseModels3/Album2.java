package database.testDatabaseModels3;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import playlist.generic.MetaSong;

@Entity
@Table(name="Album2")
@SequenceGenerator(name = "album_seq", sequenceName = "album_id_seq")
public class Album2 implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 9010817103628242666L;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="album_seq")
    private Integer aid;
    private String album;
    private String albumArtist;
    private String totalDisc;
    @OneToMany(mappedBy="album")
    private Set<File2> files=new HashSet<File2>();
    
    public Album2() {
        
    }
    
    public Album2(MetaSong meta) {
        this.album=meta.getAlbum();
        this.albumArtist=meta.getAlbumArtist();
        this.totalDisc=meta.getTotalDiscNo();
    }
    
    public Integer getAid() {
        return aid;
    }
    public void setAid(Integer aid) {
        this.aid = aid;
    }
    public String getAlbum() {
        return album;
    }
    public void setAlbum(String album) {
        this.album = album;
    }
    public String getAlbumArtist() {
        return albumArtist;
    }

    public void setAlbumArtist(String albumArtist) {
        this.albumArtist = albumArtist;
    }

    public String getTotalDisc() {
        return totalDisc;
    }
    public void setTotalDisc(String totalDisc) {
        this.totalDisc = totalDisc;
    }
    public Set<File2> getFiles() {
        return files;
    }
    public void setFiles(Set<File2> files) {
        this.files = files;
    }
    
    @Override
    public String toString() {
        return "Album2 [aid=" + aid + ", albumName=" + album + ", totalDisc=" + totalDisc
            + ", files=" + files + "]";
    }
    
    
    
    

}
