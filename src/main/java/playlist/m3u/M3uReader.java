package playlist.m3u;

import exception.NativeReflectionException;
import exception.PlaylistIOException;
import playlist.generic.AbstractPlaylistReader;
import playlist.generic.AbstractPlaylistTable;

public class M3uReader extends AbstractPlaylistReader {
    String src=null;

    @Override
    public AbstractPlaylistTable getTable() throws PlaylistIOException {
        M3uTable rtrTable = new M3uTable(songArrList);
        rtrTable.setPlaylistSrc(this.playlistSrc);
        return rtrTable;
    }

    @Override
    protected void readHeading() {
        //not supported for m3u
        
    }

    @Override
    protected void readASong() throws NativeReflectionException {
        M3uSong aSong = new M3uSong();
        src = tempData;
        // move everything beneath to playlist.generic
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

    @Override
    protected void resetEntity() {
        songArrList.clear();// flush before use.
        src=null;
        
    }

}
