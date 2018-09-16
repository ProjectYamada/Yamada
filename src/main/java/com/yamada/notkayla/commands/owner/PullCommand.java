package com.yamada.notkayla.commands.owner;

import com.yamada.notkayla.Kayla;
import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.apache.http.client.RedirectHandler;

import java.io.IOException;

@Command(name="pull",group="owner",hidden=true)
public class PullCommand {
    public PullCommand(){}
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        if(bot.getGuildById("481210197453438996").getMemberById(event.getAuthor().getId()) == null) return;
        try {
            event.getChannel().sendTyping().submit();
            ProcessBuilder git = new ProcessBuilder("git","pull");
            git.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            git.redirectError(ProcessBuilder.Redirect.INHERIT);
            git.start().waitFor();
            ProcessBuilder comp = new ProcessBuilder("gradle","compileJava");
            comp.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            comp.redirectError(ProcessBuilder.Redirect.INHERIT);
            int compile = comp.start().waitFor();
            if (compile !=0) throw new IOException("couldn't compile code.");
            event.getChannel().sendMessage("\uD83D\uDC4C").submit();
        } catch (IOException e) {
            e.printStackTrace();
            event.getChannel().sendMessage("\uD83D\uDC4E").submit();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
