package com.coldrain.old.readers;

import com.coldrain.old.localModels.M3U8Song;
import com.coldrain.old.localModels.M3USong;
import com.coldrain.old.localModels.ZplSong;
import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

// ZplMediaReader 输入一个mediaInfo，返回一个包装好的ZplSong
public class SongReader {

    public Object songInfo;


    // Use of "Element" clsss indicates that this is an xml-style file.
    public SongReader(Element mediaInfo)
        throws DocumentException, NoSuchMethodException, SecurityException, IllegalAccessException,
        IllegalArgumentException, InvocationTargetException {

        songInfo = new ZplSong();

        // 遍历每个media的attribute
        List<Attribute> attributes = mediaInfo.attributes();
        for (Attribute soloAttr : attributes) {
            System.out.println(soloAttr.getName() + " :" + soloAttr.getValue());
            writeProperty(soloAttr.getName(), soloAttr.getValue());
        }
    }

    // Overloaded: generate various song based on input String
    // typeIdentifier="m3u8": M3U8Song
    public SongReader(String songPart, String typeIdentifier)
        throws NoSuchMethodException, SecurityException, IllegalAccessException,
        IllegalArgumentException, InvocationTargetException {
        if (typeIdentifier == "m3u8") {
            songInfo = new M3U8Song();
            String[] linedSongInfo = songPart.split("/r/n");
            System.out.println("TEST in SongReader " + linedSongInfo[0]);// todo
            for (String line : linedSongInfo) {
                if (line.contains("#EXTINF:")) {
                    line = line.replace("#EXTINF:", "");// DELETE EXTINF
                    String[] splittedInfoLine = line.split(",");
                    for (int i = 0; i < splittedInfoLine.length; i++) {
                        switch (String.valueOf(i)) {
                            case "0":
                                writeProperty("unknownNum", splittedInfoLine[i]);
                                break;
                            case "1":
                                writeProperty("trackTitle", splittedInfoLine[i]);
                        }
                    }
                } else { // If line not containing #EXTINF
                    writeProperty("src", line);
                }
            }

        } else if (typeIdentifier == "m3u") {
            songInfo = new M3USong();
            writeProperty("src", songPart);

        }
    }


    // 使用反射获取getter和setter
    private void writeProperty(String property_name, String property_value)
        throws NoSuchMethodException, SecurityException, IllegalAccessException,
        IllegalArgumentException, InvocationTargetException {
        Method setMethod = getSetMethod(property_name);
        setMethod.invoke(songInfo, property_value);


    }

    private Method getSetMethod(String property_name)
        throws NoSuchMethodException, SecurityException {
        String methodName =
            "set" + property_name.substring(0, 1).toUpperCase() + property_name.substring(1);
        // testing method for reflcet
        //System.out.println("TEST in SongReader MethodName: " + methodName);
        Method gotSetMethod = songInfo.getClass().getMethod(methodName, String.class); // 逗号后相当于给一个固定类型的参
        return gotSetMethod;

    }



    public ZplSong getZplSong() {
        return (ZplSong) songInfo;
    }

    public M3U8Song getM3U8Song() {
        return (M3U8Song) songInfo;
    }

    public M3USong getM3USong() {
        return (M3USong) songInfo;
    }


}
