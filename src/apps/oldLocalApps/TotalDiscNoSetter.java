package apps.oldLocalApps;

import java.io.IOException;
import java.util.ArrayList;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.TagException;
import old.localModels.MetaSong_old;
import toolkit.LogMaker;
import toolkit.MethodInvoker;
import toolkit.Timer;

// tgf都找不到TotalDisc这个tag，但是dopamine在处理多disc专辑的时候要用...只好写一个了
public class TotalDiscNoSetter {

    public static void main(String[] args) {
        Timer tim = new Timer();
        tim.timerStart();
        String toAddAudioFolderAddr = args[0];
        String toAddTotalDiscDesc = args[1]; // format: 01,02,03...
        String[] allowedSuffix = {".mp3", ".flac"};
        ArrayList<String> audioList = new ArrayList<String>();
        MethodInvoker.singlizeInputR(toAddAudioFolderAddr, allowedSuffix, audioList);
        for (String audioLoc : audioList) {
            try {
                MetaSong_old aMeta = new MetaSong_old(audioLoc);
                //test
                LogMaker.logs(audioLoc+" totalDisc:" +aMeta.getTotalDiscNo());
                aMeta.setTotalDiscNo(toAddTotalDiscDesc);
                aMeta.writeMetaToFile();
                LogMaker.logs("After::"+audioLoc+" totalDisc:" +aMeta.getTotalDiscNo());
            } catch (CannotReadException | IOException | TagException | ReadOnlyFileException
                | InvalidAudioFrameException e) {
                LogMaker.logs("Unable Reading Meta from " + audioLoc);
                e.printStackTrace();
                return;
            } catch (KeyNotFoundException | CannotWriteException e2) {
                LogMaker.logs("Unable to write back meta into" + audioLoc);
                e2.printStackTrace();
                return;
            }
            LogMaker
                .logs("Successfully written TotalDisc " + toAddTotalDiscDesc + "into" + audioLoc);
        }
        tim.timerEnd();
    }

}
