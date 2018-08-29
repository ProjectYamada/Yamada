package com.yamada.notkayla.commands.owner;

import com.yamada.notkayla.Kayla;
import com.yamada.notkayla.commands.Checks;
import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

@Command(name="reload",group = "owner",hidden = true)
public class ReloadCommand {
    public void run (JDA bot, GuildMessageReceivedEvent event,String[] args) throws IllegalAccessException, InstantiationException {
        if(Checks.isNotAdmin(event.getAuthor().getId())) return;
        if (args[0] == null || args[1] == null) {
            event.getChannel().sendMessage("choose `command` or `module` and set the object to reload as the argument").queue();
        }
        switch(args[0]){
            case "command":
                Kayla.registry.reload(args[1]);
                break;
            case "module":
                String response = Kayla.reloadModule("");
                break;
            default:
                event.getChannel().sendMessage("choose `command` or `module` for the first argument and set the object to reload as the second").queue();
                break;
        }
    }
}
