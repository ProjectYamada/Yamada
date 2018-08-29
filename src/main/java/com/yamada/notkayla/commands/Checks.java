package com.yamada.notkayla.commands;

import com.yamada.notkayla.Config;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.List;

import static com.yamada.notkayla.Config.*;

public class Checks {
    private static Object owners = null;
    public static boolean isNotAdmin(String id){
        if (owners == null)
            owners =
                configuration.get("owners");
        return !((List<String>)owners).contains(id);
    }

    public static boolean isNotNSFW(TextChannel channel) {
        return !channel.isNSFW();
    }
}
