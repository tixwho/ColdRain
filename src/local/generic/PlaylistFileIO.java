package local.generic;

import java.util.HashMap;

public class PlaylistFileIO {

    // use a map with <suffix, correspondingReader>
    // considering file suffix to use correct reader.
    // same thing for writer. consult
    // https://bitbucket.org/ijabz/jaudiotagger/src/d83ea5692c23b1323dde25c4d7e9bac1462eaa21/src/org/ja
    // udiotagger/audio/AudioFileIO.java?at=master#AudioFileIO.java-237,352,382,414
    private AbstractPlaylistWriter writer;
    private AbstractPlaylistTable table;
    private AbstractPlaylistReader reader;
    private HashMap<String, AbstractPlaylistReader> readerMap;
    private HashMap<String, AbstractPlaylistWriter> writerMap;

}
