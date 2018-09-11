package com.yamada.notkayla.module.modules.database;

import com.yamada.notkayla.module.Adapter;
import com.yamada.notkayla.module.entities.GuildData;
import com.yamada.notkayla.module.entities.Result;
import com.yamada.notkayla.module.entities.UserData;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.util.Map;

public class DatabaseAdapter extends Adapter {
    public DatabaseAdapter(Map config) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        super("com.yamada.notkayla.module.modules.database.DatabaseModule",config);
    }
    public Result createUserData(String id) {
        try {
            runMethod("",id);
            return new Result(true,"Successfully created user entry");
        }catch (Exception e){
            return new Result(false,"Could not create user data",null,e);
        }
    }
    public Result guildData (String id) {
        try {
            ResultSet query = (ResultSet) runMethod("guildData", id);
            GuildData guildData = new GuildData(query.getString("gid"), query.getString("prefix"), query.getBoolean("customPrefix"));
            return new Result(false,"Successfully retrieved server data",guildData);
        }catch(Exception e){
            return new Result(false,"Could not retrieve server data",null,e);
        }
    }
    public Result userData(String id) {
        try {
            ResultSet query = (ResultSet) runMethod("userData", id);
            UserData userData = new UserData(query.getString("uid"), query.getLong("coins"));
            return new Result(true,"Successfully retrieved user data",userData);
        }catch (Exception e){
            return new Result(false,"Could not retrieve user data",null,e);
        }
    }
}
