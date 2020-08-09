package apps.testDatabaseApps;

import java.io.File;
import database.generic.BaseDatabaseTestingClass;
import database.service.AudioDBService;
import database.service.PlaylistDBService;
import exception.DatabaseException;

public class T_playlistScan extends BaseDatabaseTestingClass {

    public static void main(String[] args) throws DatabaseException {
        T_playlistScan me = new T_playlistScan();
        me.setAllLevel("debug");
        tim.timerStart();
        deployFullScan();
        tim.timerEnd();

    }

    public static void deployFullScan() throws DatabaseException {
        // assume using 'create' property in hibernate.cfg.xml
        String[] allHomeDirs = {"E:\\lzx\\Discovery\\ColdRain\\Discography",
            "E:\\lzx\\etc\\OST\\Discography", "E:\\program files\\foobar2000\\playlists"};
        String[] playlistDirs = {"E:\\program files\\foobar2000\\playlists","C:\\Users\\ASUS\\Music\\Dopamine\\Playlists"};
        for (String aHomeDirStr : playlistDirs) {
            File aHomeDir = new File(aHomeDirStr);
            AudioDBService.fullScanAudioFiles(aHomeDir);
            PlaylistDBService.fullScanPlaylistFiles(aHomeDir);
        }
    }

}
