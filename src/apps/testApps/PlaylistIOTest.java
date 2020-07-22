package apps.testApps;

import java.util.ArrayList;
import exception.MetaIOException;
import exception.NativeReflectionException;
import exception.PlaylistIOException;
import local.generic.AbstractPlaylistReader;
import local.generic.AbstractPlaylistSong;
import local.generic.BaseLocalTestingClass;
import local.m3u.M3uReader;
import local.m3u.M3uTable;
import local.m3u8.M3u8Reader;
import local.m3u8.M3u8Song;
import local.m3u8.M3u8Table;
import local.m3u8.M3u8Writer;

public class PlaylistIOTest extends BaseLocalTestingClass {

    public static void main(String[] args)
        throws PlaylistIOException, NativeReflectionException, MetaIOException {
        tim.timerStart();
        String src = "C:\\Users\\ASUS\\Music\\Dopamine\\Playlists\\RevueStarlight! OST.m3u";
        String src3 = "E:\\lzx\\Discovery\\ColdRain\\Playlist_now.m3u";

        AbstractPlaylistReader reader = new M3uReader();
        reader.read(src);
        M3uTable aTable = (M3uTable) reader.getTable();
        M3u8Table bTable = new M3u8Table(aTable);
        tim.timerPeriod("table created");
        M3u8Writer writer = new M3u8Writer();
        writer.setSongArrList(bTable.getSongArrList());
        writer.write(src3, true);
        tim.timerPeriod("written");
        reader = new M3u8Reader();
        reader.read(src3);
        ArrayList<AbstractPlaylistSong> aArrList = reader.getSongArrList();
        for(AbstractPlaylistSong eachSong:aArrList) {
            M3u8Song mSong = (M3u8Song)eachSong;
            System.out.println(mSong);
        }
    }
}
