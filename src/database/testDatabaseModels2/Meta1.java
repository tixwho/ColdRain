package database.testDatabaseModels2;

import java.util.HashSet;
import java.util.Set;
import playlist.generic.MetaSong;

public class Meta1 {
    private int mid;
    private String title;
    private String artist;
    private String trackNo;
    private String discNo;
    private Album1 album;
    private Set<File1> files_orig=new HashSet<File1>();
    private Set<File1> files_mod=new HashSet<File1>();
    
    public Meta1() {
        
    }
    public Meta1(MetaSong meta) {
        this.title=meta.getTrackTitle();
        this.artist=meta.getArtist();
        this.trackNo=meta.getTrackNo();
        this.discNo=meta.getDiscNo();
    }
    public int getMid() {
        return mid;
    }
    public void setMid(int mid) {
        this.mid = mid;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getArtist() {
        return artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public String getTrackNo() {
        return trackNo;
    }
    public void setTrackNo(String trackNo) {
        this.trackNo = trackNo;
    }
    public String getDiscNo() {
        return discNo;
    }
    public void setDiscNo(String discNo) {
        this.discNo = discNo;
    }
    public Album1 getAlbum() {
        return album;
    }
    public void setAlbum(Album1 album) {
        this.album = album;
    }
    public Set<File1> getFiles_orig() {
        return files_orig;
    }
    public void setFiles_orig(Set<File1> files_orig) {
        this.files_orig = files_orig;
    }
    public Set<File1> getFiles_mod() {
        return files_mod;
    }
    public void setFiles_mod(Set<File1> files_mod) {
        this.files_mod = files_mod;
    }
    
    

    

}
