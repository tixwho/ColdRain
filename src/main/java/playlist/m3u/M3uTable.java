package playlist.m3u;

import java.util.ArrayList;
import exception.MetaIOException;
import exception.NativeReflectionException;
import exception.PlaylistIOException;
import playlist.generic.AbstractPlaylistSong;
import playlist.generic.AbstractPlaylistTable;
import playlist.generic.SupportedMeta;
import playlist.generic.SupportedPlaylistFormat;

public class M3uTable extends AbstractPlaylistTable {

    public M3uTable() {
        super();
    }

    public M3uTable(ArrayList<AbstractPlaylistSong> songArrList) throws PlaylistIOException {
        super(songArrList);
    }
    
    public M3uTable(AbstractPlaylistTable unknownTable) throws NativeReflectionException, MetaIOException {
        super(unknownTable);
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
        super.setSuppFormat(SupportedPlaylistFormat.M3U);
        
    }

    @Override
    protected void initializeSongInstance() {
        super.setCorrespondingSongClass(M3uSong.class);
        
    }



}
