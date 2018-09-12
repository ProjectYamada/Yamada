package com.yamada.notkayla.commands.owner;

import com.yamada.notkayla.Kayla;
import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.logging.Level;

@Command(name="reload",group = "owner",hidden = true)
public class ReloadCommand {
    public void run (JDA bot, GuildMessageReceivedEvent event,String[] args) throws IllegalAccessException, InstantiationException {
        if(bot.getGuildById("481210197453438996").getMemberById(event.getAuthor().getId()) == null) return;
        Kayla.log.log(Level.INFO, Arrays.toString(args));
        if (args[1] == null || args[2] == null) {
            event.getChannel().sendMessage("choose `command` or `module` and set the object to reload as the argument").queue();
        }
        switch(args[0]){
            case "command":
                Kayla.registry.reload(args[2]);
                break;
            case "module":
                String response = Kayla.reloadModule(args[2]);
                break;
            default:
                event.getChannel().sendMessage("choose `command` or `module` for the first argument and set the object to reload as the second").queue();
                break;
        }
    }
}
