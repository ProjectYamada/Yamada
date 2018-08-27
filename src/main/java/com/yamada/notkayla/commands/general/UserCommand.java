package com.yamada.notkayla.commands.general;

import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

@Command(name="user",group="general",description="View information on a server member")
public class UserCommand {
    public UserCommand(){}
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        //TODO: Allow people to get user information from other people.
        //String arg = event.getMessage().getContentRaw().substring("!yuser ".length());
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Information collected!");
        embed.setDescription("Here's what we know about this user!");
        embed.setColor(new Color(0xe91e63));
        embed.setFooter(String.format("Hello, %s", event.getAuthor().getName()), event.getAuthor().getAvatarUrl());
        embed.setThumbnail(event.getAuthor().getAvatarUrl());
        embed.addField("User ID: ", event.getAuthor().getId(), false);
        embed.addField("Joined on: ", String.valueOf(event.getAuthor().getCreationTime()), false);
        event.getChannel().sendMessage(embed.build()).submit();
    }
}
