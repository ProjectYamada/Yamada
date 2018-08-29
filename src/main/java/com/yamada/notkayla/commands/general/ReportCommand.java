package com.yamada.notkayla.commands.general;

import com.yamada.notkayla.Kayla;
import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

@Command(name="report",group="general")
public class ReportCommand {
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        StringBuilder reported = new StringBuilder();
        EmbedBuilder embed = new EmbedBuilder();
        try {
            bot.getTextChannelById(String.valueOf(Kayla.configuration.get("report-id")));
        } catch (Exception e) {
            event.getChannel().sendMessage("Invalid integer").queue();
            return;
        }
        for (int i = 1; i < args.length; i++)
            reported.append(args[i]).append(" ");
        embed.setTitle("Received a report.");
        embed.setDescription("We have received a report.");
        embed.setColor(0xff0000);
        embed.addField("Report: ", String.valueOf(reported), false);
        embed.addField("Sender: ", event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(), false);
        bot.getTextChannelById(String.valueOf(Kayla.configuration.get("report-id"))).sendMessage(embed.build()).queue();
        event.getChannel().sendMessage("Your report has been sent.").queue();
    }
}
