package apps.oldLocalApps;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import exception.NormalE.NoCorrespondingFileTypeFound;
import toolkit.DirMaker;
import toolkit.MethodInvoker;
import toolkit.MisUtils;
import toolkit.PlaylistUpdater;

/*
 * Algorithm Prework:Split different disc into different folders, with same meta for album.
 * NamePattern: AlbumAAA Disc1;AlbumAAA Disc2 Arg1:FolderName Preset (most of time, album name) e.g.
 * AlbumAAA Arg2:parent folder (folder that contains "AlbumAAA Disc1")
 * 
 * First: search for all folders match name pattern "AlbumAAA Disc", pack into a map, count disc.
 * *Map Entry<discFolderPath,disc> Second: create new folders if not exist. Third: iterate through
 * map, for each discpath, write in disc and totalDisc Meta, move to new folder(albumName/Disc
 * N),ready to update related playlist info. Fourth: update playlist.
 */

// known bug: if discography name is too short (eg: ai), then it might effect other disc contains
// this short phrase (eg. dais). this will rarely happen since most discography has long name.
public class DiscMerger {

    public static void main(String[] args)
        throws NoCorrespondingFileTypeFound, NoSuchMethodException, SecurityException,
        IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
        // manually input folders to be mixed and header
        // 2020.6.1 test regex pattern
        PlaylistUpdater updater = new PlaylistUpdater();
        updater.setDefaultPlaylist();
        ArrayList<String> testArr = new ArrayList<String>();
        String parentFolder = "F:\\Discography";
        /*
         * testArr.add("涼宮ハルヒの消失 オリジナルサウンドトラック Disc1"); //normal type
         * testArr.add("STEINS;GATE VOCAL BEST DISC1"); //capitalized type
         */
        File father = new File(parentFolder);
        String[] subFolders = father.list();
        for (String folder : subFolders) {
            testArr.add(parentFolder + "\\" + folder);
        }
        String heading = "魔法少女まどか☆マギカ Music Collection";
        String regex = "\\s?(?i)disc\\s?"; // (?i)后的字符均无视大小写
        String regex1 = "(?<=(" + heading + ")).+";
        String[] supportedSuffix = {".flac", ".mp3"};
        ArrayList<String> targetArr = new ArrayList<String>();
        int totalDiscCount = 0;
        for (String sentence : testArr) {
            System.out.println(sentence);
            if (sentence.contains(parentFolder + "\\" + heading)) {
                targetArr.add(sentence);
                Pattern p = Pattern.compile(regex1);
                Matcher m = p.matcher(sentence);
                m.find();
                System.out.println(m.group().trim());
                totalDiscCount += 1;
            } else {
                System.out.println("inconsistent!");
            }

        }
        System.out.println("Total Disc Count:" + totalDiscCount);
        String strTotalDiscCount = "0" + totalDiscCount;
        DirMaker.mkRawDirIfNotExist(parentFolder + "\\" + heading);
        for (String filteredFold : targetArr) {
            // to make it easy, 暴力提取最后一个字符
            String discNum = filteredFold.substring(filteredFold.length() - 1, filteredFold.length());
            String toPutFolder = parentFolder + "\\" + heading + "\\Disc " + discNum;
            DirMaker.mkRawDirIfNotExist(toPutFolder);
            // first set up metadata
            String[] thatArg = {filteredFold, strTotalDiscCount, "0"+discNum};
            TotalDiscNoSetter.main(thatArg);
            // then move the files and record
            ArrayList<String> audioFiles = new ArrayList<String>();
            MethodInvoker.singlizeInputR(filteredFold, supportedSuffix, audioFiles);
            for (String audioLoc : audioFiles) {
                updater.addRenameMap(audioLoc, toPutFolder + "\\" + MisUtils.getFilename(audioLoc));
            }
            MisUtils.renameFolder(filteredFold, toPutFolder);
        }
        updater.executeUpdate();
        /*
         * for (String sentence : testArr) { String[] res = sentence.split(regex); for (String s :
         * res) { System.out.println(s); } }
         */


    }

}
