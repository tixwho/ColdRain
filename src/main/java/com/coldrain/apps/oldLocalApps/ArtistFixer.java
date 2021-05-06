package com.coldrain.apps.oldLocalApps;

import com.coldrain.exception.NormalE.NoCorrespondingFileTypeFound;
import com.coldrain.old.localModels.MetaSong_old;
import com.coldrain.toolkit.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.TagException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

//最新力作，虽然有的地方还是不堪入目但是至少比以前好了
public class ArtistFixer {
    
    /* Upcoming Feature:
     * even if name is not perfectly matched (sakuzyo vs xi), if contains name, enter a list
     * for manual check.
     * Notice: finding ways to avoid confusion caused by short artist name (e.g. b, An)
     */


    public static HashMap<String, String> readModList(String toReadXmlLoc)
        throws DocumentException {
        HashMap<String, String> comparMap = new HashMap<String, String>();
        SAXReader reader = new SAXReader();
        Document doc = reader.read(toReadXmlLoc); // throw documentExeption when reading failed.
        Element rootElement = doc.getRootElement();
        List<Element> artists = rootElement.elements("Artist");
        for (Element artistRec : artists) {
            String origName = artistRec.element("OriginalName").getText();
            String fixedName = artistRec.element("FixedName").getText();
            comparMap.put(origName, fixedName);
        }
        return comparMap;
    }

    public static void main(String[] args) {
        Timer tim = new Timer();
        tim.timerStart();
        PlaylistUpdater updater = new PlaylistUpdater();
        String folderAddr = args[0];
        String substituteAddr = args[1];
        String[] allowedAudio = {".flac", ".mp3"};
        /* Data Preparation */
        HashMap<String, String> modMap;
        HashMap<String, String> renameMap = new HashMap<String, String>();
        try {
            modMap = readModList(substituteAddr);
        } catch (DocumentException e) {
            LogMaker.logs("Can't read xml file for Artist fix!");
            e.printStackTrace();
            return;
        }
        ArrayList<String> sampleAudioList = new ArrayList<String>();
        MethodInvoker.singlizeInputR(folderAddr, allowedAudio, sampleAudioList); // no need for ret.
        Iterator<String> audioIt = sampleAudioList.iterator();
        /* Get Medatata from Song */
        while (audioIt.hasNext()) {
            String checkingAddr = audioIt.next();
            try {
                MetaSong_old aMeta = new MetaSong_old(checkingAddr);
                String toCheckArtist = aMeta.getArtist();
                if (modMap.containsKey(toCheckArtist)) {
                    aMeta.setArtist(modMap.get(toCheckArtist));
                    aMeta.writeMetaToFile();
                    LogMaker.logs("fixed meta in " + checkingAddr + " with artist "
                        + modMap.get(toCheckArtist));
                    try {
                        String[] tempArr = MisUtils.calcAudioLoc(aMeta);
                        renameMap.put(tempArr[0], tempArr[1]);
                    } catch (CannotReadException | IOException | TagException
                        | ReadOnlyFileException | InvalidAudioFrameException e) {
                        LogMaker.logs("Failure reading Metadata to Generate New Path.");
                        e.printStackTrace();
                        return;
                    }

                }
            } catch (CannotReadException | IOException | TagException | ReadOnlyFileException
                | InvalidAudioFrameException e) {
                // TODO Auto-generated catch block
                LogMaker.logs("Failure reading Metadata from " + checkingAddr);
                e.printStackTrace();
                return;
            } catch (KeyNotFoundException | CannotWriteException e2) {
                // TODO Auto-generated catch block
                LogMaker.logs("Failure writing back metadata into " + checkingAddr);
                e2.printStackTrace();
                return;
            }
        }



        try {
            updater.setDefaultPlaylist();
            updater.setWholeRenameMap(renameMap);
            updater.executeUpdate();

        } catch (NoCorrespondingFileTypeFound e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException
            | IllegalArgumentException | InvocationTargetException | IOException e) {
            // TODO Auto-generated catch block
            LogMaker.logs("Failure updating M3U");
            e.printStackTrace();
            return;
        }

        // rename file
        Iterator<Entry<String, String>> iterMap = renameMap.entrySet().iterator();
        while (iterMap.hasNext()) {
            HashMap.Entry<String, String> entry = iterMap.next();
            MisUtils.renameFile(entry.getKey(), entry.getValue());
        }
        tim.timerEnd();

    }

}
