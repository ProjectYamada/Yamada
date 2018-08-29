package com.yamada.notkayla.commands;

import com.yamada.notkayla.Config;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.List;

import static com.yamada.notkayla.Config.*;

public class Checks {
    private static List<String> owners = null;
    public static boolean isNotAdmin(String id){
        if (owners == null)
            owners =
                (List<String>)
                configuration.get("owners");
        return !owners.contains(id);
    }

    public static boolean isNotNSFW(TextChannel channel) {
        return !channel.isNSFW();
    }
}
