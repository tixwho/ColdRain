package localApps;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import tables.M3UTable;
import toolkit.NewFileWriter;

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
