package apps.oldLocalApps;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.dom4j.DocumentException;
import old.localModels.M3U8Table;
import old.localModels.M3UTable;
import toolkit.DirMaker;
import toolkit.NewFileWriter;

//最早器黑历史之一 M3U8好像格式很多，我只选了网易云导出工具导出来的那种硬做匹配
//但是能用，先留着 要是有人想回头是岸不用网易云了这就是第一步（话又说回来很多播放器都支持m3u8了...只是
//我这些个玩意儿还是以最简单的m3u为模板
//但是到了数据库这些就都不需要了，可喜可贺
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
