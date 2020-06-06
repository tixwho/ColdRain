package local.m3u;

import java.util.ArrayList;
import exception.MetaIOException;
import exception.NativeReflectionException;
import exception.PlaylistIOException;
import local.generic.AbstractPlaylistSong;
import local.generic.AbstractPlaylistTable;
import local.generic.SupportedMeta;

public class M3uTable extends AbstractPlaylistTable {

    public M3uTable() {
        super();
    }

    public M3uTable(ArrayList<AbstractPlaylistSong> songArrList) throws PlaylistIOException {
        super(songArrList);
    }

    @Override
    public void setInfoFromTable(AbstractPlaylistTable foreignTable)
        throws NativeReflectionException, MetaIOException {
        SupportedMeta[] foreignSuppMeta = foreignTable.getSupportedMeta();
        ArrayList<AbstractPlaylistSong> foreignArr = foreignTable.getSongArrList();
        super.setDesiredSongArrList(M3uSong.class, this.getSupportedMeta(), foreignSuppMeta,
            foreignArr);
        logger.debug("converted M3U Table from:"+foreignTable.getClass());
    }

    @Override
    protected void initializeMeta() {
        super.setSupportedMeta(M3uUtils.getSupportedMeta());
        
    }

    @Override
    protected void initializeSongInstance() {
        super.setCorrespondingSongClass(M3uSong.class);
        
    }



}
