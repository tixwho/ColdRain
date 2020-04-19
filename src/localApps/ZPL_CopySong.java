package localApps;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.dom4j.DocumentException;
import tables.ZplSong;
import tables.ZplTable;
import toolkit.DirMaker;

//用zpl去复制歌，算是对文件读写的尝试；现在没啥卵用
public class ZPL_CopySong {

    public static void copySongFromAZplTable(String address) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException, IOException {
        String outputAddr = DirMaker.mkDirIfNotExist(address);
        ZplTable all_info_table = new ZplTable(address);
        ArrayList<ZplSong> songArrlist = all_info_table.getSongArraylist();
        System.out.println("Loaded!");
        for (ZplSong song : songArrlist) {
            String inputLocation = song.getSrc();
            System.out.println("Trace: inputLocation: " +inputLocation);
           
            File inputFile = new File(inputLocation);
            File outputFile = new File(outputAddr,inputFile.getName());
            FileInputStream is = new FileInputStream(inputFile);
            FileOutputStream os = new FileOutputStream(outputFile);
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            byte[]buffer = new byte[8192];
            int count = bis.read(buffer);
            while(count != -1){
                //使用缓冲流写数据
                bos.write(buffer,0,count);
                //刷新
                bos.flush();
                count = bis.read(buffer);
            }
            is.close();
            os.close();
            System.out.println("Saved "+ String.valueOf(outputFile));
        
        }


        


    }

    public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException, IOException {
        String addr = args[0];
        File checkAddr = new File(addr);
        if (checkAddr.isDirectory()) {
            String s[] = checkAddr.list();
            for (int i = 0; i < s.length; i++) {
                File f = new File(addr, s[i]);
                if (f.isDirectory()) {
                    continue;
                } else {
                    copySongFromAZplTable(String.valueOf(f));
                }
            }
        } else {
            copySongFromAZplTable(addr);
        }


    }

}
