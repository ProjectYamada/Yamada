package com.yamada.notkayla.module.modules.database;

import com.yamada.notkayla.module.Adapter;
import com.yamada.notkayla.module.entities.GuildData;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseAdapter extends Adapter {
    public DatabaseAdapter() throws InstantiationException, IllegalAccessException {
        super("com.yamada.notkayla.module.modules.database.DatabaseModule");
    }
    public GuildData guildData (String id) throws SQLException, InvocationTargetException, IllegalAccessException {
        ResultSet query = (ResultSet) runMethod("run",id);
        return new GuildData(query.getString("gid"),query.getString("prefix"),query.getBoolean("customPrefix"));
    }
}
