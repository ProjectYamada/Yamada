package com.yamada.notkayla.module;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DatabaseAdapter extends Adapter{
    public DatabaseAdapter(){
        super(cl.loadClass("com.yamada.notkayla.module.database.DatabaseModule"));
        this.runMethod("",10);
    }
    public void query(){

    }
}
