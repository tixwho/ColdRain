package databaseApps;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import tables.MetaSong;
import toolkit.LogMaker;
import toolkit.MethodInvoker;
import toolkit.MisUtils;

public class TEST_createMeta {

    public static String createInsertSentence(String tableName, String title, String artist,
        String album, boolean isTemp) {
        String tempFlag;
        if (isTemp) {
            tempFlag = "'1'";
        } else {
            tempFlag = "'0'";
        }
        String rtrSentence;
        rtrSentence = "INSERT INTO " + tableName + " VALUES(null,"
            + MisUtils.addSingleQuoteForDB(title) + "," + MisUtils.addSingleQuoteForDB(artist)
            + "," + MisUtils.addSingleQuoteForDB(album) + "," + tempFlag + ")";
        return rtrSentence;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        /* INFO COLLECTION */
        // 1. Get basic info.
        // Possible universal heading:
        // basic info collection
        String folderAddr = args[0];
        String[] allowedAudio = {".flac", ".mp3"};
        LogMaker.logs("srcFolder: " + folderAddr);
        ArrayList<String> sampleAudioList = new ArrayList<String>();
        sampleAudioList = MethodInvoker.singlizeInputR(folderAddr, allowedAudio, sampleAudioList);
        Iterator<String> audioIt = sampleAudioList.iterator();
        ArrayList<MetaSong> metaList = new ArrayList<MetaSong>();

        // 2. Get Metadata from each audio file separately.
        while (audioIt.hasNext()) {
            String singleFileaddr = audioIt.next();
            try {
                MetaSong aSong = new MetaSong(singleFileaddr);
                metaList.add(aSong);

            } catch (CannotReadException | IOException | TagException | ReadOnlyFileException
                | InvalidAudioFrameException e) {
                LogMaker.logs("failure Reading Metadata");
                e.printStackTrace();
                return;
            }
        }


        /* Below SQL execution part */
        String createSentence = "CREATE TABLE \"MAINMETA\" (\r\n"
            + "    \"mid\"   INTEGER PRIMARY KEY AUTOINCREMENT,\r\n"
            + "    \"title\" TEXT NOT NULL,\r\n" + "    \"artist\"    TEXT NOT NULL,\r\n"
            + "    \"album\" TEXT NOT NULL,\r\n" + "    \"isTemp\"    INTEGER DEFAULT 0\r\n" + ")";

        Class.forName("org.sqlite.JDBC");
        Connection connection = null;
        try {
            // create a database connection
            connection =
                DriverManager.getConnection("jdbc:sqlite:D:/SQLite/SQLiteDB/testPlaylist.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30); // set timeout to 30 sec.
            // drop MAINMETA when testing.
            statement.executeUpdate("DROP TABLE IF EXISTS MAINMETA");
            // create MAINMETA everytime launch
            statement.executeUpdate(createSentence);
            // iterate through metadata list to insert metadata into sqlite.
            for (MetaSong singleMeta : metaList) {
                String title = singleMeta.getTrackTitle();
                String artist = singleMeta.getArtist();
                String album = singleMeta.getAlbum();
                String stnce =
                    createInsertSentence("MAINMETA", title, artist, album, false);
                LogMaker.logs(stnce);
                statement.executeUpdate(stnce);
            }

        } catch (SQLException e) {
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
    }

}
