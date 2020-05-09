package local.m3u;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import exception.MetaIOException;
import exception.NativeReflectionException;
import local.generic.AbstractPlaylistSong;
import local.generic.AbstractPlaylistTable;
import local.generic.SupportedMeta;

public class M3uTable extends AbstractPlaylistTable {

    public M3uTable() {
        setSupportedMeta(M3uUtils.getSupportedMeta());
    }

    public M3uTable(ArrayList<AbstractPlaylistSong> songArrList, SupportedMeta[] suppMeta) {
        super(songArrList, suppMeta);
    }

    @Override
    public void setInfoFromTable(AbstractPlaylistTable foreignTable)
        throws NativeReflectionException, MetaIOException {
        SupportedMeta[] foreignSuppMeta = foreignTable.getSupportedMeta();
        ArrayList<AbstractPlaylistSong> foreignArr = foreignTable.getSongArrList();
        super.setDesiredSongArrList(M3uSong.class, this.getSupportedMeta(), foreignSuppMeta,
            foreignArr);

    }



}
