package com.yamada.notkayla.commands.general;

import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class TestCommand implements Command {

    @Override
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        event.getChannel().sendMessage(String.format("Your args: %s", String.join(", ", args))).submit();
    }
}
