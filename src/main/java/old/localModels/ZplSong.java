package old.localModels;

public class ZplSong {
    //atom级，包含了每个<media>内的属性
    private String src;
    private String albumTitle;
    private String albumArtist;
    private String trackTitle;
    private String trackArtist;
    private String duration;
    
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

    public void setAlbumArtist(String albumArtist) {
        this.albumArtist = albumArtist;
    }

    public String getTrackTitle() {
        return trackTitle;
    }

    public void setTrackTitle(String trackTitle) {
        this.trackTitle = trackTitle;
    }

    public String getTrackArtist() {
        return trackArtist;
    }

    public void setTrackArtist(String trackArtist) {
        this.trackArtist = trackArtist;
    }
  
    public ZplSong() {
        this.src = "";
        this.albumTitle = "";
        this.albumArtist = "";
        this.trackTitle = "";
        this.trackArtist = "";
        this.duration = "";
        //todo
    }

}
