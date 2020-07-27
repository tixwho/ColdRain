package apps.testDatabaseApps;

import java.io.File;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import database.generic.BaseDatabaseTestingClass;
import database.models.AlbumModel;
import database.models.ArtistModel;
import database.models.FileInfoComp;
import database.models.FileModel;
import database.models.MetaModel;
import database.models.SongModel;
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

public class T_Initial extends BaseDatabaseTestingClass {

    public static void main(String[] args)
        throws PlaylistIOException, NativeReflectionException, MetaIOException {
        T_Initial me = new T_Initial();
        me.setAllLevel("debug");
        BaseLocalTestingClass they = new BaseLocalTestingClass();
        they.setAllLevel("debug");
        //String playlist = "E:\\lzx\\Discovery\\ColdRain\\Discography\\DatabasePlaylist.m3u";
        String playlist = "E:\\program files\\foobar2000\\playlists\\Casual English.m3u8";
        AbstractPlaylistTable testTable = PlaylistFileIO.readPlaylist(new File(playlist));
        testTable.printAllSong();
        for (AbstractPlaylistSong aSong : testTable.getSongArrList()) {
            MetaSong aMeta = new MetaSong(aSong.getSrc());
            System.out.println("aMeta Date:"+aMeta.getAlbumDate());
            FileModel fileM = FileModel.createFileInfoModel(aMeta);
            MetaModel metaM = MetaModel.createMetaModel(aMeta);
            fileM.attachMetaModel(metaM);
            ArtistModel artistM = ArtistModel.guaranteeArtistModel(aMeta);
            metaM.attachArtistModel(artistM);
            AlbumModel albumM = AlbumModel.guaranteeAlbumModel(aMeta);
            metaM.attachAlbumModel(albumM);
            SongModel songM = SongModel.guaranteeSongModel(aMeta);
            metaM.attachSongModel(songM);
            
        }

    }
    
    
    /*
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
    */

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


}
