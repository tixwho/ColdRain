package test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import exception.MetaIOException;
import exception.NativeReflectionException;
import exception.PlaylistIOException;
import local.generic.AbstractPlaylistReader;
import local.generic.AbstractPlaylistSong;
import local.m3u.M3uReader;
import local.m3u.M3uTable;
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

  
            for (String path:folderList) {
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
        for (AbstractPlaylistSong songIn:arr) {
            LogMaker.logs("songIn: " + songIn.getSrc());
        }
        
        tim.timerPeriod();
        try {
            Method met = AbstractPlaylistSong.class.getMethod("getSrc");
            LogMaker.logs("retrieved!");
        }catch(NoSuchMethodException nse) {
            nse.printStackTrace();
        }
        tim.timerEnd();
    }

}
