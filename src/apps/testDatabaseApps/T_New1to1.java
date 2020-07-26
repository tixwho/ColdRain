package apps.testDatabaseApps;

import java.io.File;
import org.hibernate.Session;
import org.hibernate.Transaction;
import database.generic.BaseDatabaseTestingClass;
import database.testDatabaseModels3.Album2;
import database.testDatabaseModels3.File2;
import database.utils.InitSessionFactory;
import exception.MetaIOException;
import exception.NativeReflectionException;
import exception.PlaylistIOException;
import local.generic.AbstractPlaylistSong;
import local.generic.AbstractPlaylistTable;
import local.generic.BaseLocalTestingClass;
import local.generic.MetaSong;
import local.generic.PlaylistFileIO;

public class T_New1to1 extends BaseDatabaseTestingClass {

    public static void main(String[] args)
        throws PlaylistIOException, NativeReflectionException, MetaIOException {
        T_New1to1 me = new T_New1to1();
        me.setAllLevel("debug");
        BaseLocalTestingClass they = new BaseLocalTestingClass();
        they.setAllLevel("debug");
        String playlist = "E:\\lzx\\Discovery\\ColdRain\\Discography\\DatabasePlaylist.m3u";
        AbstractPlaylistTable testTable = PlaylistFileIO.readPlaylist(new File(playlist));
        testTable.printAllSong();
        for (AbstractPlaylistSong aSong : testTable.getSongArrList()) {
            MetaSong aMeta = new MetaSong(aSong.getSrc());
            Album2 album = createAlbum2(aMeta);
            File2 file = createFile2(aMeta);
            setRelation(album,file);
        }

    }
    
    private static void setRelation(Album2 album, File2 file) {
        album.getFiles().add(file);
        file.setAlbum(album);
        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(album);
        session.saveOrUpdate(file);
        tx.commit();
        session.close();
    }

    private static File2 createFile2(MetaSong meta) {
        File2 aFile2 = new File2(meta);

        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        session.save(aFile2);
        tx.commit();
        session.close();
        return aFile2;
    }
    
    private static Album2 createAlbum2(MetaSong meta) {
        Album2 aAlbum2 = new Album2(meta);
        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        session.save(aAlbum2);
        tx.commit();
        session.close();
        return aAlbum2;
    }


}
