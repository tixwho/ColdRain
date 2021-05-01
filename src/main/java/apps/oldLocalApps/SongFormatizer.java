package apps.oldLocalApps;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import old.localModels.M3USong;
import old.localModels.M3UTable;
import old.localModels.MetaSong_old;
import toolkit.LogMaker;
import toolkit.MethodInvoker;
import toolkit.MisUtils;
import toolkit.NewFileWriter;
import toolkit.Timer;

//用metadata写新文件名并更新m3u 格式为 歌手-歌名
//也是第一次让我意识到了windows路径还有不支持某些字符这回事，当时可让我一顿debug，debug了整整两天
public class SongFormatizer {

    public static String[] calcAudioLoc(String audioFileLoc) throws CannotReadException,
        IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
        MetaSong_old singleSongMeta = new MetaSong_old(audioFileLoc);
        String artist = singleSongMeta.getArtist();
        String name = singleSongMeta.getTrackTitle();
        String suffix = MisUtils.getSuffix(audioFileLoc);
        File audioFile = new File(audioFileLoc);
        String parentFolder = audioFile.getParent();
        String newFileName = MisUtils.replaceIlligalFilename(artist.concat(" - ").concat(name).concat(suffix));
        String[] keyVarray = new String[2];
        String finalizedAbsPath = new File(parentFolder,newFileName).toString();
        keyVarray[0]=audioFileLoc;
        keyVarray[1]=finalizedAbsPath;
        return keyVarray;
    }
    
    
    // copied to MisUtils utility class.
    public static void fixAM3U (String M3UAddr, HashMap<String,String>searchMap) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
        M3UTable singleM3UTable;
        singleM3UTable = new M3UTable(M3UAddr);
        ArrayList<M3USong>songArrList = singleM3UTable.getSongArrrayList();
        for (M3USong singleM3USong :songArrList) {
            String src = singleM3USong.getSrc();
            if (searchMap.containsKey(src)) {
                src=searchMap.get(src);
                LogMaker.logs("got!");
                singleM3USong.setSrc(src);
            }
            else{
                LogMaker.logs("Not found!");
            }
        }
        singleM3UTable.setSongArrrayList(songArrList);
        //overwrite original m3u file.
        NewFileWriter.writeAM3U(singleM3UTable, M3UAddr);
    }


    public static void main(String[] args) {
        // basic info collection
        String folderAddr = args[0];
        String m3uAddr = args[1];
        String[] allowedAudio = {".flac", ".mp3"};
        Timer tim = new Timer();
        tim.timerStart();
        LogMaker.logs("srcFolder: " + folderAddr);
        ArrayList<String> toCheckM3Us = MethodInvoker.singlizeInput(m3uAddr, ".m3u");
        ArrayList<String> sampleAudioList = MethodInvoker.singlizeInput(folderAddr, allowedAudio);
        Iterator<String> audioIt = sampleAudioList.iterator();
        // step 1. iterate through all audio file and calculate new name. store in arraylist of
        // String[2];
        HashMap<String,String> toModHashAudLoc = new HashMap<String,String>();
        while (audioIt.hasNext()) {
            try {
                String[] tempArr = calcAudioLoc(audioIt.next());
                toModHashAudLoc.put(tempArr[0], tempArr[1]);
            } catch (CannotReadException | IOException | TagException | ReadOnlyFileException
                | InvalidAudioFrameException e) {
                LogMaker.logs("Failure reading Metadata to Generate New Path.");
                e.printStackTrace();
                return;
            }
        }
        // step 2. iterate through M3U files to fix playlist.        
        for (String singleM3Uaddr : toCheckM3Us) {
            try {
                fixAM3U(singleM3Uaddr,toModHashAudLoc);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException | IOException e) {
                LogMaker.logs("Failure reading M3U from: "+singleM3Uaddr);
                e.printStackTrace();
                return;
            }
            
        }
        
        // step 3. rename audiofile.
        
        Iterator<Entry<String, String>> iterMap = toModHashAudLoc.entrySet().iterator();
        while(iterMap.hasNext()) {
            HashMap.Entry<String,String> entry = (HashMap.Entry<String,String>)iterMap.next();
            MisUtils.renameFile(entry.getKey(), entry.getValue());
        }
        tim.timerEnd();
    }

}
