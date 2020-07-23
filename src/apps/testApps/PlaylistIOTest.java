package apps.testApps;

import java.io.File;
import exception.MetaIOException;
import exception.NativeReflectionException;
import exception.PlaylistIOException;
import local.generic.AbstractPlaylistTable;
import local.generic.BaseLocalTestingClass;
import local.generic.PlaylistFileIO;
import local.generic.SupportedPlaylistFormat;

public class PlaylistIOTest extends BaseLocalTestingClass {

    public static void main(String[] args)
        throws PlaylistIOException, NativeReflectionException, MetaIOException {
        PlaylistIOTest me = new PlaylistIOTest();
        me.setAllLevel("debug");
        String src = "C:\\Users\\ASUS\\Music\\Dopamine\\Playlists\\RevueStarlight! OST.m3u";
        String src3 = "E:\\lzx\\Discovery\\ColdRain\\Playlist_now.m3u";
        String src4 = "E:\\program files\\foobar2000\\playlists\\AD Piano.m3u8";
        String src5= "E:\\lzx\\Discovery\\ColdRain\\Playlist_now_FROMm3u.m3u8";
        String src6= "E:\\lzx\\Discovery\\ColdRain\\Playlist_now_FROMm3u8.m3u";

        AbstractPlaylistTable aTable=PlaylistFileIO.readPlaylist(new File(src));
        aTable.printAllSong();
        PlaylistFileIO.writePlaylistAs(aTable, new File(src5), SupportedPlaylistFormat.M3U8, true);
        AbstractPlaylistTable bTable =PlaylistFileIO.readPlaylist(new File(src4));
        PlaylistFileIO.writePlaylistAs(bTable,new File(src6),SupportedPlaylistFormat.M3U,true);
    }
}
