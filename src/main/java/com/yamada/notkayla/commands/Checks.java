package com.yamada.notkayla.commands;

import com.yamada.notkayla.Config;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.List;

public class Checks {
    private static List<String> owners = null;
    public static boolean isNotAdmin(String id){
        if (owners == null){
            owners = (List<String>) Config.configuration.get("owners");
            for (String owner : owners)System.out.println(owner);
        }
        return !owners.contains(id);
    }

    public static boolean isNSFW(TextChannel channel) {
        return !channel.isNSFW();
    }
}
