package test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import old.localModels.M3USong;
import old.localModels.M3UTable;
import old.localModels.MetaSong;
import toolkit.NewFileWriter;

public class SingleUse_IslandFixer {

    public static void main(String[] args) throws NoSuchMethodException, SecurityException,
        IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException,
        CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
        String islandPlaylistAddr = args[0];
        String islandFolderAddr = args[1];
        File islandFolder = new File(islandFolderAddr);
        //debug
        System.out.println("isFolder: "+islandFolder.isDirectory());
        String[] songAddresses = islandFolder.list();
        ArrayList<M3USong> origFileList = new ArrayList<M3USong>();
        
        for (String fileLoc : songAddresses) {
            M3USong aSong = new M3USong();
            aSong.setSrc(fileLoc);
            //debug
            System.out.println("fileLos: "+fileLoc);
            String fullPath = islandFolder.getPath().concat("\\").concat(fileLoc);
            //debug
            System.out.println("fullPath: "+fullPath);
            MetaSong aMetaSong = new MetaSong(fullPath);
            aSong.setMetaSong(aMetaSong);
            origFileList.add(aSong);
        }
        
        M3UTable playlistIslandTable = new M3UTable(islandPlaylistAddr);
        ArrayList<M3USong> playlistSonglist = playlistIslandTable.getSongArrrayList();
        for (M3USong playlistRecord : playlistSonglist) {
            //Collect Filenames
            String fullSrc = playlistRecord.getSrc();
            String fileNameWithSuffix = fullSrc.substring(fullSrc.lastIndexOf("\\")+1);
            String searchIndex = fileNameWithSuffix.substring(0,fileNameWithSuffix.lastIndexOf("_"));
            //debug
            System.out.println("searchingIndex: " + searchIndex);
            //Iterate through songs to find correct src
            for (M3USong correctSong: origFileList) {
                String trackNo = correctSong.getMetaSong().getTrackNo();
                //debug
                System.out.println("trackNo: " + trackNo);
                if (searchIndex.compareTo(trackNo)==0) {
                    playlistRecord.setSrc(correctSong.getMetaSong().getSrc());
                    //debug
                    System.out.println("Set Correct Path: "+correctSong.getMetaSong().getSrc());
                    break;
                }
            }
        }
        playlistIslandTable.setSongArrrayList(playlistSonglist);
        String outputAddr = "E:\\lzx\\etc\\Miscellaneous\\TestOutput\\testIsland.m3u";
        NewFileWriter.writeAM3U(playlistIslandTable, outputAddr);
        
    }

}
