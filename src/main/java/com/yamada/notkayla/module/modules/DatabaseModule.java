package com.yamada.notkayla.module.modules;

import com.yamada.notkayla.Yamada;
import com.yamada.notkayla.module.entities.GuildData;
import com.yamada.notkayla.module.entities.UserData;
import org.postgresql.ds.PGPooledConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

@SuppressWarnings("ALL")
public class DatabaseModule {
    private static PGPooledConnection connection;
    private static Map<String, PreparedStatement> statements = new HashMap<>();
    private static Map<String,Object> config;
    public static void init(Map config) throws SQLException, ClassNotFoundException {
        try {
            Class.forName("org.postgresql.Driver");
            Map db = (Map) config.get("db");// we can manually set the host and database instead of making it required
            connection = new PGPooledConnection(DriverManager.getConnection(
                    String.format("jdbc:postgresql://%s/%s?allowMultiQueries=true", db.get("host") == null ? "localhost:5433" : db.get("host"),
                            db.get("name") == null ? "yamada" : db.get("name")),(String) db.get("user"), (String) db.get("pass")),true );

            if (10 > connection.getConnection().getMetaData().getDatabaseMajorVersion()) throw new SQLException("Postgres major version must be 10 or newer. Current version: "+connection.getConnection().getMetaData().getDatabaseProductVersion());
            DatabaseModule.config = config;
            prepareStatements();
        } catch (ClassNotFoundException e) {
            Yamada.log.log(Level.SEVERE,"Uh oh, looks like the driver wasn't found for the database ;P");
            e.printStackTrace();
            throw e;
        } catch (SQLException e) {
            Yamada.log.log(Level.SEVERE,"oof the bad thing happened and there was an error from getConnection");
            e.printStackTrace();
            throw e;
        }catch (NullPointerException e){
            Yamada.log.log(Level.SEVERE,"Whoops! You forgot a database key, you gotta have db.user and db.pass at least.");
            e.printStackTrace();
            throw e;
        }
    }
    //TODO: update statements
    private static void prepareStatements() throws SQLException {
        Connection conn = connection.getConnection();
        statements.put("guild",conn.prepareStatement("SELECT * from guilds WHERE gid = ?",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE));
        statements.put("user",conn.prepareStatement("select * from users where uid = ?",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE));
        statements.put("cUser",conn.prepareStatement("insert into users values (?,100)",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE));
        String prefix = config.get("prefix") == null ? "'!y'" : "'"+(String) config.get("prefix")+"'";
        statements.put("cGuild",conn.prepareStatement("insert into guilds values (?,"+prefix+",'FALSE')",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE));
    }

    //returns true if successful
    public static void createUserData(String id) throws SQLException {

        PreparedStatement stmt = statements.get("cUser");
        stmt.setString(1,id);
        try {
            stmt.execute();
        }catch (SQLException ignored){}
    }
    public static void createGuildData(String id) throws SQLException {
        PreparedStatement stmt = statements.get("cGuild");
        stmt.setString(1,id);
        stmt.execute();
    }

    public static GuildData guildData(String id) throws SQLException {
        PreparedStatement stmt = statements.get("guild");
        createGuildData(id);
        stmt.setString(1,id);
        ResultSet query = stmt.executeQuery();
        query.first(); // just wanna make sure row is first row
        return new GuildData(query.getString("gid"),query.getString("prefix"),query.getBoolean("customPrefix"));
    }

    public static UserData userData(String id) throws SQLException {
        PreparedStatement stmt = statements.get("user");
        createUserData(id);
        stmt.setString(1,id);
        ResultSet query = stmt.executeQuery();
        query.first(); // just wanna make sure row is first row
        UserData userData = new UserData(query.getString("uid"), query.getLong("coins"));
        return userData;
    }
}
