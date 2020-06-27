package local.generic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.apache.commons.io.input.BOMInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import exception.ErrorCodes;
import exception.NativeReflectionException;
import exception.PlaylistIOException;

/**
 * General Format of PlaylistReader
 * 
 * @author tixwho
 *
 */
public abstract class AbstractPlaylistReader {
    /**
     * 
     */
    protected SupportedMeta[] suppMeta;
    protected InputStreamReader in;
    protected BufferedReader br;
    protected XMLHelper xmlhelper; //actually no need to declare here. AbstractXMLPlaylistReader
    
    protected String tempData;

    /**
     * ArrayList contains PlaylistSong instances
     */

    protected ArrayList<AbstractPlaylistSong> songArrList = new ArrayList<AbstractPlaylistSong>();
    /**
     * Universal logger for playlistReader.
     */
    public static Logger logger = LoggerFactory.getLogger(AbstractPlaylistReader.class);
    
    //constructor: call initializeMeta()
    public AbstractPlaylistReader() {
        initializeMeta();
        logger.debug("Meta Initialized in:" + this.getClass());
    }


    // abstract methods

    /**
     * Read Playlist File with corresponding reading method. Add throws later.
     * 
     * @throws PlaylistIOException
     * @throws NativeReflectionException
     */
    public void read(File f) throws PlaylistIOException, NativeReflectionException{
        logger.info("Start reading playlist "+f.toString());
        songArrList.clear();//flush before use.
        setStream(f);
        readHeading();        
        try {
            if (tempData==null) {//to handle circumstances that tempData is not readed in heading
                tempData = br.readLine(); //readFirstLine
            }  
            while (tempData != null) {
            readASong();
            tempData = br.readLine();
            }
        }catch (IOException ioe) {
            throw new PlaylistIOException("Error in reading bufferedReader",ioe,ErrorCodes.BASE_IO_ERROR);
        }
        readEnding();
        closeStream();
        logger.debug("Reading playlist complete!" + f.toString());
        
    }

    /**
     * Read Playlist File with corresponding reading method by String. Add throws later.
     * 
     * @throws PlaylistIOException
     * @throws NativeReflectionException
     */
    public void read(String src) throws PlaylistIOException, NativeReflectionException {
        read(new File(src));
    }
    // universal methods
    
    protected abstract void initializeMeta();
    
    /**
     * Have not read any line yet.
     */
    protected abstract void readHeading();
    
    /**
     * NOTICE: Not Always Read a Song!!!
     * Read current line stored in Reader, and act correspondingly.
     * If song instance in playlist file occupy more than one line, then extra scanning need to be
     * taken in this method.
     * Ending of this method is always add a song to songArrList.
     * @throws NativeReflectionException
     */
    protected abstract void readASong() throws NativeReflectionException;
    
    protected abstract void readEnding();


    /**
     * Return the arraylist contains corresponding AbstractPlaylistSong.
     * 
     * @return Arraylist of AbstractPlaylistSong read from the playlist.
     */
    public ArrayList<AbstractPlaylistSong> getSongArrList() {
        return songArrList;
    }

    public abstract AbstractPlaylistTable getTable() throws PlaylistIOException;

    public void setAllProperties(AbstractPlaylistReader typicalReader, AbstractPlaylistSong aSong)
        throws NativeReflectionException {
        try {
            for (SupportedMeta meta : suppMeta) {
                Method setMethod = SupportedMeta.getSetter(aSong, meta);
                Field field = typicalReader.getClass().getDeclaredField(meta.getProperty());
                field.setAccessible(true);
                setMethod.invoke(aSong, field.get(this));
            }
        } catch (NoSuchMethodException nsme) {
            throw new NativeReflectionException("M3UReader Reflection Exception", nsme,
                ErrorCodes.REFLECT_UNKNOWN_METHOD_ERROR);
        } catch (IllegalAccessException iace) {
            throw new NativeReflectionException("M3UReader Reflection Exception", iace,
                ErrorCodes.ILLEGAL_ACCESS_ERROR);
        } catch (IllegalArgumentException iare) {
            throw new NativeReflectionException("M3UReader Reflection Exception", iare,
                ErrorCodes.ILLEGAL_ARGUMENT_ERROR);
        } catch (InvocationTargetException ite) {
            throw new NativeReflectionException("M3UReader Reflection Exception", ite,
                ErrorCodes.INVOCATION_TARGET_ERROR);
        } catch (NoSuchFieldException nfe) {
            throw new NativeReflectionException("M3UReader Reflection Exception", nfe,
                ErrorCodes.NO_SUCH_FIELD_ERROR);
        } catch (SecurityException se) {
            throw new NativeReflectionException("M3UReader Reflection Exception", se,
                ErrorCodes.SECURITY_ERROR);
        }
    }

    public void setSupportedMeta(SupportedMeta[] metaArray) {
        suppMeta = metaArray;
    }
    
    //added BOMInputStream
    protected void setStream(File f) throws PlaylistIOException{
        try {
            BOMInputStream bomIn = new BOMInputStream(new FileInputStream(f));
            in = new InputStreamReader(bomIn, "utf-8");
            br = new BufferedReader(in);
            logger.info("BOM Status: "+bomIn.hasBOM());
            
        } catch (FileNotFoundException fnfe) {
            throw new PlaylistIOException("Error in reading playlist file",fnfe,ErrorCodes.BASE_IO_ERROR);
        } catch (UnsupportedEncodingException uee) {
            throw new PlaylistIOException("Error in reading",uee,ErrorCodes.UNSUPPORTED_ENCODING_ERROR);
        } catch (IOException ioe) {
            throw new PlaylistIOException("Error in reading BOM status",ioe,ErrorCodes.BASE_IO_ERROR);
        } 
        logger.debug("Stream created!");
        
    }
    
    protected void closeStream() throws PlaylistIOException{
        try {
            in.close();
            br.close();
        } catch (IOException ioe) {
            throw new PlaylistIOException("Error in closing outputStream",ioe,ErrorCodes.BASE_IO_ERROR);
        }
        
        logger.debug("Stream closed!");
    }


}
