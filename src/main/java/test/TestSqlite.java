package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestSqlite {



    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection connection = null;
        try {
            // create a database connection
            connection =
                DriverManager.getConnection("jdbc:sqlite:D:/SQLite/SQLiteDB/testPlaylist.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30); // set timeout to 30 sec.

            statement.executeUpdate("DROP TABLE IF EXISTS MAINMETA");
            statement.executeUpdate("CREATE TABLE MAINMETA" + "(mid INTEGER PRIMARY KEY,"
                + "title STRING NOT NULL," + "artist STRING NOT NULL," + " album STRING NOT NULL)");
            statement.executeUpdate(
                "INSERT INTO MAINMETA VALUES(null, 'songName', 'zexuan','noNameAlbum')");
            ResultSet rs = statement.executeQuery(
                "SELECT * FROM MAINMETA JOIN addr ON (MAINMETA.mid=addr.mid) ORDER BY (order) ASC");
            while (rs.next()) {
                // read the result set
                System.out.println("mid = " + rs.getInt("mid"));
                System.out.println("title = " + rs.getString("title"));
                System.out.println("artist =" + rs.getString("artist"));
                System.out.println("album = " + rs.getString("album"));
                System.out.println("addr =" + rs.getString("addr"));
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
