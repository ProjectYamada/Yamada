package com.yamada.notkayla.commands.owner;

import com.yamada.notkayla.commands.Command;
import com.yamada.notkayla.module.modules.database.DatabaseModule;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.sql.SQLException;

@Command(name="generate",hidden = true,group="economy")
public class GenerateCommand{
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        if(bot.getGuildById("481210197453438996").getMemberById(event.getAuthor().getId()) == null) return;
        event.getChannel().sendMessage("sorry bro this isn't done lmao").queue();
    }
}