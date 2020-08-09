package apps.testApps;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.io.FileUtils;
import exception.MetaIOException;
import exception.NativeReflectionException;
import exception.PlaylistIOException;
import local.generic.AbstractPlaylistSong;
import local.generic.AbstractPlaylistTable;
import local.generic.BaseLocalTestingClass;
import local.generic.PlaylistFileIO;
import local.generic.SupportedPlaylistFormat;

public class PlaylistIOTest extends BaseLocalTestingClass {

    public static void main(String[] args)
        throws PlaylistIOException, NativeReflectionException, MetaIOException {
        PlaylistIOTest me = new PlaylistIOTest();
        me.setAllLevel("debug");

        String sourceSrc = "E:\\program files\\foobar2000\\playlists";
        Collection<File> playlistFiles = FileUtils.listFiles(new File(sourceSrc),
            SupportedPlaylistFormat.getSupportedPlaylistArray(), true);
        for(File pFile: playlistFiles) {
            AbstractPlaylistTable theTable = PlaylistFileIO.readPlaylist(pFile);
            logger.error("READING PLAYLISTFILE "+theTable.getPlaylistSrc());
            ArrayList<AbstractPlaylistSong> arr =theTable.getSongArrList();
            for(AbstractPlaylistSong theSong: arr) {
                logger.warn("playlistSong:"+theSong.getSrc());
            }
        }

    }
}
