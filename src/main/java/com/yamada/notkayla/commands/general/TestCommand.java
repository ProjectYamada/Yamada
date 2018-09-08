package com.yamada.notkayla.commands.general;

import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

@Command(name="test",group="general",description = "a command for testing")
public class TestCommand{
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        event.getChannel().sendMessage(String.format("Your args: ```%s```Are you admin: `%b`\nDid reloading work: true", String.join(", ", args),bot.getGuildById("481210197453438996").isMember(event.getAuthor()))).submit();
    }
}
