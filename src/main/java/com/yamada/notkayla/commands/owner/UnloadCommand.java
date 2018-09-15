package com.yamada.notkayla.commands.owner;

import com.yamada.notkayla.Events;
import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

@Command(name="unload", group="owner")
public class UnloadCommand {
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        if(bot.getGuildById("481210197453438996").getMemberById(event.getAuthor().getId()) == null) return;
        Events.registry.unload(args[1]);
    }
}
