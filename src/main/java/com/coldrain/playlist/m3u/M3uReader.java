package com.coldrain.playlist.m3u;

import com.coldrain.exception.NativeReflectionException;
import com.coldrain.exception.PlaylistIOException;
import com.coldrain.playlist.generic.AbstractPlaylistReader;
import com.coldrain.playlist.generic.AbstractPlaylistTable;

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
        // move everything beneath to com.coldrain.playlist.generic
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
