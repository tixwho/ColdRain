package tables;

import java.io.File;
import java.io.IOException;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import toolkit.LogMaker;

public class MetaSong {

    private AudioFile aof;
    private Tag tag;
    private String src;
    private String fileName;
    private String trackTitle;
    private String trackNo;
    private String discNo;
    private String totalDiscNo;
    private String artist;
    private String album;
    private String albumArtist;
    private final String FORMAT;
    private final String SAMPLERATE;
    private final int BITRATE;
    private final int LENGTH;

    public MetaSong(String addr) throws CannotReadException, IOException, TagException,
        ReadOnlyFileException, InvalidAudioFrameException {

        aof = AudioFileIO.read(new File(addr));
        tag = aof.getTag();
        setSrc(addr);
        setFileName(AudioFile.getBaseFilename(new File(addr)));
        setTrackTitle(tag.getFirst(FieldKey.TITLE));
        setTrackNo(tag.getFirst(FieldKey.TRACK));
        setDiscNo(tag.getFirst(FieldKey.DISC_NO));
        setTotalDiscNo(tag.getFirst(FieldKey.DISC_TOTAL));
        setArtist(tag.getFirst(FieldKey.ARTIST));
        setAlbum(tag.getFirst(FieldKey.ALBUM));
        setAlbumArtist(tag.getFirst(FieldKey.ALBUM_ARTIST));
        AudioHeader aoh = aof.getAudioHeader();
        this.FORMAT = aoh.getFormat();
        this.SAMPLERATE = aoh.getSampleRate();
        this.BITRATE = (int) aoh.getBitRateAsNumber();
        this.LENGTH = aoh.getTrackLength();
        //debug start
        LogMaker.logs("format::"+this.FORMAT);
        LogMaker.logs("SampleRate::"+this.SAMPLERATE);
        LogMaker.logs("testbit::"+aoh.getBitRateAsNumber());
        //debug delete


    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }



    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTrackTitle() {
        return trackTitle;
    }

    public void setTrackTitle(String trackTitle) {
        this.trackTitle = trackTitle;
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

    public String getTotalDiscNo() {
        return totalDiscNo;
    }

    public void setTotalDiscNo(String totalDiscNo) {
        this.totalDiscNo = totalDiscNo;
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

    public String getAlbumArtist() {
        return albumArtist;
    }

    public void setAlbumArtist(String albumArtist) {
        this.albumArtist = albumArtist;
    }
    
    public String getFORMAT() {
        return FORMAT;
    }

    public String getSAMPLERATE() {
        return SAMPLERATE;
    }
    
    public int getBITRATE() {
        return BITRATE;
    }
    
    public int getLENGTH() {
        return LENGTH;
    }

    
    //possible enhancement: use reflect and a list to automatically rotate and write
    public void writeMetaToFile()
        throws KeyNotFoundException, FieldDataInvalidException, CannotWriteException {
        if (trackTitle != null && trackTitle != "") {
            tag.setField(FieldKey.TITLE, trackTitle);
            //throws KeyNotFoundException, FieldDataInvaidException
        }
        if (trackNo != null && trackNo != "") {
            tag.setField(FieldKey.TRACK, trackNo);
        }
        if (discNo != null && discNo != "") {
            tag.setField(FieldKey.DISC_NO, discNo);
        }
        if (totalDiscNo != null && totalDiscNo != "") {
            tag.setField(FieldKey.DISC_TOTAL, totalDiscNo);
        }
        if (artist != null && artist != "") {
            tag.setField(FieldKey.ARTIST, artist);
        }
        if (album != null && album != "") {
            tag.setField(FieldKey.ALBUM, album);
        }
        if (albumArtist != null && albumArtist != "") {
            tag.setField(FieldKey.ALBUM_ARTIST, albumArtist);
        }
        AudioFileIO.write(aof);
        //throws CannotWriteException

    }



}
