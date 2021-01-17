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
        String md5 = "";
        AudioHeader aoh = meta.getAof().getAudioHeader();
        // now only supports flac.
        if (aoh instanceof FlacAudioHeader) {
            md5 = ((FlacAudioHeader) aoh).getMd5();
        }
        return md5;
    }

    // regardless of music type, calculate md5 and return.
    public static String getAudioMd5Force(MetaSong meta) {
        // we only handle mp3 and flac now
        String md5 = "";
        AudioFile aof = meta.getAof();
        if (aof instanceof MP3File) {
            try {
                StringBuffer buf = new StringBuffer("");
                byte[] b = ((MP3File) aof).getHash();
                int i;
                for (int offset = 0; offset < b.length; offset++) {
                    i = b[offset];
                    if (i < 0) {
                        i += 256;
                    }
                    if (i < 16) {
                        buf.append("0");
                    } else {
                        buf.append(Integer.toHexString(i));
                    }
                }
            md5=buf.toString();    
            } catch (NoSuchAlgorithmException | InvalidAudioFrameException | IOException e) {
                e.printStackTrace();
            }
        } else {
            md5 = getFlacAudioMd5(meta);
        }
        return md5;
    }
}
