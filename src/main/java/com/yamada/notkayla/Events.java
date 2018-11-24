package com.yamada.notkayla;

import com.yamada.notkayla.commands.CommandRegistry;
import com.yamada.notkayla.module.modules.DatabaseModule;
import com.yamada.notkayla.utils.MiscTools;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.ExceptionEvent;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Arrays;
import java.util.logging.Level;

public class Events extends ListenerAdapter {
    public static CommandRegistry registry = new CommandRegistry();

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if(event.getAuthor().isBot())return;
        //TODO  : change prefix handler to something else and instead set it to a database prefix
        String content = event.getMessage().getContentRaw();
        String prefix = String.valueOf(Yamada.configuration.get("prefix"));
        // elf sagiri megumin threesome when
        if (content.startsWith(prefix)){
            String command = content.split(" ")[0].substring(prefix.length());
            String[] args;
            if (content.length() >= prefix.length()+command.length()) args = event.getMessage().getContentRaw().substring(prefix.length()+command.length()).split(" ");
            else args = new String[0];
            try {
                if (registry.has(command)) {
                    // TODO: command and group disabling
                    registry.run(command,event.getJDA(),event,args);
                }
            } catch (Exception e) {
                Throwable cause = MiscTools.getCause(e);
                e.printStackTrace();
                MessageEmbed embed = new EmbedBuilder()
                        .setColor(new Color(0xFF0000))
                        .setTitle("An error occured")
                        .setDescription("```\n" + Arrays.toString(cause.getStackTrace()) + "```")
                        .build();
                event.getChannel().sendMessage(embed).queue();
                onException(new ExceptionEvent(event.getJDA(), cause, true));
            }
        }
    }
    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        event.getJDA().getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB,Game.playing("with "+event.getJDA().getGuilds().size() + " guilds - "+Yamada.configuration.get("prefix")+"help"));
    }
    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        event.getJDA().getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB,Game.playing("with "+event.getJDA().getGuilds().size() + " guilds - "+Yamada.configuration.get("prefix")+"help"));
    }
    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        if (!event.getAuthor().isBot()) event.getMessage().getChannel().sendMessage("Sorry, I don't support direct messaging. Use me in a server instead.").queue();
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
        event.getJDA().getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB,Game.playing("with "+event.getJDA().getGuilds().size() + " guilds - "+Yamada.configuration.get("prefix")+"help"));
        try {
            registry.register();
            DatabaseModule.init(Yamada.configuration);
        } catch (Exception e) {
            e.printStackTrace();
            Yamada.log.log(Level.INFO,"I GUESS SOME REGISTERING COMMANDS DID AND OOPSIE WHOOPSIE");
        }
    }
}
