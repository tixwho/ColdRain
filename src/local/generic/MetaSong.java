package local.generic;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
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
import exception.ErrorCodes;
import exception.MetaIOException;
import toolkit.LogMaker;

public class MetaSong {

    // NOTICE: THIS IS NOT AN ACTUAL SONG IMPLEMENTED IN ANY TYPE OF PLAYLIST

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
    private String albumDate;
    private final String FORMAT;
    private final String SAMPLERATE;
    private final String BITRATE;
    private final String LENGTH;

    public MetaSong(String addr) throws MetaIOException {
        try {
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
            setAlbumDate(tag.getFirst(FieldKey.YEAR));
            AudioHeader aoh = aof.getAudioHeader();
            this.FORMAT = aoh.getFormat();
            this.SAMPLERATE = aoh.getSampleRate();
            this.BITRATE = String.valueOf(aoh.getBitRateAsNumber());
            this.LENGTH = String.valueOf(aoh.getTrackLength());
            // debug start
            /*
             * LogMaker.logs("format::"+this.FORMAT); LogMaker.logs("SampleRate::"+this.SAMPLERATE);
             * LogMaker.logs("testbit::"+aoh.getBitRateAsNumber());
             * LogMaker.logs("testbit2::"+this.BITRATE);
             */
            LogMaker.logs("Metaed: " + src);
            // debug delete
        } catch (CannotReadException cre) {
            throw new MetaIOException("AudioFile Reading Exception", cre,
                ErrorCodes.AUDIOFILE_READING_ERROR);
        } catch (IOException ioe) {
            throw new MetaIOException("AudioFile Reading Exception", ioe,
                ErrorCodes.AUDIOFILE_IO_ERROR);
        } catch (TagException te) {
            throw new MetaIOException("AudioFile Reading Exception", te,
                ErrorCodes.AUDIOFILE_TAG_ERROR);
        } catch (ReadOnlyFileException rofe) {
            throw new MetaIOException("AudioFile Reading Exception", rofe,
                ErrorCodes.READONLY_FILE_ERROR);
        } catch (InvalidAudioFrameException iafe) {
            throw new MetaIOException("AudioFile Reading Exception", iafe,
                ErrorCodes.INVALID_AUDIOFRAME_ERROR);
        }

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

    public String getAlbumDate() {
        return albumDate;
    }

    public void setAlbumDate(String albumDate) {
        this.albumDate = albumDate;
    }

    public String getFORMAT() {
        return FORMAT;
    }

    public String getSAMPLERATE() {
        return SAMPLERATE;
    }

    public String getBITRATE() {
        return BITRATE;
    }

    public String getLENGTH() {
        return LENGTH;
    }


    // possible enhancement: use reflect and a list to automatically rotate and write
    public void writeMetaToFile() throws MetaIOException {
        try {
            if (trackTitle != null && trackTitle != "") {
                tag.setField(FieldKey.TITLE, trackTitle);
                // throws KeyNotFoundException, FieldDataInvaidException
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
            if (albumDate != null && albumDate != "") {
                tag.setField(FieldKey.YEAR, albumDate);
            }
            AudioFileIO.write(aof);
        } catch (KeyNotFoundException kne) {
            throw new MetaIOException("AudioFile Writing Exception", kne,
                ErrorCodes.KEYNOTFOUND_ERROR);
        } catch (FieldDataInvalidException fdie) {
            throw new MetaIOException("AudioFile Writing Exception", fdie,
                ErrorCodes.INVALID_FIELDDATA_ERROR);
        } catch (CannotWriteException cwe) {
            throw new MetaIOException("AudioFile Writing Exception", cwe,
                ErrorCodes.CANNOTWRITE_ERROR);
        }

    }
    
    @Override
    public String toString() {
        return MessageFormat.format("{0}[src={1}, title={2},  artist={3}, album={4}]", new Object[] {
            getClass().getSimpleName(), src, trackTitle, artist, album});
    }



}
