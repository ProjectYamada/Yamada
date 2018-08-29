package com.yamada.notkayla.commands.owner;

import com.yamada.notkayla.Kayla;
import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;

@Command(name="pull",group="owner",hidden=true)
public class PullCommand {
    public PullCommand(){}
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        if(Kayla.Checks.isNotAdmin(event.getAuthor().getId())) return;
        try {
            event.getChannel().sendTyping().submit();
            Runtime.getRuntime().exec("git pull").waitFor();
            Runtime.getRuntime().exec("gradle build").waitFor();
            event.getChannel().sendMessage("\uD83D\uDC4C").submit();
        } catch (IOException e) {
            e.printStackTrace();
            event.getChannel().sendMessage("\uD83D\uDC4E").submit();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
