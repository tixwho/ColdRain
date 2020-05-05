package toolkit;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import exception.RuntimeE.EmptyListException;
import old.localModels.M3USong;
import old.localModels.M3UTable;
import old.localModels.MetaSong;

public class MisUtils {

    /* File Operations */

    // return TRUE if suffix is included in the given array.
    public static boolean checkSuffix(String fileSrc, String[] allowedSuffix)
        throws EmptyListException {
        // first check input List

        if (allowedSuffix.length == 0) {
            throw new EmptyListException("001", "Empty Allowed Suffix!");
        }
        List<String> list = Arrays.asList(allowedSuffix);
        String fileSuffix = MisUtils.getSuffix(fileSrc);
        if (list.contains(fileSuffix)) {
            return true;
        }
        return false;
    }

    // return file suffix as a string.
    public static String getSuffix(String fileSrc) {
        String fileSuffix = fileSrc.substring(fileSrc.lastIndexOf("."));
        return fileSuffix;
    }

    // rename a folder (move everything to the new folder, then discard the original one.)
    public static void renameFolder(String origFolder, String newFolder) {
        DirMaker.mkRawDirIfNotExist(newFolder);
        String[] allFiles = new File(origFolder).list(); // replace with new Iterator
        for (String singleFileName : allFiles) {
            File singleFile = new File(origFolder, singleFileName);
            String fileName = singleFile.getName();
            File inFile = new File(newFolder, fileName);
            renameFile(singleFile.toString(), inFile.toString());
        }
        File origFolderFile = new File(origFolder);
        origFolderFile.delete();
        LogMaker.logs("Deleted Directory: " + origFolder.toString());
    }

    // rename a file
    public static void renameFile(String origFile, String newFile) {
        File singleFile = new File(origFile);
        String fileName = singleFile.getName();
        LogMaker.logs("OrigFile: " + fileName);
        File inFile = new File(newFile);
        LogMaker.logs("New FilePath: " + inFile.getAbsolutePath());
        boolean test = singleFile.renameTo(inFile);
        LogMaker.logs("result: " + test);

    }

    /* String Operations */

    // replace illegal chars in filename.
    public static String replaceIlligalFilename(String origFileName) {
        String newFileName;
        newFileName = origFileName.replaceAll("[\\\\*<>|]", "_");
        newFileName = newFileName.replaceAll("\\?", "？");
        newFileName = newFileName.replaceAll("\\:", "：");
        newFileName = newFileName.replaceAll("\\/", "／");
        // regex replace odd \" to “,even " to ”
        Pattern p = Pattern.compile("\"");
        Matcher m = p.matcher(newFileName);
        StringBuffer sb = new StringBuffer();
        int index = 1;
        while (m.find()) {
            // m.appendReplacement(sb, (index++ & 1) == 0 ? "java" : "JAVA"); simple
            if ((index & 1) == 0) { // even
                m.appendReplacement(sb, "”");
            } else { // odd
                m.appendReplacement(sb, "“");
            }
            index++;
        }
        m.appendTail(sb);// 把剩余的字符串加入
        newFileName = sb.toString();
        return newFileName;

    }

    // replace illegal chars for database input(single quote, double quote)
    public static String replaceIlllegalForDB(String origContent) {
        String newContent;
        newContent = origContent.replaceAll("'", "''"); // ' --> '' -- '' in db
        // newContent = newContent.replaceAll("\"","\"\"");// " --> "" -- "" in db NO NEED
        return newContent;
    }



    public static String addSingleQuoteForDB(String somestr) {
        String rtrStr = "'" + MisUtils.replaceIlllegalForDB(somestr) + "'";
        return rtrStr;
    }

    /* PlayList Showrcuts */

    // replace all matching address in the arraylist with the new address.
    // phase 1: check if map contains corresponding key. if anyone matches, replace it with array[1]
    // phase 2: overwrite original m3u with new one.
    public static void fixAM3U(String M3UAddr, HashMap<String, String> searchMap)
        throws NoSuchMethodException, SecurityException, IllegalAccessException,
        IllegalArgumentException, InvocationTargetException, IOException {
        M3UTable singleM3UTable;
        int modifyFlag = 0;
        singleM3UTable = new M3UTable(M3UAddr);
        ArrayList<M3USong> songArrList = singleM3UTable.getSongArrrayList();
        for (M3USong singleM3USong : songArrList) {
            String src = singleM3USong.getSrc();
            if (searchMap.containsKey(src)) {
                src = searchMap.get(src);
                LogMaker.logs("got "+src+ " in " + M3UAddr);
                singleM3USong.setSrc(src);
                modifyFlag = 1;
            } else {
                //LogMaker.logs(src+" Not found in" + M3UAddr + "!");
            }
        }
        if (modifyFlag == 0) {
            return;
        }
        LogMaker.logs("reprinting "+M3UAddr);
        singleM3UTable.setSongArrrayList(songArrList);
        // overwrite original m3u file.
        NewFileWriter.writeAM3U(singleM3UTable, M3UAddr);
    }

    /**
     * Just for temporary use.
     * 
     * @throws IOException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws SecurityException
     * @throws NoSuchMethodException
     */
    public static void fixAllM3U(String M3UFolderAddr, HashMap<String, String> searchMap)
        throws NoSuchMethodException, SecurityException, IllegalAccessException,
        IllegalArgumentException, InvocationTargetException, IOException {
        ArrayList<String> M3ULocs = MethodInvoker.singlizeInput(M3UFolderAddr, ".m3u");
        for (String aM3U : M3ULocs) {
            fixAM3U(aM3U, searchMap);
        }

    }
    
    //copied from SongFormatizer. Input is a metadata.
    public static String[] calcAudioLoc(MetaSong singleSongMeta) throws CannotReadException,
    IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
    String artist = singleSongMeta.getArtist();
    String name = singleSongMeta.getTrackTitle();
    String suffix = MisUtils.getSuffix(singleSongMeta.getSrc());
    File audioFile = new File(singleSongMeta.getSrc());
    String parentFolder = audioFile.getParent();
    String newFileName = MisUtils.replaceIlligalFilename(artist.concat(" - ").concat(name).concat(suffix));
    String[] keyVarray = new String[2];
    String finalizedAbsPath = new File(parentFolder,newFileName).toString();
    keyVarray[0]=singleSongMeta.getSrc();
    keyVarray[1]=finalizedAbsPath;
    return keyVarray;
}

}
