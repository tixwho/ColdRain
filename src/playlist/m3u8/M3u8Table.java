package playlist.m3u8;

import java.util.ArrayList;
import exception.MetaIOException;
import exception.NativeReflectionException;
import exception.PlaylistIOException;
import playlist.generic.AbstractPlaylistSong;
import playlist.generic.AbstractPlaylistTable;
import playlist.generic.SupportedMeta;
import playlist.generic.SupportedPlaylistFormat;

public class M3u8Table extends AbstractPlaylistTable {
    
    public M3u8Table() {
        super();
    }

    public M3u8Table(ArrayList<AbstractPlaylistSong> songArrList) throws PlaylistIOException {
        super(songArrList);
    }
    
    public M3u8Table(AbstractPlaylistTable aTable) throws NativeReflectionException, MetaIOException {
        super(aTable);
    }
    

    @Override
    public void setInfoFromTable(AbstractPlaylistTable foreignTable)
        throws NativeReflectionException, MetaIOException {
        SupportedMeta[] foreignSuppMeta = foreignTable.getSupportedMeta();
        ArrayList<AbstractPlaylistSong> foreignArr = foreignTable.getSongArrList();
        super.setDesiredSongArrList(M3u8Song.class, this.getSupportedMeta(), foreignSuppMeta,
            foreignArr);
        logger.debug("converted M3U8 Table from:"+foreignTable.getClass());

    }

    @Override
    protected void initializeMeta() {
        super.setSupportedMeta(M3u8Utils.getSupportedMeta());
        super.setSuppFormat(SupportedPlaylistFormat.M3U8);

    }

    @Override
    protected void initializeSongInstance() {
        super.setCorrespondingSongClass(M3u8Song.class);

    }

}
