package local.generic;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import exception.ErrorCodes;
import exception.PlaylistIOException;

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
     * See toolkit.NewFileWriter for basic strucuture.
     * 
     * @param f File Object of output playlist location.
     * @throws PlaylistIOException
     */
    public void write(File f) throws PlaylistIOException {
        logger.info("Start writing playlist:" + f.toString());
        setStream(f);
        writeHeading();
        for (AbstractPlaylistSong aSong : songArrList) {
            writeASong(aSong);
        }
        writeEnding();
        closeStream();
        logger.debug("Writing playlist complete!" + f.toString());


    }

    public void write(File f, boolean bomToggle) throws PlaylistIOException {
        if (bomToggle) {
            logger.info("Start writing playlist:" + f.toString());
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
            logger.debug("Writing playlist complete!" + f.toString());

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
            oStreamWriter = new OutputStreamWriter(dataOut, "utf-8");

        } catch (FileNotFoundException fnfe) {
            throw new PlaylistIOException("Error in creating new Playlist File", fnfe,
                ErrorCodes.BASE_IO_ERROR);
        } catch (UnsupportedEncodingException uee) {
            throw new PlaylistIOException("Error in creating new Playlist File", uee,
                ErrorCodes.UNSUPPORTED_ENCODING_ERROR);
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
