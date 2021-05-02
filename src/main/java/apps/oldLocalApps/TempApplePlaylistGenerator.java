package apps.oldLocalApps;

import old.localModels.M3UTable;
import toolkit.NewFileWriter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

//temp是因为数据库化之后就不需要了，不过这里哪个app不是这样呢(
//会从"\Discography"开始往后截路径，并且把"\"都转换成"/" 有优化空间，不过对我是够用了
public class TempApplePlaylistGenerator {

    public static void main(String[] args) {
        final String ORIGM3UADDR = args[0];
        final String APPLEM3UADDR = args[1];
        M3UTable all_info_table_m3u;
        try {
            all_info_table_m3u = new M3UTable(ORIGM3UADDR);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException
            | IllegalArgumentException | InvocationTargetException | IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return;
        }
        try {
            NewFileWriter.writeAnAppleM3U(all_info_table_m3u, APPLEM3UADDR);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }
        System.out.println("Loaded!");
        
        System.out.println("Completed! Saved at: " + APPLEM3UADDR);
    }

}
