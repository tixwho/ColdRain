package toolkit;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.flac.FlacAudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import playlist.generic.MetaSong;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class AudioMd5Helper {

    //read flac md5 from flac heading info. not calculating actual media.
    public static String getFlacAudioMd5(MetaSong meta) {
        String md5 = "";
        AudioHeader aoh = meta.getAof().getAudioHeader();
        // now only supports flac.
        if (aoh instanceof FlacAudioHeader) {
            md5 = ((FlacAudioHeader) aoh).getMd5();
        }
        return md5;
    }

    //calculate mp3 md5; if exception occured, return empty string.
    public static String getMp3AudioMd5(MetaSong meta) {

        StringBuffer buf = new StringBuffer();
        byte[] b;
        try {
            b = ((MP3File) meta.getAof()).getHash();

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
            return buf.toString();
        } catch (NoSuchAlgorithmException | InvalidAudioFrameException | IOException e) {

            e.printStackTrace();
        }
        return "";

    }

    // regardless of music type, calculate md5 and return.
    public static String getAudioMd5Force(MetaSong meta) {
        // we only handle mp3 and flac now
        String md5 = "";
        AudioFile aof = meta.getAof();
        if (aof instanceof MP3File) {
            md5 = getMp3AudioMd5(meta);
        } else {
            md5 = getFlacAudioMd5(meta);
        }
        return md5;
    }
}
