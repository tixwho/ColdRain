package test;

import exception.MetaIOException;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.flac.FlacAudioHeader;
import playlist.generic.MetaSong;

public class TestExtendJaudiotagger {

    public static void main(String[] args) throws MetaIOException {
        String loc = "E:\\lzx\\etc\\OST\\Discography\\初音ミクの消失 -Real And Repeat\\Cosmo@暴走p - 初音ミクの消失.flac";
        MetaSong aSong = new MetaSong(loc);
        AudioHeader aoh = aSong.getAof().getAudioHeader();
        if(aoh instanceof FlacAudioHeader) {
            String md5=((FlacAudioHeader) aoh).getMd5();
            System.out.println(md5);
        }
        

    }

}
