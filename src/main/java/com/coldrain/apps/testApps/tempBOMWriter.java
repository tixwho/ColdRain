package com.coldrain.apps.testApps;

import com.coldrain.exception.MetaIOException;
import com.coldrain.exception.NativeReflectionException;
import com.coldrain.exception.PlaylistIOException;
import com.coldrain.playlist.generic.AbstractPlaylistReader;
import com.coldrain.playlist.generic.AbstractPlaylistWriter;
import com.coldrain.playlist.generic.BaseLocalTestingClass;
import com.coldrain.playlist.m3u.M3uReader;
import com.coldrain.playlist.m3u.M3uTable;
import com.coldrain.playlist.m3u.M3uWriter;
import com.coldrain.toolkit.MethodInvoker;

import java.io.File;
import java.util.ArrayList;

public class tempBOMWriter extends BaseLocalTestingClass {
    
    public static void main(String[] args) throws PlaylistIOException, NativeReflectionException, MetaIOException {
        tim.timerStart();
        tempBOMWriter testInstance = new tempBOMWriter();
        testInstance.setAllLevel("debug");
        /*
        String src = "C:\\Users\\ASUS\\Music\\Dopamine\\Playlists\\RevueStarlight! OST.m3u";
        String src2 = "C:\\Users\\ASUS\\Music\\Dopamine\\Playlists\\Rayons.m3u";
        String src3 ="E:\\lzx\\Discovery\\ColdRain\\Playlist_noBOM.m3u";
        String src4 ="E:\\lzx\\Discovery\\ColdRain\\Playlist_BOM.m3u";
        
        AbstractPlaylistReader reader = new M3uReader();
        reader.read(src);
        M3uTable aTable =(M3uTable) reader.getTable();
        tim.timerPeriod("table created");
        M3uWriter writer = new M3uWriter();
        writer.setSongArrList(aTable.getSongArrList());
        writer.write(src3, true);
        tim.timerPeriod("written");
        AbstractPlaylistTable bTable = new M3u8Table(aTable);
        */
        String src = "C:\\Users\\ASUS\\Music\\Dopamine\\Playlists";
        String[] allowedSuffix = {".m3u"};
        ArrayList<String> m3ufilesLoc = new ArrayList<String>();
        MethodInvoker.singlizeInputR(src,allowedSuffix,m3ufilesLoc);
        //com.coldrain.test
        int i = 0;
        AbstractPlaylistReader reader = new M3uReader();
        AbstractPlaylistWriter writer = new M3uWriter();
        for (String loc: m3ufilesLoc) {
            System.out.println("loc: "+loc);
            reader.read(new File(loc));
            M3uTable aTable = (M3uTable) reader.getTable();
            writer.setSongArrList(aTable.getSongArrList());
            writer.write(new File(loc),true);
            System.out.println("written:"+loc);
            i+=1;
        }
        System.out.println("m3ufiles count:"+i);
        tim.timerEnd("That's all");
    }

}
