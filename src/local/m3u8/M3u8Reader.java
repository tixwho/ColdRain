package local.m3u8;

import exception.NativeReflectionException;
import exception.PlaylistIOException;
import local.generic.AbstractPlaylistReader;
import local.generic.AbstractPlaylistTable;

public class M3u8Reader extends AbstractPlaylistReader {
    

    @Override
    protected void initializeMeta() {
        super.setSupportedMeta(M3u8Utils.getSupportedMeta());

    }

    @Override
    protected void readHeading() {
        // TODO Auto-generated method stub
        //if #EXTINF do normal read
        //if not, notify

    }

    @Override
    protected void readASong() throws NativeReflectionException {
        // TODO Auto-generated method stub

    }

    @Override
    protected void readEnding() {
        // TODO Auto-generated method stub

    }

    @Override
    public AbstractPlaylistTable getTable() throws PlaylistIOException {
        // TODO Auto-generated method stub
        return null;
    }

}
