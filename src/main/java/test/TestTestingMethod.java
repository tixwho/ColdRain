package test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import old.localModels.MetaSong_old;
import toolkit.LogMaker;
import toolkit.MethodInvoker;

public class TestTestingMethod {
    // WHEN DELETE, you can also delete TestCounter.java
    

    public static void main(String[] args) throws ClassNotFoundException {
        
        HashMap<String,String> sqlMap = new HashMap<String,String>();
        HashMap<String,TestCounter> ourMap = new HashMap<String,TestCounter>();
        ArrayList<String>receivedList = new ArrayList<String>();
        String[] allowedAudio = {".flac", ".mp3"};
        receivedList = MethodInvoker.singlizeInputR(args[0], allowedAudio, receivedList);

        for (String addr:receivedList) {
            try {
                MetaSong_old aMeta = new MetaSong_old(addr);
                String albumName = aMeta.getAlbum();
                if (ourMap.containsKey(albumName)) {
                    ourMap.get(albumName).addOne();
                }else {
                    TestCounter aCounter = new TestCounter();
                    ourMap.put(albumName,aCounter);
                }
                
            } catch (CannotReadException | IOException | TagException | ReadOnlyFileException
                | InvalidAudioFrameException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return;
            }
        }

        Class.forName("org.sqlite.JDBC");
        Connection connection = null;
        
        try {
            // create a database connection
            connection =
                DriverManager.getConnection("jdbc:sqlite:D:/SQLite/SQLiteDB/testPlaylist.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30); // set timeout to 30 sec.
            ResultSet rs = statement.executeQuery("SELECT COUNT(mid) AS cnt,album FROM MAINMETA GROUP BY album ORDER BY album ASC");
            while(rs.next())
            {
              // read the result set
                sqlMap.put(rs.getString("album"),rs.getString("cnt"));
            }
        }catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }

        }
        
        Set<Entry<String, TestCounter>> set=ourMap.entrySet();
        Iterator<HashMap.Entry<String, TestCounter>> it=set.iterator();
        while(it.hasNext()){
         //System.out.println(list.get(0) ); 
            Map.Entry<String, TestCounter> e=it.next();
            String albumHere = e.getKey();
            String albumCount = String.valueOf(e.getValue().getCount());
            if(sqlMap.containsKey(albumHere)) {
                if(albumCount.compareTo(sqlMap.get(albumHere))==0) {
                    LogMaker.logs(albumHere + " OK!");
                }else {
                    LogMaker.logs(albumHere + " sql has "+sqlMap.get(albumHere) + " while playlist count has "+albumCount);
                }
            }else {
                LogMaker.logs("Can't find album "+ albumHere + " from sql!");
            }
        }
        LogMaker.logs("localFileSize: " + receivedList.size());

    }

}
