package apps.testDatabaseApps;

import java.io.File;
import database.generic.BaseDatabaseTestingClass;
import database.generic.DbNavigator;
import database.models.FileModel;
import database.models.MetaModel;
import exception.DatabaseException;
import exception.MetaIOException;
import exception.NativeReflectionException;
import exception.PlaylistIOException;
import local.generic.AbstractPlaylistSong;
import local.generic.AbstractPlaylistTable;
import local.generic.BaseLocalTestingClass;
import local.generic.MetaSong;
import local.generic.PlaylistFileIO;

public class T_Initial extends BaseDatabaseTestingClass {

    public static void main(String[] args)
        throws PlaylistIOException, NativeReflectionException, MetaIOException, DatabaseException {
        T_Initial me = new T_Initial();
        me.setAllLevel("debug");
        BaseLocalTestingClass they = new BaseLocalTestingClass();
        they.setAllLevel("debug");
        String playlist = "E:\\lzx\\Discovery\\ColdRain\\Discography\\DatabasePlaylist.m3u";
        //String playlist = "E:\\program files\\foobar2000\\playlists\\Casual English.m3u8";
        AbstractPlaylistTable testTable = PlaylistFileIO.readPlaylist(new File(playlist));
        testTable.printAllSong();
        for (AbstractPlaylistSong aSong : testTable.getSongArrList()) {
            MetaSong aMeta = new MetaSong(aSong.getSrc());
            System.out.println("aMeta Date:"+aMeta.getAlbumDate());
            //DbNavigator.loadNewFile(aMeta);
            DbNavigator.updateMetaForFile(aMeta);
            
        }
        DbNavigator.cleanValidationSets();
        
        
    }
    



}
