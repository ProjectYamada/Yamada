package com.yamada.notkayla.database;

import com.yamada.notkayla.Kayla;

import java.util.logging.Level;

public class Database {
    public static void init(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            Kayla.log.log(Level.SEVERE,"Uh oh, looks like the driver wasn't found for the database ;P");
            e.printStackTrace();
        }
    }
}
