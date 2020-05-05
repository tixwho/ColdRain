package local.generic;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * @throws IOException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public abstract void read(File f)
        throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException,
        IllegalArgumentException, InvocationTargetException, NoSuchFieldException;

    /**
     * Read Playlist File with corresponding reading method by String. Add throws later.
     * 
     * @throws IOException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public void read(String src)
        throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException,
        IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
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

    public void setAllProperties(AbstractPlaylistReader typicalReader, AbstractPlaylistSong aSong)
        throws NoSuchMethodException, SecurityException, NoSuchFieldException,
        IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        for (SupportedMeta meta : suppMeta) {
            Method setMethod = SupportedMeta.getSetter(aSong, meta);
            Field field = typicalReader.getClass().getDeclaredField(meta.getMetaName());
            field.setAccessible(true);
            setMethod.invoke(aSong, field.get(this));
        }
    }

    public void setSupportedMeta(SupportedMeta[] metaArray) {
        suppMeta = metaArray;
    }


}
