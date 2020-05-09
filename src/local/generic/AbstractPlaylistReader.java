package local.generic;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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

    /**
     * ArrayList contains PlaylistSong instances
     */

    protected ArrayList<AbstractPlaylistSong> songArrList = new ArrayList<AbstractPlaylistSong>();
    /**
     * Universal logger for playlistReader.
     */
    public static Logger logger = LoggerFactory.getLogger(AbstractPlaylistReader.class);


    // abstract methods

    /**
     * Read Playlist File with corresponding reading method. Add throws later.
     * 
     * @throws PlaylistIOException
     * @throws NativeReflectionException
     */
    public abstract void read(File f) throws PlaylistIOException, NativeReflectionException;

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


    /**
     * Return the arraylist contains corresponding AbstractPlaylistSong.
     * 
     * @return Arraylist of AbstractPlaylistSong read from the playlist.
     */
    public ArrayList<AbstractPlaylistSong> getSongArrList() {
        return songArrList;
    }

    public abstract AbstractPlaylistTable getTable();

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


}
