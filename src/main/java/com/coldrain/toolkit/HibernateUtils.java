package com.coldrain.toolkit;

public class HibernateUtils {
    
    public static String[] splitFormat(String origFormat) {
        return origFormat.split(" ",2);
    }
    
    public static String acquireAlbumArtist(String albumArtist, String artist) {
        if(albumArtist==null || albumArtist=="") {
            return artist;
        }
        return albumArtist;
    }

}
