package com.yamada.notkayla.database;

import com.yamada.notkayla.Config;
import com.yamada.notkayla.Kayla;
import com.yamada.notkayla.database.entities.GuildData;
import org.postgresql.ds.PGPooledConnection;

import java.sql.*;
import java.util.Map;
import java.util.logging.Level;

public class Database {
    private PGPooledConnection connection;
    public Database() throws SQLException, ClassNotFoundException {
        try {
            Class.forName("org.postgresql.Driver");
            Map db = (Map)Config.configuration.get("db");// we can manually set the host and database instead of making it
            if (db.get("user") != null && db.get("pass") != null)connection = new PGPooledConnection(DriverManager.getConnection(
                    String.format("jdbc:postgresql://%s/%s", db.get("host") == null ? "localhost" : db.get("host"),
                            db.get("name") == null ? "yamada" : db.get("name")),(String) db.get("user")
                    , (String) db.get("pass")),true);
            if (10 >connection.getConnection().getMetaData().getDatabaseMajorVersion()) throw new SQLException("Postgres major version must be 10 or newer.");
        } catch (ClassNotFoundException e) {
            Kayla.log.log(Level.SEVERE,"Uh oh, looks like the driver wasn't found for the database ;P");
            e.printStackTrace();
            throw e;
        } catch (SQLException e) {
            Kayla.log.log(Level.SEVERE,"oof the bad thing happened and there was an error from getConnection");
            e.printStackTrace();
            throw e;
        }catch (NullPointerException e){
            Kayla.log.log(Level.SEVERE,"Whoops! You forgot a database key");
        }
    }

    public ResultSet query(String query) throws SQLException {
        Statement st = connection.getConnection().createStatement();
        return st.executeQuery(query);
    }

    public GuildData guildData(String id) throws SQLException {
        ResultSet query = query("SELECT * from guilds");
        query.first(); // just wanna make sure row is first row
        return new GuildData(query.getString("gid"),query.getString("prefix"),query.getBoolean("customPrefix"));
    }
}
