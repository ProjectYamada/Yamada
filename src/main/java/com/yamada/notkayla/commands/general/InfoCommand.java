package com.yamada.notkayla.commands.general;

import com.yamada.notkayla.Yamada;
import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Date;

@Command(name="info",group="general",description = "Shows general info about Yamada")
public class InfoCommand{
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0xe91e63));
        embed.setTitle("Wanna know more about me?");
        embed.setDescription("I'm being built by Sanae, Bill, Kaniel, and Jan.");
        embed.setThumbnail(event.getGuild().getSelfMember().getUser().getAvatarUrl());
        embed.setFooter(String.format("Hello, %s", event.getAuthor().getName()), event.getAuthor().getAvatarUrl());
        embed.addField("Version:", Yamada.version, false);
        embed.addField("JDA: ", "3.8.1_439", false);
//        embed.addField("Bot Uptime",rb.getUptime())
        embed.addField("Users: ", String.valueOf(bot.getUsers().size()), false);
        embed.addField("Guilds: ", String.valueOf(bot.getGuilds().size()), false);
        //embed.addField("Shard: ", bot.getShardInfo().getShardString(), false);
        event.getChannel().sendMessage(embed.build()).queue();
    }
}
