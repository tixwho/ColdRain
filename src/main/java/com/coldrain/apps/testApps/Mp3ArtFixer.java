package com.coldrain.apps.testApps;

import com.coldrain.exception.MetaIOException;
import com.coldrain.playlist.generic.MetaSong;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.images.Artwork;

import java.util.List;

public class Mp3ArtFixer {

    public static void main(String[] args)
        throws MetaIOException, FieldDataInvalidException, CannotWriteException {
        String loc = "F:\\CloudMusic\\伊藤真澄 - ユメのなかノわたしのユメ.mp3";
        readImgStatus(loc);
        System.out.println("------------------");
        loc = "F:\\CloudMusic\\鈴木このみ - THERE IS A REASON.flac";
        readImgStatus(loc);

    }

    public static void readImgStatus(String loc)
        throws MetaIOException, FieldDataInvalidException, CannotWriteException {
        System.out.println("SRC=" + loc);
        MetaSong meta = new MetaSong(loc);
        Tag tag = meta.getAof().getTag();
        List<Artwork> artworkList = tag.getArtworkList();
        boolean flag = false;
        for (Artwork anArt : artworkList) {
            System.out.println("PictureType:" + anArt.getPictureType());
            System.out.println("Description:" + anArt.getDescription());
            System.out.println("ImageUrl:" + anArt.getImageUrl());

            if (anArt.getPictureType() == 6) {
                anArt.setPictureType(3);
                System.out.println("FIXED");
                flag = true;

            }
        }
        
            AudioFileIO.write(meta.getAof());
            System.out.println("written!@");
        
    }

}
