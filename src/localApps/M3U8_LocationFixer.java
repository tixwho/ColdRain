package localApps;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.dom4j.DocumentException;
import tables.M3U8Song;
import tables.M3U8Table;
import toolkit.NewFileWriter;

public class M3U8_LocationFixer {

    public static void fixALocation(String m3u8loc, String newDirLoc)
        throws NoSuchMethodException, SecurityException, IllegalAccessException,
        IllegalArgumentException, InvocationTargetException, IOException {
        // call M3U8Table(location) to generate a new M3U8Table Object, and get Arraylist of
        // M3U8Song
        // with getSongArrayList();
        // for each M3U8Song, get its src and modify to correct location
        // generate new M3U8 file with modified M3U8Song objects.
        M3U8Table playListTable = new M3U8Table(m3u8loc);
        ArrayList<M3U8Song> songList = playListTable.getSongArrrayList();
        boolean isModified = false;
        // try a song to get
        for (M3U8Song song : songList) {
            String tempLoc = song.getSrc();
            if (tempLoc.contains("\\")){
                System.out.println(tempLoc + " Already modified!");
                isModified = true;
                break;
            }
            String origDir = tempLoc.substring(0, tempLoc.indexOf("/"));
            tempLoc = tempLoc.replace(origDir, newDirLoc);
            File tempFile = new File(tempLoc);
            String fileLoc = tempFile.getPath();// use java File class to adjust separators
            song.setSrc(fileLoc);
            // test
            System.out.println("TEST in M3U8_Location_Fixer new src:" + song.getSrc());
            System.out.println("TEST in M3U8_Location_Fixer file src: " + fileLoc);
            // TODO 考虑是不是需要把所有写新文件的操作都包装在一个writer的Package里
        }
        if (!isModified) {
            M3U8Table newPlayListTable = new M3U8Table(songList);
            NewFileWriter.writeAM3U8(newPlayListTable, m3u8loc);// 会覆盖原来的文件
        }


    }


    public static void main(String[] args)
        throws NoSuchMethodException, SecurityException, IllegalAccessException,
        IllegalArgumentException, InvocationTargetException, DocumentException, IOException {
        String addr = args[0];
        String cloudMusicAddr = args[1];
        File checkAddr = new File(addr);
        if (checkAddr.isDirectory()) {
            String s[] = checkAddr.list();
            for (int i = 0; i < s.length; i++) {
                File f = new File(addr, s[i]);
                String fileName = f.getName();            
                System.out.println("FIlename: " + fileName);
                if (f.isDirectory()) {
                    continue;//skip directory in target directory
                } else {
                    String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
                    if (".m3u8".compareTo(fileSuffix)==0) {
                        System.out.println("test: " + fileName +"-" + fileSuffix);
                        fixALocation(String.valueOf(f), cloudMusicAddr);
                        System.out.println("fixed: " + String.valueOf(f));
                    }else {
                        System.out.println("Not a m3u8 File!");
                    }
                }
            }
        } else {
            String fileName = checkAddr.getName();
            String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
            System.out.println("FIlename: " + fileName +"-" +fileSuffix);
            if (".m3u8".compareTo(fileSuffix)==0) {
                fixALocation(addr, cloudMusicAddr);
            }else {
                System.out.println(fileName + " is Not a m3u8 File!");
            }
        }
    }


}
