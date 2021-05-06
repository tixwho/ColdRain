package com.coldrain.ncm.utils;

import com.coldrain.ncm.models.NcmAudioInfoComp;

import java.io.*;
import java.net.URL;

public class NcmTempFileUtil {

    public static File cacheDJImg(NcmAudioInfoComp djAudioInfo) throws IOException{

        File tempImgFile=null;

            //retrieve picture and write to tempfile
            URL imgurl = new URL(djAudioInfo.getDj_coverUrl());
            DataInputStream dataInputStream = new DataInputStream(imgurl.openStream());
            tempImgFile = File.createTempFile("DJIMG_", ".jpg", initTempPath());
            FileOutputStream fileOutputStream = new FileOutputStream(tempImgFile);
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            byte[] context=output.toByteArray();
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();

        return tempImgFile;
    }


    private static File initTempPath(){
        File tempPath = new File("com/coldrain/temp");
        if(!tempPath.exists() || !tempPath.isDirectory()) {
            tempPath.mkdir(); //如果不存在，则创建该文件夹
         }
        return tempPath;
    }
}
