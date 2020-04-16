package localApps;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.dom4j.DocumentException;
import tables.M3U8Table;
import tables.M3UTable;
import toolkit.DirMaker;
import toolkit.NewFileWriter;

public class M3U8_2_M3U {
 // NOTICE: args already set up in Run-Run Configurations-Arguments
    public static void generateAPlaylist(String address)
        throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException,
        IllegalArgumentException, InvocationTargetException, DocumentException {
        String outputAddr = DirMaker.mkDirIfNotExist(address, ".m3u");
        M3U8Table all_info_table_m3u8 = new M3U8Table(address);
        M3UTable all_info_table_m3u = new M3UTable(all_info_table_m3u8);
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
                    String fileName = f.getAbsolutePath();
                    String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
                    if (".m3u8".compareTo(fileSuffix)==0) {
                        generateAPlaylist(String.valueOf(f));
                    }
                    else {
                        System.out.println(fileName + " is not a m3u8 File!");
                    }
                    
                }
            }
        } else {
            generateAPlaylist(addr);
        }

    }

}
