package local.m3u8;

import local.generic.AbstractPlaylistSong;

public class M3u8Song extends AbstractPlaylistSong {
    
    protected String artist;
    protected String trackTitle;
    protected String LENGTH;
    public String getArtist() {
        return artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public String getTrackTitle() {
        return trackTitle;
    }
    public void setTrackTitle(String trackTitle) {
        this.trackTitle = trackTitle;
    }
    public String getLENGTH() {
        return LENGTH;
    }
    public void setLENGTH(String LENGTH) {
        this.LENGTH = LENGTH;
    }
    
    @Override
    public String toString() {
        return ("M3u8Song[src:"+src+", artist:"+artist+", trackTitle:"+trackTitle+", LENGTH:"+LENGTH+"]");
    }
    
    

}
