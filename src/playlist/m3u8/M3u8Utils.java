package local.m3u8;

import local.generic.SupportedMeta;

public class M3u8Utils {
    
    public static SupportedMeta[] getSupportedMeta() {
        SupportedMeta[] rtrMeta = {SupportedMeta.SRC,SupportedMeta.ARTIST,SupportedMeta.TRACKTITLE,SupportedMeta.LENGTH};
        return rtrMeta;
    }

}
