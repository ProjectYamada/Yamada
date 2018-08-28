package com.yamada.notkayla.commands.owner;

import com.yamada.notkayla.Kayla;
import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

@Command(name="load",group = "owner")
public class LoadCommand {
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) throws IllegalAccessException, InstantiationException {
        if (args[0] == null || args[1] == null) {
            event.getChannel().sendMessage("choose `command` or `module` and set the object to load as the argument").queue();
        }
        switch(args[0]){
            case "command":
                Kayla.registry.load(args[1]);
                break;
            case "module":
                String response = Kayla.reloadModule(args[1]);
                break;
            default:
                event.getChannel().sendMessage("choose `command` or `module` for the first argument and set the object to load as the second").queue();
                return;
        }
    }
}
