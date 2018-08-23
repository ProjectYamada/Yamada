package com.yamada.notkayla.commands.general;

import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

@Command(name="ping",group="general")
public class PingCommand{
    public PingCommand(){}
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        event.getChannel().sendMessage("Pong! ``" + String.valueOf(bot.getPing()) + " ms``").submit();
    }
}
