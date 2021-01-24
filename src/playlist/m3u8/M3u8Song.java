package local.m3u8;

import java.text.MessageFormat;
import local.generic.AbstractPlaylistSong;

public class M3u8Song extends AbstractPlaylistSong {

    protected String artist = null;
    protected String trackTitle = null;
    protected String LENGTH = null;

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
        return MessageFormat.format("{0}[src={1}, artist={2},  trackTitle={3}, LENGTH={4}]",
            new Object[] {getClass().getSimpleName(), src, artist, trackTitle, LENGTH});
    }



}
