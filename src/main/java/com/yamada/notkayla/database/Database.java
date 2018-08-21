package com.yamada.notkayla.database;

import com.yamada.notkayla.Config;
import com.yamada.notkayla.Kayla;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;

public class Database {
    static Connection connection;
    public static void init(){
        try {
            Class.forName("org.postgresql.Driver");
            Map db = (Map<String,String>)Config.configuration.get("db");// we can manually set the host and database instead of making it
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/yamada", (String) db.get("user"), (String) db.get("pass"));
        } catch (ClassNotFoundException e) {
            Kayla.log.log(Level.SEVERE,"Uh oh, looks like the driver wasn't found for the database ;P");
            e.printStackTrace();
        } catch (SQLException e) {
            Kayla.log.log(Level.SEVERE,"oof the bad thing happened and there was an error from getConnection");
            e.printStackTrace();
        }
    }

    public static ResultSet blockingQuery() throws SQLException {
        connection.createStatement();// incomplete
        return null;
    }
}
