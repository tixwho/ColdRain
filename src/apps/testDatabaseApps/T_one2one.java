package apps.testDatabaseApps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import database.generic.BaseDatabaseTestingClass;
import database.testDatabaseModels2.Album1;
import database.testDatabaseModels2.File1;
import database.testDatabaseModels2.FileProperty1;
import database.testDatabaseModels2.Meta1;
import exception.MetaIOException;
import local.generic.MetaSong;
import toolkit.LogMaker;
import toolkit.MethodInvoker;

public class T_one2one extends BaseDatabaseTestingClass {

    private static SessionFactory factory;



    // todo: split album; check metadata before insert a new meta(guarantee meta)
    public static void main(String[] args) throws MetaIOException {
        tim.timerStart();
        String[] allowedAudio = {".flac", ".mp3"};
        String folderLoc = "E:\\lzx\\Discovery\\ColdRain\\SimpDiscography";
        String anotherFolderLoc = "E:\\lzx\\etc\\OST\\Discography";
        String huntShowFolderLoc =
            "E:\\lzx\\etc\\OST\\Discography\\Hunt- Showdown (Original Game Soundtrack)";
        ArrayList<String> sampleAudioList = new ArrayList<String>();
        sampleAudioList = MethodInvoker.singlizeInputR(folderLoc, allowedAudio, sampleAudioList);
        Iterator<String> audioIt = sampleAudioList.iterator();
        ArrayList<MetaSong> metaList = new ArrayList<MetaSong>();
        while (audioIt.hasNext()) {
            metaList.add(new MetaSong(audioIt.next()));
        }
        tim.timerPeriod("reading meta complete!");
        // start hib
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        // start insertion
        // tip: save fron "one" side first.
        for (MetaSong eachMeta : metaList) {
            LogMaker.logs("--------------------------");
            System.out.println(eachMeta);
            Session session = factory.openSession();
            //session.beginTransaction();
            // create objects
            File1 aFile = new File1(eachMeta);
            FileProperty1 aFileProperty = new FileProperty1(eachMeta);
            // put before session to guarantee session not locked
            Album1 aAlbum = guaranteeAlbum(session, eachMeta);
            System.out.println(aAlbum);
            Meta1 aMeta = guaranteeMeta(session,eachMeta,aAlbum);
            // files-fielsProperty one-to-one
            aFile.setFileProperty(aFileProperty);
            aFileProperty.setFileModel(aFile);
            // files-meta many-to-one
            aFile.setOrigMeta(aMeta);
            aMeta.getFiles_orig().add(aFile);
            // meta-album many-to-one
            aMeta.setAlbum(aAlbum);
            aAlbum.getMetas().add(aMeta);
            // start saving: from one-side first album>meta>file=property
            session.saveOrUpdate(aAlbum);
            session.save(aMeta);
            session.save(aFile);
            session.save(aFileProperty);

            session.close();
        }
        tim.timerPeriod("Created base info!");
        // start modifying modifiedMeta
        Session session = factory.openSession();
        session.beginTransaction();
        Meta1 toAddMeta = session.get(Meta1.class, 2);
        File1 toModFile = session.get(File1.class, 1);
        toModFile.setModifiedMeta(toAddMeta);
        toAddMeta.getFiles_mod().add(toModFile);
        session.saveOrUpdate(toAddMeta);
        session.saveOrUpdate(toModFile);
        session.getTransaction().commit();
        session.close();
        tim.timerEnd("modified one meta");
    }

    // test Method one: for each album, query if it exists or not.
    @SuppressWarnings("unchecked")
    public static Album1 guaranteeAlbum(Session session, MetaSong meta) {
        String album = meta.getAlbum();
        String albumArtist = meta.getAlbumArtist();
        Album1 returnAlbum;
        session.beginTransaction();
        Query<Album1> q =
            session.createQuery("from Album1 a where a.album=?0 and a.albumArtist=?1");
        q.setParameter(0, album);
        q.setParameter(1, albumArtist);
        session.getTransaction().commit();
        List<Album1> albumList = q.list();
        if (albumList.size() == 0) {
            LogMaker.logs("Nope");
            returnAlbum = new Album1(meta);
        } else if (albumList.size() == 1) {
            LogMaker.logs("Got!");
            returnAlbum = albumList.get(0);
        } else {
            LogMaker.logs("FUCK");
            returnAlbum = new Album1(meta);
        }
        return returnAlbum;

    }
    
    public static Meta1 guaranteeMeta(Session session, MetaSong meta, Album1 album) {
        String title=meta.getTrackTitle();
        String artist=meta.getArtist();
        Meta1 returnMeta;
        session.beginTransaction();
        @SuppressWarnings("unchecked")
        Query<Meta1> q =
            session.createQuery("from Meta1 m where m.title=?0 and m.artist=?1 and m.album=?2");
        q.setParameter(0, title);
        q.setParameter(1, artist);
        q.setParameter(2,album);
        session.getTransaction().commit();
        List<Meta1> metaList = q.list();
        if (metaList.size() == 0) {
            LogMaker.logs("Nope");
            returnMeta = new Meta1(meta);
            returnMeta.setAlbum(album);
        } else if (metaList.size() == 1) {
            LogMaker.logs("Got!");
            returnMeta = metaList.get(0);
        } else {
            LogMaker.logs("FUCK");
            returnMeta = new Meta1(meta);
            returnMeta.setAlbum(album);
        }

        return returnMeta;
    }

}
