package com.yamada.notkayla.commands;

import com.yamada.notkayla.Config;
import com.yamada.notkayla.Kayla;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static com.yamada.notkayla.Config.*;

public class Checks {
    private static ArrayList<String> owners = null;
    public static boolean isNotAdmin(String id){
        Kayla.log.log(Level.INFO, String.valueOf(configuration));
        if (owners == null)
            owners =
                    (ArrayList<String>) configuration.get("owners");
        return !((List<String>)owners).contains(id);
    }

    public static boolean isNotNSFW(TextChannel channel) {
        return !channel.isNSFW();
    }
}
