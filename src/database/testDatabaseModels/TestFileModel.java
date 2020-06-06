package database.testDatabaseModels;

import old.localModels.MetaSong_old;

public class TestFileModel {
    
    private int fid;
    private String addr;
    private String title;
    private String artist;
    private String album;
    
    
    public TestFileModel(MetaSong_old meta) {
        this.addr = meta.getSrc();
        this.title = meta.getTrackTitle();
        this.artist = meta.getArtist();
        this.album = meta.getAlbum();
        
        
    }


    public int getFid() {
        return fid;
    }


    public void setFid(int fid) {
        this.fid = fid;
    }


    public String getAddr() {
        return addr;
    }


    public void setAddr(String addr) {
        this.addr = addr;
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


    public String getAlbum() {
        return album;
    }


    public void setAlbum(String album) {
        this.album = album;
    }

}
