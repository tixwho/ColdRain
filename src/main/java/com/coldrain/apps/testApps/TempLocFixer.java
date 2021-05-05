package com.coldrain.apps.testApps;

import com.coldrain.exception.MetaIOException;
import com.coldrain.exception.NativeReflectionException;
import com.coldrain.exception.PlaylistIOException;
import com.coldrain.playlist.generic.AbstractPlaylistSong;
import com.coldrain.playlist.generic.AbstractPlaylistTable;
import com.coldrain.playlist.generic.PlaylistFileIO;
import com.coldrain.playlist.generic.SupportedPlaylistFormat;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TempLocFixer {
    //rewrite!!

    public static void main(String[] args)
        throws PlaylistIOException, NativeReflectionException, MetaIOException {
        // args1: folder of playlists.
        String sourceSrc = args[0];

        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("E:\\lzx\\etc\\OST\\Discography", "F:\\OST\\0216\\E");
        replaceMap.put("F:\\Discography", "F:\\OST\\0216\\F");
        replaceMap.put("F:\\CloudMusic", "F:\\OST\\0216\\CloudMusic");
        Collection<File> playlistFiles = FileUtils.listFiles(new File(sourceSrc),
            SupportedPlaylistFormat.getSupportedPlaylistArray(), true);
        for (File pFile : playlistFiles) {
            AbstractPlaylistTable theTable = PlaylistFileIO.readPlaylist(pFile);
            System.out.println("READING PLAYLISTFILE " + theTable.getPlaylistSrc());
            ArrayList<AbstractPlaylistSong> arr = theTable.getSongArrList();
            for (AbstractPlaylistSong theSong : arr) {
                String tempSrc = theSong.getSrc();
                for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
                    if (tempSrc.contains(entry.getKey())) {
                        tempSrc = tempSrc.replace(entry.getKey(), entry.getValue());
                        theSong.setSrc(tempSrc);
                        System.out.println("Setting as:"+tempSrc);
                        break;
                    }
                    
                }

            }
            theTable.setSongArrList(arr);
            PlaylistFileIO.writePlaylistAs(theTable, pFile, SupportedPlaylistFormat.M3U, true);
        }

    }

}
