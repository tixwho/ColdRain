package com.coldrain.apps.testApps;

import com.coldrain.exception.MetaIOException;
import com.coldrain.exception.NativeReflectionException;
import com.coldrain.exception.PlaylistIOException;
import com.coldrain.playlist.generic.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class PlaylistIOTest extends BaseLocalTestingClass {

    public static void main(String[] args)
        throws PlaylistIOException, NativeReflectionException, MetaIOException {
        PlaylistIOTest me = new PlaylistIOTest();
        me.setAllLevel("debug");

        String sourceSrc = "E:\\program files\\foobar2000\\playlists";
        Collection<File> playlistFiles = FileUtils.listFiles(new File(sourceSrc),
            SupportedPlaylistFormat.getSupportedPlaylistArray(), true);
        for(File pFile: playlistFiles) {
            AbstractPlaylistTable theTable = PlaylistFileIO.readPlaylist(pFile);
            logger.error("READING PLAYLISTFILE "+theTable.getPlaylistSrc());
            ArrayList<AbstractPlaylistSong> arr =theTable.getSongArrList();
            for(AbstractPlaylistSong theSong: arr) {
                logger.warn("playlistSong:"+theSong.getSrc());
            }
        }

    }
}
