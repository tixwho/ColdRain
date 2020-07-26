package database.testDatabaseModels2;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;
import local.generic.MetaSong;

public class Album1 {
    private int aid;
    private String album;
    private String albumArtist;
    private String totalDiscNo;
    private Set<Meta1> metas= new HashSet<Meta1>();
    
    public Album1() {
        
    }
    public Album1(MetaSong meta) {
        this.album=meta.getAlbum();
        this.albumArtist=meta.getAlbumArtist();
        this.totalDiscNo=meta.getTotalDiscNo();
    }
    public int getAid() {
        return aid;
    }
    public void setAid(int aid) {
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
    public String getTotalDiscNo() {
        return totalDiscNo;
    }
    public void setTotalDiscNo(String totalDiscNo) {
        this.totalDiscNo = totalDiscNo;
    }
    public Set<Meta1> getMetas() {
        return metas;
    }
    public void setMetas(Set<Meta1> metas) {
        this.metas = metas;
    }
    
    /* Possible new toString impl*/
    public String toString() {
        return MessageFormat.format("{0}[id={1}, name={2}]", new Object[] {
              getClass().getSimpleName(), aid, album });
     }

    
    

}
