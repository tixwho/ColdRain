package test;

import old.localModels.M3U8Song;
import old.readers.M3U8Reader;
import old.readers.SongReader;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class TestReadM3U8 {


    public static void main(String[] args) throws DocumentException, IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        M3U8Reader dividedM3U8 = new M3U8Reader(
            "E:\\downloaded_app\\1545974780763-sln\\sln\\ncmexport\\12. olddays.m3u8");
        ArrayList<String>tempList = dividedM3U8.getStrInfoList();
        System.out.println(tempList);
        String testStr = tempList.get(0);
        SongReader testReader = new SongReader(testStr,"m3u8");
        M3U8Song testingM3U8Song = testReader.getM3U8Song();
        System.out.println("unknownNum: " + testingM3U8Song.getUnknownNum());
        System.out.println("trackTitle: " + testingM3U8Song.getTrackTitle());
        System.out.println("src: " + testingM3U8Song.getSrc());
    }

}
