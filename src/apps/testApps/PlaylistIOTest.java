package apps.testApps;

import exception.MetaIOException;
import exception.NativeReflectionException;
import exception.PlaylistIOException;
import local.generic.AbstractPlaylistReader;
import local.generic.AbstractPlaylistTable;
import local.generic.BaseLocalTestingClass;
import local.m3u.M3uReader;
import local.m3u.M3uTable;
import local.m3u.M3uWriter;
import local.m3u8.M3u8Table;

public class PlaylistIOTest extends BaseLocalTestingClass {

    public static void main(String[] args)
        throws PlaylistIOException, NativeReflectionException, MetaIOException {
        tim.timerStart();
        String src = "C:\\Users\\ASUS\\Music\\Dopamine\\Playlists\\RevueStarlight! OST.m3u";
        String src2 = "C:\\Users\\ASUS\\Music\\Dopamine\\Playlists\\Rayons.m3u";
        String src3 = "E:\\lzx\\Discovery\\ColdRain\\Playlist_noBOM.m3u";
        String src4 = "E:\\lzx\\Discovery\\ColdRain\\Playlist_BOM.m3u";

        AbstractPlaylistReader reader = new M3uReader();
        reader.read(src);
        M3uTable aTable = (M3uTable) reader.getTable();
        tim.timerPeriod("table created");
        M3uWriter writer = new M3uWriter();
        writer.setSongArrList(aTable.getSongArrList());
        writer.write(src3, true);
        tim.timerPeriod("written");
        AbstractPlaylistTable bTable = new M3u8Table(aTable);
    }
}
