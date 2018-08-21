package com.yamada.notkayla.commands.general;

import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class InfoCommand implements Command {
    @Override
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0xe91e63));
        embed.setTitle("Wanna know more about me?");
        embed.setDescription("I'm being built by (insert name here).");
        embed.setThumbnail(event.getGuild().getSelfMember().getUser().getAvatarUrl());
        embed.setFooter(String.format("Hello, %s", event.getAuthor().getName()), event.getAuthor().getAvatarUrl());
        embed.addField("Version:", "throw new NotImplementedError();", false);
        embed.addField("JDA: ", "3.7.1_397", false);
        embed.addField("Users: ", String.valueOf(bot.getUsers().size()), false);
        embed.addField("Guilds: ", String.valueOf(bot.getGuilds().size()), false);
        //embed.addField("Shard: ", bot.getShardInfo().getShardString(), false);
        event.getChannel().sendMessage(embed.build()).queue();
    }
}
