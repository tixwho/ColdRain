package test;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import exception.MetaIOException;
import exception.NativeReflectionException;
import exception.PlaylistIOException;
import local.generic.AbstractPlaylistReader;
import local.generic.AbstractPlaylistSong;
import local.generic.BaseLocalTestingClass;
import local.m3u.M3uReader;
import local.m3u.M3uTable;
import local.m3u.M3uWriter;
import toolkit.LogMaker;
import toolkit.MethodInvoker;

public class GeneralTesting extends BaseLocalTestingClass{
    // 用后既删
    public static void main(String[] args) {
        GeneralTesting me = new GeneralTesting();
        me.setAllLevel("debug");
//        me.readerCtrl.setLevel("error");
        AbstractPlaylistReader reader = new M3uReader();
        ArrayList<String> folderList = new ArrayList<String>();
        String[] allowedSuffix = {".m3u"};
        MethodInvoker.singlizeInputR("C:\\Users\\ASUS\\Music\\Dopamine\\Playlists", allowedSuffix,
            folderList);
        tim.timerStart();


        for (String path : folderList) {
            try {
                reader.read(new File(path)  );
            } catch (PlaylistIOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NativeReflectionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        M3uTable testTable;
        try {
            testTable= (M3uTable) reader.getTable();
        } catch (PlaylistIOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return;
        }
        M3uTable testTable2 = new M3uTable();

        try {
            testTable2.setInfoFromTable(testTable);
        } catch (NativeReflectionException e) {

            e.printStackTrace();
        } catch (MetaIOException e) {

            e.printStackTrace();
        }

        ArrayList<AbstractPlaylistSong> arr = testTable2.getSongArrList();
        for (AbstractPlaylistSong songIn : arr) {
            logger.trace("songIn: "+songIn.getSrc());
        }

        tim.timerPeriod("blah");
        try {
            Method met = AbstractPlaylistSong.class.getMethod("getSrc");
            LogMaker.logs("retrieved!");
        } catch (NoSuchMethodException nse) {
            nse.printStackTrace();
        }
        M3uWriter writer = new M3uWriter();
        writer.setSongArrList(testTable2.getSongArrList());
        try {
            writer.write(new File("E:\\lzx\\Discovery\\Absys.m3u"));
        } catch (PlaylistIOException e) {
            e.printStackTrace();
        }
        tim.timerEnd();
    }

}
