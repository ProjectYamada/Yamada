package com.yamada.notkayla;

import com.yamada.notkayla.utils.MiscTools;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.ExceptionEvent;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Arrays;

public class Events extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        //TODO: change prefix handler to something else and instead set it to a database prefix
        String content = event.getMessage().getContentRaw();
        String prefix = String.valueOf(Kayla.configuration.get("prefix"));
        // elf sagiri megumin threesome when
        if (content.startsWith(prefix)){
            String command = content.split(" ")[0].substring(prefix.length());
            String[] args = event.getMessage().getContentRaw().substring(prefix.length()+command.length()).split(" ");
            try {
                if (Kayla.registry.has(command)) {
                    Kayla.registry.run(command,event.getJDA(),event,args);
                }
            } catch (Exception e) {
                Throwable cause = MiscTools.getCause(e);
                e.printStackTrace();
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(new Color(0xff0000));
                embed.setTitle("An error occurred");
                embed.setDescription(String.format("```\n%s\n```", Arrays.toString(cause.getStackTrace())));
                event.getChannel().sendMessage(embed.build()).queue();
                onException(new ExceptionEvent(event.getJDA(), cause, true));
            }
        }
    }
    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        event.getJDA().getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB,Game.playing("with "+event.getJDA().getGuilds().size() + " guilds - !yhelp"));
    }
    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        event.getJDA().getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB,Game.playing("with "+event.getJDA().getGuilds().size() + " guilds - !yhelp"));
    }
    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        if (!event.getAuthor().isBot())event.getMessage().getChannel().sendMessage("Sorry, I don't support direct messaging. Use me in a server instead.").queue();
    }
    
    @Override
    public void onException(ExceptionEvent event) {
        Throwable cause = MiscTools.getCause(event.getCause());
        StringBuilder tbuild = new StringBuilder();
        String s = cause.getClass().getName();
        String message = cause.getLocalizedMessage();
        tbuild.append((message != null) ? (s + ": " + message) : s);
        StackTraceElement[] trace = cause.getStackTrace();
        for (StackTraceElement traceElement : trace)
            tbuild.append("\tat ").append(traceElement);
        event.getJDA().getTextChannelById("481510285442547723").sendMessage("```"+tbuild.toString()+"```").submit();
    }
    
    @Override
    public void onReady(ReadyEvent event) {
        System.out.println("Yamada has connected to Discord.");
        event.getJDA().getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB,Game.playing("with "+event.getJDA().getGuilds().size() + " guilds - !yhelp"));
    }
}
