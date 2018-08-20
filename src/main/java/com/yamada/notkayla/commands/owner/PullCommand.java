package com.yamada.notkayla.commands.owner;

import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;

public class PullCommand implements Command {

    @Override
    public void run(JDA bot, GuildMessageReceivedEvent event) {
        try {
            event.getChannel().sendTyping().submit();
            Runtime.getRuntime().exec("git pull");
            event.getChannel().sendMessage("\uD83D\uDC4C").submit();
        } catch (IOException e) {
            e.printStackTrace();
            event.getChannel().sendMessage("\uD83D\uDC4E").submit();
        }
    }
}
