package com.yamada.notkayla.commands.owner;

import com.yamada.notkayla.Kayla;
import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

@Command(name="load",group = "owner")
public class LoadCommand {
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args){
        if (args[1] == null){
            event.getChannel().sendMessage("You need to specify a command to load!").queue();
        }
        switch(args[0]){
            case "command":
                Kayla.registry.get(args[1]);
        }
    }
}
