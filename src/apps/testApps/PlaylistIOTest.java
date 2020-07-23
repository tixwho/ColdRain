package apps.testApps;

import exception.MetaIOException;
import exception.NativeReflectionException;
import exception.PlaylistIOException;
import local.generic.AbstractPlaylistTable;
import local.generic.BaseLocalTestingClass;
import local.generic.PlaylistFileIO;

public class PlaylistIOTest extends BaseLocalTestingClass {

    public static void main(String[] args)
        throws PlaylistIOException, NativeReflectionException, MetaIOException {
        PlaylistIOTest me = new PlaylistIOTest();
        me.setAllLevel("debug");
        String src = "C:\\Users\\ASUS\\Music\\Dopamine\\Playlists\\RevueStarlight! OST.m3u";
        String src3 = "E:\\lzx\\Discovery\\ColdRain\\Playlist_now.m3u";
        String src4 = "E:\\program files\\foobar2000\\playlists\\AD Piano.m3u8";

        AbstractPlaylistTable aTable=PlaylistFileIO.readPlaylist(src);
        aTable.printAllSong();
        PlaylistFileIO.readPlaylist(src4).printAllSong();
    }
}
