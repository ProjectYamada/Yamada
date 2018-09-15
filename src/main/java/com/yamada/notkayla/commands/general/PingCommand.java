package com.yamada.notkayla.commands.general;

import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

@Command(name="ping",group="general",description = "Pong!")
public class PingCommand{
    public PingCommand(){}
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        long now = System.currentTimeMillis();
        event.getChannel().sendMessage(":question: Pong?").queue(x -> {
            long ping = System.currentTimeMillis() - now;
            x.editMessage(String.format("**WS**: ``%s`` | **Message**: ``%s``", bot.getPing(), ping)).submit();
        });
    }
}
