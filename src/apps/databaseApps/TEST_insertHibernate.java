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
import database.databaseModels.TestFileModel;
import old.localModels.MetaSong;
import toolkit.HibernateUtils;
import toolkit.LogMaker;
import toolkit.MethodInvoker;

public class TEST_insertHibernate {

    private static SessionFactory factory;

    public static void main(String[] args) throws CannotReadException, IOException, TagException,
        ReadOnlyFileException, InvalidAudioFrameException {
        String[] allowedAudio = {".flac", ".mp3"};
        ArrayList<String> sampleAudioList = new ArrayList<String>();
        sampleAudioList = MethodInvoker.singlizeInputR("E:\\lzx\\etc\\OST\\Discography\\Irui",
            allowedAudio, sampleAudioList);
        Iterator<String> audioIt = sampleAudioList.iterator();
        ArrayList<MetaSong> metaList = new ArrayList<MetaSong>();

        // 2. Get Metadata from each audio file separately.
        while (audioIt.hasNext()) {
            String singleFileaddr = audioIt.next();
            try {
                MetaSong aSong = new MetaSong(singleFileaddr);
                metaList.add(aSong);
                LogMaker.logs(HibernateUtils.splitFormat(aSong.getFORMAT())[0]);

            } catch (CannotReadException | IOException | TagException | ReadOnlyFileException
                | InvalidAudioFrameException e) {
                LogMaker.logs("failure Reading Metadata");
                e.printStackTrace();
                return;
            }
        }
        // start hib
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        TEST_insertHibernate ME = new TEST_insertHibernate();
        // start oper
        for (MetaSong perSong:metaList) {
            ME.addRecord(perSong);
        }

    }

    public Integer addRecord(MetaSong metaIn) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer fid = null;
        try {
            tx = session.beginTransaction();
            TestFileModel fileIn = new TestFileModel(metaIn);
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
