package com.yamada.notkayla.module.modules.database;

import com.yamada.notkayla.Config;
import com.yamada.notkayla.Kayla;
import com.yamada.notkayla.module.entities.GuildData;
import com.yamada.notkayla.module.Module;
import org.postgresql.ds.PGPooledConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
@Module(name="database",guarded=false)
public class DatabaseModule {
    private PGPooledConnection connection;
    private Map<String, PreparedStatement> statements = new HashMap<>();
    public DatabaseModule() throws SQLException, ClassNotFoundException {
        try {
            Class.forName("org.postgresql.Driver");
            Map db = (Map)Config.configuration.get("db");// we can manually set the host and database instead of making it
            if (db.get("user") != null && db.get("pass") != null)connection = new PGPooledConnection(DriverManager.getConnection(
                    String.format("jdbc:postgresql://%s/%s?allowMultiQueries=true", db.get("host") == null ? "localhost" : db.get("host"),
                            db.get("name") == null ? "yamada" : db.get("name")),(String) db.get("user")
                    , (String) db.get("pass")),true);
            if (10 >connection.getConnection().getMetaData().getDatabaseMajorVersion()) throw new SQLException("Postgres major version must be 10 or newer.");
            prepareStatements();
        } catch (ClassNotFoundException e) {
            Kayla.log.log(Level.SEVERE,"Uh oh, looks like the driver wasn't found for the database ;P");
            e.printStackTrace();
            throw e;
        } catch (SQLException e) {
            Kayla.log.log(Level.SEVERE,"oof the bad thing happened and there was an error from getConnection");
            e.printStackTrace();
            throw e;
        }catch (NullPointerException e){
            Kayla.log.log(Level.SEVERE,"Whoops! You forgot a database key, you gotta have db.user and db.pass at least.");
        }
    }

    private void prepareStatements() throws SQLException {
        Connection conn = this.connection.getConnection();
        statements.put("guild",conn.prepareStatement("SELECT * from guilds WHERE id = ?"));
    }

    public ResultSet guildData(String id) throws SQLException {
        PreparedStatement stmt = statements.get("guild");
        stmt.setString(0,id);
        ResultSet query = stmt.executeQuery();
        query.first(); // just wanna make sure row is first row
        return query;
    }
}
