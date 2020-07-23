package local.m3u;

import local.generic.AbstractPlaylistSong;

public class M3uSong extends AbstractPlaylistSong {

    // nothing to add here. M3uSong is the most basic implementation.
    
    @Override
    public String toString() {
        return ("M3uSong[src:"+src+"]");
    }

}
