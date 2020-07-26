package apps.testDatabaseApps;

import java.io.File;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import database.generic.BaseDatabaseTestingClass;
import database.testDatabaseModels2.Album1;
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
import toolkit.LogMaker;

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
            Album2 album = guaranteeAlbum2(aMeta);
            File2 file = createFile2(aMeta);
            setRelation(album,file);
        }

    }
    
    
    private static void setRelation(Album2 album, File2 file) {
        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        System.out.println("Refreshing...");
        session.refresh(album);
        session.refresh(file);
        album.getFiles().add(file);
        file.setAlbum(album);

        System.out.println("Album Update?");
        session.saveOrUpdate(album);
        System.out.println("File Update?");
        session.saveOrUpdate(file);
        tx.commit();
        session.close();
        System.out.println("Set Relation!");
    }
    
    private static Album2 guaranteeAlbum2(MetaSong meta) {
        Album2 returnAlbum;
        String album = meta.getAlbum();
        String albumArtist = meta.getAlbumArtist();
        Session session=InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        Query<Album2> q= (Query<Album2>) session.createQuery("from Album2 a where a.album=?0 and a.albumArtist=?1");
        q.setParameter(0, album);
        q.setParameter(1, albumArtist);
        Album2 toCheckAlbum = q.uniqueResult();
        session.close();
        if (toCheckAlbum == null){
            returnAlbum=createAlbum2(meta);
        }else {
            returnAlbum = toCheckAlbum;
        }
        return returnAlbum;
    }

    private static File2 createFile2(MetaSong meta) {
        File2 aFile2 = new File2(meta);

        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        session.save(aFile2);
        tx.commit();
        session.close();
        System.out.println("Created File");
        return aFile2;

    }
    
    private static Album2 createAlbum2(MetaSong meta) {
        Album2 aAlbum2 = new Album2(meta);
        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        session.save(aAlbum2);
        tx.commit();
        session.close();
        System.out.println("created album!");
        return aAlbum2;
    }


}
