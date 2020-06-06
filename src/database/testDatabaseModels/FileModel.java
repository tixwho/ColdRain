package database.testDatabaseModels;

import old.localModels.MetaSong_old;
import toolkit.HibernateUtils;

public class FileModel {

    private int fid;
    private String location;
    private String title;
    private String album;
    private String artist;
    private String albumartist;
    private String trackNo;
    private String suffix;
    private String bitdepth;
    private int bitrate;
    private String samplerate;
    private int length;
    private String offset_artist;
    private String offset_album;
    private String offset_title;
    private int quality_num;

    /* Constructor */
    public FileModel(MetaSong_old meta) {
        this.location = meta.getSrc();
        this.title = meta.getTrackTitle();
        this.album = meta.getAlbum();
        this.artist = meta.getArtist();
        this.albumartist =
            HibernateUtils.acquireAlbumArtist(meta.getAlbumArtist(), meta.getArtist());
        this.trackNo = meta.getTrackNo();
        this.suffix = fixSuffix(HibernateUtils.splitFormat(meta.getFORMAT())[0]);
        this.bitdepth = HibernateUtils.splitFormat(meta.getFORMAT())[1];
        this.bitrate = meta.getBITRATE();
        this.samplerate = meta.getSAMPLERATE();
        this.length = meta.getLENGTH();
        this.offset_artist = this.artist;
        this.offset_album = this.album;
        this.offset_title = this.title;
        this.quality_num = getQuality(this.suffix, this.samplerate, this.bitdepth, this.bitrate);
    }

    /* In class functions */
    private int getQuality(String isuffix, String isample, String ibitdepth, int ibitrate) {
        int quality = 0;
        switch (isuffix) {
            case "FLAC":
                quality += 200;
                break;
            case "MP3":
                quality += 100;
                break;
        }
        int intSample = Integer.parseInt(isample);
        if (intSample > 48000) {
            quality += 30;
        } else if (intSample > 44100) {
            quality += 20;
        } else {
            quality += 10;
        }
        switch (ibitdepth) {
            case "32 bits":
                quality += 5;
                break;
            case "24 bits":
                quality += 4;
                break;
            case "16 bits":
                quality += 3;
                break;
            case "Layer 3":
                if (ibitrate == 320) {
                    quality += 2;
                } else {
                    quality += 1;
                }
                break;
        }
        return quality;
    }

    private String fixSuffix(String inSuffix) {
        if (inSuffix.equals("MPEG-1")) {
            return "MP3";
        }
        return inSuffix;
    }

    /* Getter'n'setter */

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbumartist() {
        return albumartist;
    }

    public void setAlbumartist(String albumartist) {
        this.albumartist = albumartist;
    }

    public String getTrackNo() {
        return trackNo;
    }

    public void setTrackNo(String trackNo) {
        this.trackNo = trackNo;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getBitdepth() {
        return bitdepth;
    }

    public void setBitdepth(String bitrate) {
        this.bitdepth = bitrate;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public String getSamplerate() {
        return samplerate;
    }

    public void setSamplerate(String samplerate) {
        this.samplerate = samplerate;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getOffset_artist() {
        return offset_artist;
    }

    public void setOffset_artist(String offset_artist) {
        this.offset_artist = offset_artist;
    }

    public String getOffset_album() {
        return offset_album;
    }

    public void setOffset_album(String offset_album) {
        this.offset_album = offset_album;
    }

    public String getOffset_title() {
        return offset_title;
    }

    public void setOffset_title(String offset_title) {
        this.offset_title = offset_title;
    }

    public int getQuality_num() {
        return quality_num;
    }

    public void setQuality_num(int quality_num) {
        this.quality_num = quality_num;
    }



}
