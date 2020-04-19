package localApps;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.TagException;
import tables.MetaSong;
import toolkit.LogMaker;
import toolkit.MethodInvoker;

//类似一次性程序，用来从文件名读取metadata然后写进去
public class MetaFromName {

    public static void generateAMeta(String audioAddr,String divider) throws CannotReadException, IOException,
        TagException, ReadOnlyFileException, InvalidAudioFrameException, KeyNotFoundException, CannotWriteException {
        MetaSong aSong = new MetaSong(audioAddr);
        File inpFile = new File(audioAddr);
        String nameWithSuffix = inpFile.getName();
        String pureFilename = nameWithSuffix.substring(0,nameWithSuffix.lastIndexOf("."));
        String toSetTrackNo = pureFilename.substring(0,pureFilename.indexOf(divider));
        String toSetTrackTitle = pureFilename.substring(pureFilename.indexOf(divider)+1);
        LogMaker.logs("nWithSuffix: "+nameWithSuffix);
        LogMaker.logs("pureFilename: "+pureFilename);
        LogMaker.logs("trackNo: "+toSetTrackNo);
        LogMaker.logs("trackTitle: "+toSetTrackTitle);
        aSong.setTrackNo(toSetTrackNo);
        aSong.setTrackTitle(toSetTrackTitle);
        aSong.writeMetaToFile();
    }


    public static void main(String[] args) {
        // 1. Get basic info.
        // Possible universal heading:
        // basic info collection
        String folderAddr = args[0];
        String divider = args[1];
        String[] allowedAudio = {".flac", ".mp3"};
        LogMaker.logs("srcFolder: " + folderAddr);
        ArrayList<String> sampleAudioList = MethodInvoker.singlizeInput(folderAddr, allowedAudio);
        Iterator<String> audioIt = sampleAudioList.iterator();
        // 2. iterate through audio files and set meta.
        while (audioIt.hasNext()) {
            String singleAddr = audioIt.next();
            try {
                generateAMeta(singleAddr,divider);
            } catch (CannotReadException | IOException | TagException | ReadOnlyFileException
                | InvalidAudioFrameException e) {
                LogMaker.logs("Failure reading metadata from " + singleAddr);
                e.printStackTrace();
                return;
            } catch(KeyNotFoundException | CannotWriteException e2) {
                LogMaker.logs("Failure writing metadata back into "+singleAddr);
                e2.printStackTrace();
                return;
            }
            LogMaker.logs("Complete setting Metadata into "+singleAddr);
        }

    }

}
