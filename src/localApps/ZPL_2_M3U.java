package localApps;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.dom4j.DocumentException;
import tables.M3UTable;
import tables.ZplTable;
import toolkit.DirMaker;
import toolkit.NewFileWriter;

//极早期作品，为了把groove音乐的歌单拷出来；根本就没人用zpl吧喂
public class ZPL_2_M3U {

    // NOTICE: args already set up in Run-Run Configurations-Arguments
    public static void generateAPlaylist(String address)
        throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException,
        IllegalArgumentException, InvocationTargetException, DocumentException {
        String outputAddr = DirMaker.mkDirIfNotExist(address, ".m3u");
        ZplTable all_info_table_zpl = new ZplTable(address);
        M3UTable all_info_table_m3u = new M3UTable(all_info_table_zpl);
        NewFileWriter.writeAM3U(all_info_table_m3u, outputAddr);
        System.out.println("Loaded!");
        
        System.out.println("Completed! Saved at: " + outputAddr);
    }

    public static void main(String[] args)
        throws NoSuchMethodException, SecurityException, IllegalAccessException,
        IllegalArgumentException, InvocationTargetException, DocumentException, IOException {
        String addr = args[0];
        File checkAddr = new File(addr);
        if (checkAddr.isDirectory()) {
            String s[] = checkAddr.list();
            for (int i = 0; i < s.length; i++) {
                File f = new File(addr, s[i]);
                if (f.isDirectory()) {
                    continue;
                } else {
                    generateAPlaylist(String.valueOf(f));
                }
            }
        } else {
            generateAPlaylist(addr);
        }

    }

}
