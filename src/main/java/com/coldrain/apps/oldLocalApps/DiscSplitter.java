package com.coldrain.apps.oldLocalApps;

import com.coldrain.old.localModels.MetaSong_old;
import com.coldrain.toolkit.DirMaker;
import com.coldrain.toolkit.LogMaker;
import com.coldrain.toolkit.MethodInvoker;
import com.coldrain.toolkit.MisUtils;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;


//黑历史系列，等删
public class DiscSplitter {

    public static String[] calcNewAudioLoc(String parentFolderLoc, String audioLoc)
        throws CannotReadException, IOException, TagException, ReadOnlyFileException,
        InvalidAudioFrameException {
        MetaSong_old singleSongMeta = new MetaSong_old(audioLoc);
        String album = singleSongMeta.getAlbum();
        // try generate a new folder for album.
        File possibleNewFolder = new File(parentFolderLoc, MisUtils.replaceIlligalFilename(album));
        boolean splitFlag = DirMaker.mkRawDirIfNotExist(possibleNewFolder.toString());
        if (splitFlag) {
            LogMaker.logs("New Disc Folder at: " + possibleNewFolder);

        }
        File audioFile = new File(audioLoc);
        LogMaker.logs("fileName: " + audioFile.getName());
        String origAlbumFolder = audioFile.getParent();
        if (origAlbumFolder.compareTo(possibleNewFolder.getAbsolutePath()) != 0) {
            String[] keyVarray = new String[2];
            String finalizedAbsPath =
                new File(possibleNewFolder.toString(), audioFile.getName()).toString();
            LogMaker.logs("newPath: " + finalizedAbsPath);
            keyVarray[0] = audioLoc;
            keyVarray[1] = finalizedAbsPath;
            return keyVarray;
        }

        return null;
    }

    public static void main(String[] args) {
        // 1. Get basic info.
        // Possible universal heading:
        // basic info collection
        String folderAddr = args[0];
        String m3uAddr = args[1];
        String[] allowedAudio = {".flac", ".mp3"};
        LogMaker.logs("srcFolder: " + folderAddr);
        LogMaker.logs("m3uAddr: " + m3uAddr);
        ArrayList<String> toCheckM3Us = MethodInvoker.singlizeInput(m3uAddr, ".m3u");
        ArrayList<String> sampleAudioList = MethodInvoker.singlizeInput(folderAddr, allowedAudio);
        HashMap<String, String> toRenameAudioMap = new HashMap<String, String>();
        Iterator<String> audioIt = sampleAudioList.iterator();
        // 2. for each audiofile in folder, examine Album
        // metadata. If album not the same as foldername, try create a new folder, move audiofile to
        // that new folder, and add a keyVpair of original address-new address into arraylist.
        File audioFolder = new File(folderAddr);
        String parentFolder = audioFolder.getParent();
        LogMaker.logs("folder: " + parentFolder); // debug
        while (audioIt.hasNext()) {
            try {
                String[] tempArr = calcNewAudioLoc(parentFolder, audioIt.next());
                if (tempArr != null) {
                    toRenameAudioMap.put(tempArr[0], tempArr[1]);
                    LogMaker.logs("Added: " + tempArr[0]);
                }
            } catch (CannotReadException | IOException | TagException | ReadOnlyFileException
                | InvalidAudioFrameException e) {
                LogMaker.logs("failure reading metadata!");
                e.printStackTrace();
                return;
            }
        }



        // 3. iterate through m3u files, replace com.coldrain.old records with new ones according to map

        for (String singleM3ULoc : toCheckM3Us) {
            try {
                MisUtils.fixAM3U(singleM3ULoc, toRenameAudioMap);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException | IOException e) {
                LogMaker.logs("Failure reading M3U from " + singleM3ULoc);
                e.printStackTrace();
                return;
            }
        }

        // 4. move audio files according to HashMap.
        // com.coldrain.test printing files added.
        Iterator<Entry<String, String>> iterMap = toRenameAudioMap.entrySet().iterator();
        while (iterMap.hasNext()) {
            HashMap.Entry<String, String> entry = iterMap.next();
            MisUtils.renameFile(entry.getKey(), entry.getValue());
        }
        // com.coldrain.test ended.
    }



}
