package com.yamada.notkayla.commands;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public interface Command {
    void run(JDA bot, GuildMessageReceivedEvent event, String[] args);
}
