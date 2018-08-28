package com.yamada.notkayla.commands;

import com.yamada.notkayla.Config;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.util.List;

public class Checks {
    private static List<String> owners = null;
    public static boolean isNotAdmin(String id){
        if (owners == null){
            owners = (List<String>) Config.configuration.get("owners");
            for (String owner : owners)System.out.println(owner);
        }
        return !owners.contains((String) id);
    }
    
    public static boolean isNSFW(MessageChannel channel) {
        return !channel.isNSFW();   
    }
}
