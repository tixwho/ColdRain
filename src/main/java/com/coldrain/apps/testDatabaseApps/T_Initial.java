package com.coldrain.apps.testDatabaseApps;

import com.coldrain.database.generic.BaseDatabaseTestingClass;
import com.coldrain.database.service.AudioDBService;
import com.coldrain.exception.DatabaseException;
import com.coldrain.exception.MetaIOException;
import com.coldrain.exception.NativeReflectionException;
import com.coldrain.exception.PlaylistIOException;
import com.coldrain.playlist.generic.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class T_Initial extends BaseDatabaseTestingClass {

    public static void main(String[] args)
        throws PlaylistIOException, NativeReflectionException, MetaIOException, DatabaseException, IOException {
        T_Initial me = new T_Initial();
        me.setAllLevel("debug");
        BaseLocalTestingClass they = new BaseLocalTestingClass();
        they.setAllLevel("debug");
        String playlist = "E:\\lzx\\Discovery\\ColdRain\\Discography\\DatabasePlaylist.m3u";
        //String com.coldrain.playlist = "E:\\program files\\foobar2000\\playlists\\Casual English.m3u8";
        AbstractPlaylistTable testTable = PlaylistFileIO.readPlaylist(new File(playlist));
        testTable.printAllSong();
        
        boolean isInitializing = true;
        if(isInitializing) {
            setHeteroFile();
        }else {
            setOrthroFile();
        }
        for (AbstractPlaylistSong aSong : testTable.getSongArrList()) {
            MetaSong aMeta = new MetaSong(aSong.getSrc());
            System.out.println("aMeta Date:"+aMeta.getAlbumDate());
            if(isInitializing) {
            AudioDBService.loadNewFile(aMeta);
            }else {
            AudioDBService.updateMetaForFile(aMeta);
            }
            
        }
        if(!isInitializing) {
        AudioDBService.cleanValidationSets();
        }
        
        
    }
    
    public static void setHeteroFile() throws IOException {
        logger.info("Hetero");
        File srcFile = new File("E:\\lzx\\Discovery\\ColdRain\\eufonius - きみがいた.flac");
        File destFile = new File("E:\\lzx\\Discovery\\ColdRain\\Discography\\eufonius - きみがいた.flac");
        FileUtils.copyFile(srcFile, destFile);
    }
    
    public static void setOrthroFile() throws IOException {
        logger.info("Orthro");
        File srcFile = new File("E:\\lzx\\Discovery\\ColdRain\\Discography\\Island オリジナルサウンドトラック\\eufonius - きみがいた.flac");
        File destFile = new File("E:\\lzx\\Discovery\\ColdRain\\Discography\\eufonius - きみがいた.flac");
        FileUtils.copyFile(srcFile, destFile);
    }
    



}
