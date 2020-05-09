package old.localModels;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import old.readers.M3UReader_old;
import old.readers.SongReader;

public class M3UTable {

    ArrayList<M3USong> playlist = new ArrayList<M3USong>();

    public M3UTable(String addr) throws IOException, NoSuchMethodException, SecurityException,
        IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        M3UReader_old fileReader = new M3UReader_old(addr);
        ArrayList<String> dividedStrList = fileReader.getStrInfoList();
        for (String songInfo : dividedStrList) {
            SongReader singleReader = new SongReader(songInfo, "m3u");
            M3USong packedSong = singleReader.getM3USong();
            playlist.add(packedSong);
        }

    }

    public M3UTable(String addr, String metaIdentifier)
        throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException,
        IllegalArgumentException, InvocationTargetException, CannotReadException, TagException,
        ReadOnlyFileException, InvalidAudioFrameException {
        M3UReader_old fileReader = new M3UReader_old(addr);
        ArrayList<String> dividedStrList = fileReader.getStrInfoList();
        for (String songInfo : dividedStrList) {
            SongReader singleReader = new SongReader(songInfo, "m3u");
            M3USong packedSong = singleReader.getM3USong();
            String src = packedSong.getSrc();
            MetaSong_old mtSong = new MetaSong_old(src);
            packedSong.setMetaSong(mtSong);
            playlist.add(packedSong);
        }

    }

    public M3UTable(M3U8Table m3u8table) throws NoSuchMethodException, SecurityException,
        IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ArrayList<M3U8Song> m3u8ArrList = m3u8table.getSongArrrayList();
        for (M3U8Song song : m3u8ArrList) {
            String fileSrc = song.getSrc();
            SongReader singleReader = new SongReader(fileSrc, "m3u");
            M3USong packedSong = singleReader.getM3USong();
            playlist.add(packedSong);
        }
    }

    public M3UTable(ZplTable zpltable) throws NoSuchMethodException, SecurityException,
        IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ArrayList<ZplSong> zplArrList = zpltable.getSongArraylist();
        for (ZplSong song : zplArrList) {
            String fileSrc = song.getSrc();
            SongReader singleReader = new SongReader(fileSrc, "m3u");
            M3USong packedSong = singleReader.getM3USong();
            playlist.add(packedSong);
        }
    }

    public ArrayList<M3USong> getSongArrrayList() {
        return playlist;
    }
    
    public void setSongArrrayList(ArrayList<M3USong> newlist) {
        this.playlist = newlist;
    }

}
