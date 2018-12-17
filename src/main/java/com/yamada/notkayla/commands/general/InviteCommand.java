package com.yamada.notkayla.commands.general;

import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

@Command(name="invite",group="general",description="Invite the bot with this link!")
public class InviteCommand {
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0xe91e63));
        embed.setTitle("Want to invite me to your server?");
        embed.setDescription("You can invite me by clicking [here.](https://discordapp.com/api/oauth2/authorize?client_id=412095213700382740&permissions=0&scope=bot)");
        embed.setThumbnail(event.getGuild().getSelfMember().getUser().getAvatarUrl());
        event.getChannel().sendMessage(embed.build()).queue();
    }
}
