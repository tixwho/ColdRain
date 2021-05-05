package com.coldrain.playlist.m3u;

import com.coldrain.exception.MetaIOException;
import com.coldrain.exception.NativeReflectionException;
import com.coldrain.exception.PlaylistIOException;
import com.coldrain.playlist.generic.AbstractPlaylistSong;
import com.coldrain.playlist.generic.AbstractPlaylistTable;
import com.coldrain.playlist.generic.SupportedMeta;
import com.coldrain.playlist.generic.SupportedPlaylistFormat;

import java.util.ArrayList;

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
