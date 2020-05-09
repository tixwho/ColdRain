package apps.databaseApps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import apps.testApps.BaseTestApp;
import database.databaseModels.FileModel;
import old.localModels.MetaSong_old;
import toolkit.HibernateUtils;
import toolkit.LogMaker;
import toolkit.MethodInvoker;

/* IMPORTANT: DELETE "create" property before use */

public class TEST_insertHibernate3 extends BaseTestApp {

    private static SessionFactory factory;

    public static void main(String[] args) throws CannotReadException, IOException, TagException,
        ReadOnlyFileException, InvalidAudioFrameException {
        TEST_insertHibernate3 ME = new TEST_insertHibernate3();
        ME.taskStart();
        String[] allowedAudio = {".flac", ".mp3"};
        ArrayList<String> sampleAudioList = new ArrayList<String>();
        sampleAudioList = MethodInvoker.singlizeInputR(
            "E:\\lzx\\etc\\OST\\Discography",
            allowedAudio, sampleAudioList);
        Iterator<String> audioIt = sampleAudioList.iterator();
        ArrayList<MetaSong_old> metaList = new ArrayList<MetaSong_old>();

        // 2. Get Metadata from each audio file separately.
        while (audioIt.hasNext()) {
            String singleFileaddr = audioIt.next();
            try {
                MetaSong_old aSong = new MetaSong_old(singleFileaddr);
                metaList.add(aSong);
                LogMaker.logs(HibernateUtils.splitFormat(aSong.getFORMAT())[0]);

            } catch (CannotReadException | IOException | TagException | ReadOnlyFileException
                | InvalidAudioFrameException e) {
                LogMaker.logs("failure Reading Metadata");
                e.printStackTrace();
                return;
            }
        }
        ME.taskCheckPoint();
        // start hib
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        // start oper
        for (MetaSong_old perSong : metaList) {
            ME.addRecord(perSong);
        }
        ME.taskEnd();

    }

    public Integer addRecord(MetaSong_old metaIn) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer fid = null;
        try {
            tx = session.beginTransaction();
            FileModel fileIn = new FileModel(metaIn);
            fid = (Integer) session.save(fileIn);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return fid;
    }

}
