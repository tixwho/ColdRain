package test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import org.slf4j.Logger;
import exception.MetaIOException;
import exception.NativeReflectionException;
import exception.PlaylistIOException;
import local.generic.AbstractPlaylistReader;
import local.generic.AbstractPlaylistSong;
import local.generic.BaseTestingClass;
import local.m3u.M3uReader;
import local.m3u.M3uTable;
import toolkit.LogMaker;
import toolkit.MethodInvoker;

public class GeneralTesting extends BaseTestingClass{
    // 用后既删
    public static void main(String[] args) {
        GeneralTesting me = new GeneralTesting();
        me.selfCtrl.setLevel("error");
        me.readerCtrl.setLevel("error");
        AbstractPlaylistReader reader = new M3uReader();
        ArrayList<String> folderList = new ArrayList<String>();
        String[] allowedSuffix = {".m3u"};
        MethodInvoker.singlizeInputR("C:\\Users\\ASUS\\Music\\Dopamine\\Playlists\\Abyss.m3u", allowedSuffix,
            folderList);
        tim.timerStart();


        for (String path : folderList) {
            try {
                reader.read(path);
            } catch (PlaylistIOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NativeReflectionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        M3uTable testTable = (M3uTable) reader.getTable();
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
            logger.info("songIn: "+songIn.getSrc());
        }

        tim.timerPeriod();
        try {
            Method met = AbstractPlaylistSong.class.getMethod("getSrc");
            LogMaker.logs("retrieved!");
        } catch (NoSuchMethodException nse) {
            nse.printStackTrace();
        }
        tim.timerEnd();
    }

}
