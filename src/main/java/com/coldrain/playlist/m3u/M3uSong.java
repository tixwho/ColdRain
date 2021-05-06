package com.coldrain.playlist.m3u;

import com.coldrain.playlist.generic.AbstractPlaylistSong;

import java.text.MessageFormat;

public class M3uSong extends AbstractPlaylistSong {

    // nothing to add here. M3uSong is the most basic implementation.
    
    @Override
    public String toString() {
        return MessageFormat.format("{0}[src={1}]", getClass().getSimpleName(), src);
        
    }

}
