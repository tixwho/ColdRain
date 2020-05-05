package test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import local.generic.AbstractPlaylistReader;
import local.generic.AbstractPlaylistSong;
import local.m3u.M3uReader;
import old.localModels.M3UTable;
import toolkit.LogMaker;
import toolkit.MethodInvoker;
import toolkit.Timer;

public class GeneralTesting {
    
    //用后既删
    public static void main (String[] args) {
        Timer tim = new Timer();
        AbstractPlaylistReader reader = new M3uReader();
        ArrayList<String> folderList = new ArrayList<String>();
        String[] allowedSuffix = {".m3u"};
        MethodInvoker.singlizeInputR("C:\\Users\\ASUS\\Music\\Dopamine\\Playlists",allowedSuffix,folderList );
        tim.timerStart();
        try {
            for (String path:folderList) {
                reader.read(path);
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException
            | IllegalArgumentException | InvocationTargetException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ArrayList<AbstractPlaylistSong> list = reader.getSongArrList();
        /*
        for (AbstractPlaylistSong aSong: list) {
            LogMaker.logs("Path: "+aSong.getSrc());
        }
        */
        tim.timerPeriod();
        try {
            for (String path:folderList) {
                M3UTable table = new M3UTable(path);
                LogMaker.logs("OnePath");
            }
            
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException
            | IllegalArgumentException | InvocationTargetException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        tim.timerEnd();
    }

}
