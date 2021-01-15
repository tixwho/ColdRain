package toolkit;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.flac.FlacAudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import local.generic.MetaSong;

public class AudioMd5Helper {
    
    public static String getFlacAudioMd5(MetaSong meta) {
        String md5="";
        AudioHeader aoh = meta.getAof().getAudioHeader();
        //now only supports flac.
        if(aoh instanceof FlacAudioHeader) {
            md5=((FlacAudioHeader) aoh).getMd5();
        }
        return md5;
    }

    //regardless of music type, calculate md5 and return.
    public static String getAudioMd5Force(MetaSong meta) {
        //we only handle mp3 and flac now
        String md5 = null;
        AudioFile aof = meta.getAof();
        if(aof instanceof MP3File) {
            try {
                md5= new String(((MP3File)aof).getHash());
            } catch (NoSuchAlgorithmException | InvalidAudioFrameException | IOException e) {
                e.printStackTrace();
            }
        }else {
            md5=getFlacAudioMd5(meta);
        }
        return md5;
    }
}
