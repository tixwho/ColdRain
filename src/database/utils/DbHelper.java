package database.utils;

import java.io.File;
import playlist.generic.MetaSong;

public class DbHelper {
    
    public static long calcLastModTimestamp(MetaSong meta) {
        File f = new File(meta.getSrc());
        return f.lastModified();
    }
    
    public static long calcLastModTimestamp(String src) {
        File f = new File(src);
        return f.lastModified();
    }
    
    public static Integer calcAudioQuality(MetaSong meta) {
        Integer rtrQuality = 0;
        String[] formatDiv = processAudioProperty(meta);
        //file format
        switch(formatDiv[0]) {
            case"FLAC":
                rtrQuality+=2000;
                break;
            case"MPEG-1":
                rtrQuality+=1000;
                break;
                
        }
        //bitdepth
        switch(formatDiv[1]) {
            case"32 bits":
                rtrQuality+=300;
                break;
            case"24 bits":
                rtrQuality+=200;
                break;
            case"16 bits":
                rtrQuality+=100;
                break;
            case"Layer 3":
                int bitRate = Integer.parseInt(meta.getBITRATE());
                if(bitRate<=128) {
                    rtrQuality+=100;
                }else if(bitRate>128&&bitRate<=192) {
                    rtrQuality+=200;
                }else if(bitRate>192&&bitRate<=256) {
                    rtrQuality+=200;
                }else if(bitRate>256&&bitRate<320) {
                    rtrQuality+=300;
                }else if(bitRate==320) {
                    rtrQuality+=400;
                }
        }
        //sampleRate
        int srInt = Integer.parseInt(meta.getSAMPLERATE());
        if(srInt>=88200) {
            rtrQuality+=30;
        }else if (srInt>=48000) {
            rtrQuality+=20;
        }else {
            rtrQuality+=10;
        }
        //left one column for future use.
        return rtrQuality;
        
        
    }
    
    private static String[] processAudioProperty(MetaSong meta) {
        String format = meta.getFORMAT();
        String[] formatDiv = format.split(" ", 2);
        return formatDiv;
    }

}
