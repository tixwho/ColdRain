package test;

import exception.MetaIOException;
import playlist.generic.MetaSong;

import java.io.File;

public class Testm4a {

    public static void main(String[] args) throws MetaIOException {
        String loc = "F:\\Discography\\Wonder Egg Priority\\巣立ちの歌\\001-巣立ちの歌.m4a";
        String loc2 = "F:\\Discography\\Wonder Egg Priority\\Life is サイダー\\001-Life is サイダー.m4a";
        File nestFile = new File(loc);
        System.out.println("fileHere:"+nestFile.exists());
        MetaSong nestMeta = new MetaSong(loc2);
        System.out.println(nestMeta);

    }

}
