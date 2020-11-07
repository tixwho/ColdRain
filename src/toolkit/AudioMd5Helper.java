package toolkit;

import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.flac.FlacAudioHeader;
import local.generic.MetaSong;

public class AudioMd5Helper {
    
    public static String getAudioMd5(MetaSong meta) {
        String md5="";
        AudioHeader aoh = meta.getAof().getAudioHeader();
        //now only supports flac.
        if(aoh instanceof FlacAudioHeader) {
            md5=((FlacAudioHeader) aoh).getMd5();
        }
        return md5;
    }

}
