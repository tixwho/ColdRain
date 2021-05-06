package com.coldrain.playlist.generic;

import com.coldrain.exception.ErrorCodes;
import com.coldrain.exception.PlaylistIOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public abstract class AbstractPlaylistWriter {

    protected ArrayList<AbstractPlaylistSong> songArrList = new ArrayList<AbstractPlaylistSong>();
    protected DataOutputStream dataOut;
    protected OutputStreamWriter oStreamWriter;
    protected XMLHelper xmlhelper; // when used, override setStream,writeHeading,writeEnding and
                                   // closeStream()
    /**
     * Universal logger for playlistWriter.
     */
    protected static final Logger logger = LoggerFactory.getLogger(AbstractPlaylistWriter.class);

    /**
     * See com.coldrain.toolkit.NewFileWriter for basic strucuture.
     * 
     * @param f File Object of output com.coldrain.playlist location.
     * @throws PlaylistIOException
     */
    public void write(File f) throws PlaylistIOException {
        logger.info("Start writing com.coldrain.playlist:" + f.toString());
        setStream(f);
        writeHeading();
        for (AbstractPlaylistSong aSong : songArrList) {
            writeASong(aSong);
        }
        writeEnding();
        closeStream();
        logger.debug("Writing com.coldrain.playlist complete!" + f);


    }

    public void write(File f, boolean bomToggle) throws PlaylistIOException {
        if (bomToggle) {
            logger.info("Start writing com.coldrain.playlist:" + f.toString());
            setStream(f);
            try {
                logger.info("writing BOM");
                oStreamWriter.append('\ufeff');
            } catch (IOException ioe) {
                throw new PlaylistIOException("BOM Writer IO Exception", ioe, ErrorCodes.BASE_IO_ERROR);
            }
            writeHeading();
            for (AbstractPlaylistSong aSong : songArrList) {
                writeASong(aSong);
            }
            writeEnding();
            closeStream();
            logger.debug("Writing com.coldrain.playlist complete!" + f);

        } else {
            write(f);
        }
    }
    
    public void write(File f, AbstractPlaylistTable table, boolean bomToggle) throws PlaylistIOException {
        setSongArrList(table.getSongArrList());
        write(f,bomToggle);
    }

    

    protected void writeBom() {

    }

    protected abstract void writeHeading() throws PlaylistIOException;

    protected abstract void writeEnding();

    protected abstract void writeASong(AbstractPlaylistSong aPlaylistSong)
        throws PlaylistIOException;

    public void setSongArrList(ArrayList<AbstractPlaylistSong> songArrList) {
        this.songArrList = songArrList;
    }

    protected void setStream(File f) throws PlaylistIOException {
        try {
            dataOut = new DataOutputStream(new FileOutputStream(f));
            oStreamWriter = new OutputStreamWriter(dataOut, StandardCharsets.UTF_8);

        } catch (FileNotFoundException fnfe) {
            throw new PlaylistIOException("Error in creating new Playlist File", fnfe,
                ErrorCodes.BASE_IO_ERROR);
        }
        logger.debug("Stream created!");
    }

    protected void closeStream() throws PlaylistIOException {
        try {
            oStreamWriter.close();
            dataOut.close();

        } catch (IOException ioe) {
            throw new PlaylistIOException("Error in closing outputStream", ioe,
                ErrorCodes.BASE_IO_ERROR);
        }
        logger.debug("Stream closed!");
    }

}
