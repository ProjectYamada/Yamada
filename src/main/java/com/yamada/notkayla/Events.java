package com.yamada.notkayla;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ExceptionEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

public class Events extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        //TODO: change prefix handler to something else and instead set it to a database prefix
        String content = event.getMessage().getContentRaw();
        String prefix = "!y";//temp hardcoded prefix cause elf = gay for sagiri
        // elf sagiri megumi threesome when
        if (content.startsWith("!y")){
            String command = content.substring(prefix.length());
            try {
                if (Kayla.registry.has(command)) Kayla.registry.get(command).run(Kayla.bot, event);
            } catch (Exception e) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(new Color(0xff0000));
                embed.setTitle("An error occurred");
                embed.setDescription(String.format("```\n%s\n```", e.getStackTrace()));
                event.getChannel().sendMessage(embed.build()).queue();
            }
        }
    }

    @Override
    public void onException(ExceptionEvent event) {
        StringBuilder tbuild = new StringBuilder();
        String s = event.getCause().getClass().getName();
        String message = event.getCause().getLocalizedMessage();
        tbuild.append((message != null) ? (s + ": " + message) : s);
        StackTraceElement[] trace = event.getCause().getStackTrace();
        for (StackTraceElement traceElement : trace)
            tbuild.append("\tat ").append(traceElement);
        Kayla.bot.getTextChannelById(451175777996898305L).sendMessage("```"+tbuild.toString()+"```").submit();

    }
}
