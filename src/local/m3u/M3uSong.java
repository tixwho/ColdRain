package local.m3u;

import java.text.MessageFormat;
import local.generic.AbstractPlaylistSong;

public class M3uSong extends AbstractPlaylistSong {

    // nothing to add here. M3uSong is the most basic implementation.
    
    @Override
    public String toString() {
        return MessageFormat.format("{0}[src={1}]", new Object[] {
            getClass().getSimpleName(), src});
        
    }

}
