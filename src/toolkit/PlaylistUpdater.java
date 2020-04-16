package toolkit;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import exception.NormalE.NoCorrespondingFileTypeFound;

// Mimic the playlist update process.
// whether or not use void/int for return undecided for now.

public class PlaylistUpdater {

    private ArrayList<String> renameFileLocs = new ArrayList<String>();
    private HashMap<String, String> renameMap = new HashMap<String, String>();

    public int loadPlaylistFile(String fileloc) throws NoCorrespondingFileTypeFound {
        String[] allowedSuffix = {".m3u"};
        MethodInvoker.singlizeInputR(fileloc, allowedSuffix, this.renameFileLocs);
        if (this.renameFileLocs.isEmpty()) {
            throw new NoCorrespondingFileTypeFound("1", "No M3U File Found!");
        } else {
            return this.renameFileLocs.size();
        }
    }

    public void addRenameMap(String origName, String newName) {
        renameMap.put(origName, newName);
    }

    public void addRenameMap(Map.Entry<String, String> renameEntry) {
        renameMap.put(renameEntry.getKey(), renameEntry.getValue());
    }

    public void setWholeRenameMap(HashMap<String, String> wholeNewMap) {
        this.renameMap = wholeNewMap;
    }

    public String fetchRecord(String key) {
        // return entry according to key.
        return renameMap.get(key);
    }

    public void executeUpdate()
        throws NoCorrespondingFileTypeFound, NoSuchMethodException, SecurityException,
        IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
        if (renameFileLocs.isEmpty()) {
            throw new NoCorrespondingFileTypeFound("2", "Playlist Have Not been Loaded!");
        }
        for (String aLoc : renameFileLocs) {
            MisUtils.fixAM3U(aLoc, renameMap);
        }
    }

    public void executeDispose() {
        // dispose the whole map.
        renameMap.clear();
    }

    // testing method Below
    public ArrayList<String> checkNewAddr() {
        // use new map value to check if file exist. If not, return a list containing failed map
        // entry.
        ArrayList<String> failedLocs = new ArrayList<String>();
        for (Map.Entry<String,String> aEntry:renameMap.entrySet()) {
            File checkFile = new File(aEntry.getValue());
            if (!checkFile.exists()) {
                failedLocs.add(checkFile.toString());
            }
        }
        return failedLocs;
    }
    
    public void setDefaultPlaylist() throws NoCorrespondingFileTypeFound {
        loadPlaylistFile("C:\\Users\\ASUS\\Music\\Dopamine\\Playlists");
    }

}
