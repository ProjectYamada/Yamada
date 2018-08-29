package com.yamada.notkayla.commands;

import net.dv8tion.jda.core.entities.TextChannel;

import java.util.ArrayList;

public class Checks {
    public static ArrayList owners = new ArrayList();
    public static boolean isNotAdmin(String id){
        return !owners.contains(id);
    }

    public static boolean isNotNSFW(TextChannel channel) {
        return !channel.isNSFW();
    }
}
