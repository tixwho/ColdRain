package test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.dom4j.DocumentException;
import old.localModels.ZplSong;
import old.localModels.ZplTable;

public class TestZplTable {

    //第一次成功的尝试，可喜可贺！可以看作ZPL2M3U的原型
    public static void main(String[] args)
        throws NoSuchMethodException, SecurityException, IllegalAccessException,
        IllegalArgumentException, InvocationTargetException, DocumentException {
        String addr = "F:\\ASUS\\Music\\Playlists\\ORBIS TERRARVM.zpl";
        ZplTable all_info_table = new ZplTable(addr);
        ArrayList<ZplSong> songArrlist = all_info_table.getSongArraylist();
        System.out.println("Loaded!");
        for (ZplSong songInfo :songArrlist) {
            System.out.println("Title: " + songInfo.getTrackTitle());
        }

    }

}
