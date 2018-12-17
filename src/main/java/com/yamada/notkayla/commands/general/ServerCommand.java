package com.yamada.notkayla.commands.general;

import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

@Command(name="server",description = "Gets the invite to Yamada's server",group = "general")
public class ServerCommand {
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0xe91e63));
        embed.setTitle("Want to join my support server?");
        embed.setDescription("Join my server by clicking [here.](https://discord.gg/85V5p3F");
        embed.setThumbnail(event.getGuild().getSelfMember().getUser().getAvatarUrl());
        event.getChannel().sendMessage(embed.build()).queue();
    }
}
