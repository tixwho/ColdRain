package com.coldrain.playlist.generic;

import com.coldrain.exception.MetaIOException;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class MetaSongTest {

    static File folder = new File("E:\\lzx\\Discovery\\ColdRain\\SimpDiscography\\Album5-artfix");
    static File mp3File = new File(folder,"初弦__ - 「遗尘漫步」主界面.mp3");
    static File flacFile = new File(folder, "eufonius - きみがいた.flac");
    static File imgFile = new File(folder, "amy.jpg");
    static File copiedMp3File = new File(folder,"1.mp3");
    static File copiedFlacFile = new File(folder, "2.flac");

    @BeforeAll
    static void prepareFiles() throws IOException {
        System.out.println("Preparing Files");
        FileUtils.copyFile(mp3File,copiedMp3File);
        FileUtils.copyFile(flacFile,copiedFlacFile);
    }

    @Test
    void test1() throws MetaIOException {
        System.out.println("Updating Arts");
        MetaSong mp3Meta = new MetaSong(copiedMp3File.getAbsolutePath());
        mp3Meta.forceUpdateAlbumArt(imgFile);
        MetaSong flacMeta = new MetaSong(copiedFlacFile.getAbsolutePath());
        flacMeta.forceUpdateAlbumArt(imgFile);
    }


    @AfterAll
    static void destroyFiles(){

    }

}

