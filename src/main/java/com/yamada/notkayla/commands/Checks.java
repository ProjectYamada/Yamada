package com.yamada.notkayla.commands;

import com.yamada.notkayla.Config;

import java.util.List;

public class Checks {
    private static List<String> owners;
    public static boolean isAdmin(String id){
        if (owners == null){
            owners = (List) Config.configuration.get("owners");
        }
        return owners.contains(id);
    }
}
