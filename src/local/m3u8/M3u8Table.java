package local.m3u8;

import java.util.ArrayList;
import exception.MetaIOException;
import exception.NativeReflectionException;
import exception.PlaylistIOException;
import local.generic.AbstractPlaylistSong;
import local.generic.AbstractPlaylistTable;

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
        // TODO Auto-generated method stub

    }

    @Override
    protected void initializeMeta() {
        super.setSupportedMeta(M3u8Utils.getSupportedMeta());

    }

    @Override
    protected void initializeSongInstance() {
        super.setCorrespondingSongClass(M3u8Song.class);

    }

}
