package com.yamada.notkayla.commands.general;

import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class TestCommand implements Command {

    @Override
    public void run(JDA bot, GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("*notices your bulge* owo what's this").submit();
    }
}
