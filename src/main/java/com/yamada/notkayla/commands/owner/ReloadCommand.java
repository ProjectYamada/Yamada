package com.yamada.notkayla.commands.owner;

import com.yamada.notkayla.Events;
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
        Events.registry.reload(args[1]);
    }
}
