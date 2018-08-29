package com.yamada.notkayla.commands;

import com.yamada.notkayla.Kayla;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.ArrayList;
import java.util.List;

public class Checks {
    public static boolean isNotAdmin(String id){
        return !Kayla.owners.contains(id);
    }
}
