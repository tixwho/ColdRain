package old.localModels;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import old.readers.M3U8Reader;
import old.readers.SongReader;

public class M3U8Table {
    
    ArrayList<M3U8Song> playlist = new ArrayList<M3U8Song>();
    
    public M3U8Table(String addr) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // first call M3U8Reader to get an Arraylist packing Strings about Songs
        // then enumerate SongReader(songInfo,"m3u8") to generate M3U8Song object, pack into Arraylist
        M3U8Reader fileReader = new M3U8Reader(addr);
        ArrayList<String> dividedStrList = fileReader.getStrInfoList();
        for (String songInfo :dividedStrList) {
            SongReader singleReader = new SongReader(songInfo,"m3u8");
            M3U8Song packedSong = singleReader.getM3U8Song();
            playlist.add(packedSong);
        }
    }
    
    public M3U8Table(ArrayList<M3U8Song> songArrList) {
        this.playlist = songArrList;
    }
    
    public ArrayList<M3U8Song> getSongArrrayList(){
        return playlist;
    }

}
