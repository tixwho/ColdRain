package local.m3u;

import exception.NativeReflectionException;
import exception.PlaylistIOException;
import local.generic.AbstractPlaylistReader;
import local.generic.AbstractPlaylistTable;

public class M3uReader extends AbstractPlaylistReader {
    String src;

    @Override
    public AbstractPlaylistTable getTable() throws PlaylistIOException {

        return new M3uTable(songArrList);
    }

    @Override
    protected void readHeading() {
        //not supported for m3u
        
    }

    @Override
    protected void readASong() throws NativeReflectionException {
        M3uSong aSong = new M3uSong();
        src = tempData;
        // move everything beneath to generic
        super.setAllProperties(this, aSong);
        super.songArrList.add(aSong);
        //universal reader can be used in child class
        logger.trace("Song Instance added:"+aSong.getSrc());
    }

    @Override
    protected void readEnding() {
        //not supported for m3u
        
    }

    @Override
    protected void initializeMeta() {
        this.setSupportedMeta(M3uUtils.getSupportedMeta());
        
    }

}
