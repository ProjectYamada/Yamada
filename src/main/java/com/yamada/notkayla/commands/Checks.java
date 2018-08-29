package com.yamada.notkayla.commands;

import com.yamada.notkayla.Config;
import com.yamada.notkayla.Kayla;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static com.yamada.notkayla.Config.*;

public class Checks {
    private static List owners = null;
    public static boolean isNotAdmin(String id){
        if (owners == null) {
            Kayla.log.log(Level.INFO, (String) configuration.get("owners"));
            owners = (List) configuration.get("owners");
        }
        return !owners.contains(id);
    }

    public static boolean isNotNSFW(TextChannel channel) {
        return !channel.isNSFW();
    }
}
