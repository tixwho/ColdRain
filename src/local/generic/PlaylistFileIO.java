package local.generic;

import java.io.File;
import java.util.HashMap;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import exception.ErrorCodes;
import exception.NativeReflectionException;
import exception.PlaylistIOException;
import local.m3u.M3uReader;
import local.m3u.M3uWriter;
import local.m3u8.M3u8Reader;
import local.m3u8.M3u8Writer;

public class PlaylistFileIO {

    // use a map with <suffix, correspondingReader>
    // considering file suffix to use correct reader.
    // same thing for writer. consult
    // https://bitbucket.org/ijabz/jaudiotagger/src/d83ea5692c23b1323dde25c4d7e9bac1462eaa21/src/org/ja
    // udiotagger/audio/AudioFileIO.java?at=master#AudioFileIO.java-237,352,382,414

    /**
     * Static logger
     */
    public static Logger logger = LoggerFactory.getLogger(PlaylistFileIO.class);
    private static PlaylistFileIO defaultIOInstane;

    private AbstractPlaylistWriter writer;
    private AbstractPlaylistTable table;
    private AbstractPlaylistReader reader;
    private HashMap<String, AbstractPlaylistReader> readersMap =
        new HashMap<String, AbstractPlaylistReader>();
    private HashMap<String, AbstractPlaylistWriter> writersMap =
        new HashMap<String, AbstractPlaylistWriter>();

    
    public static AbstractPlaylistTable readPlaylist(File f) throws PlaylistIOException, NativeReflectionException {
        return getDefaultPlaylistFileIO().readPlaylistFile(f);
    }
    
    public static AbstractPlaylistTable readPlaylist(String filePath) throws PlaylistIOException, NativeReflectionException {
        return readPlaylist(new File(filePath));
    }

    public AbstractPlaylistTable readPlaylistFile(File f) throws PlaylistIOException, NativeReflectionException {
        String ext = FilenameUtils.getExtension(f.getAbsolutePath());
        logger.debug("Reading playlist file:"+f.toString());
        AbstractPlaylistReader apr = readersMap.get(ext);
        logger.trace("extension:"+ext);
        if (apr == null)
        {
            throw new PlaylistIOException("Unsupported playlist file suffix!",ErrorCodes.UNSUPPORTED_PLAYLIST_ERROR);
        }
        apr.read(f);
        return apr.getTable();
    }
    
    
    
    /**
     * Creates an instance.
     */
    public PlaylistFileIO() {
        prepareReadersAndWriters();
    }

    /**
     * return PlaylistFileIO for static use.
     * 
     * @return The default instance.
     */
    public static PlaylistFileIO getDefaultPlaylistFileIO() {
        if (defaultIOInstane == null) {
            defaultIOInstane = new PlaylistFileIO();
        }
        return defaultIOInstane;
    }

    private void prepareReadersAndWriters() {
        //prepare readers
        readersMap.put(SupportedPlaylistFormat.M3U.getFileSuffix(),new M3uReader());
        readersMap.put(SupportedPlaylistFormat.M3U8.getFileSuffix(),new M3u8Reader());
        
        //prepare writers
        writersMap.put(SupportedPlaylistFormat.M3U.getFileSuffix(),new M3uWriter());
        writersMap.put(SupportedPlaylistFormat.M3U8.getFileSuffix(),new M3u8Writer());
    }



}
