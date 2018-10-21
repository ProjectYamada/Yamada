package com.yamada.notkayla.commands.music;

import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

//HAHAHAHAHHAHA HESAID PP AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
@Command(name="pause",group="music")
public class PPCommand {
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args){
        event.getChannel().sendMessage("will play or pause when done making this").queue();
    }
}
