package com.yamada.notkayla.commands.general;

import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class PingCommand implements Command {
    @Override
    public void run(JDA bot, GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("Pong! ``" + String.valueOf(bot.getPing()) + " ms``").submit();
    }
}
