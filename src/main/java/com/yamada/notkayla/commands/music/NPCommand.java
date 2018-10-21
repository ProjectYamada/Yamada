package com.yamada.notkayla.commands.music;

import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

@Command(name="np",group="music")
public class NPCommand {
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args){
        event.getChannel().sendMessage("will show current and next songs + current time when done making this").queue();
    }
}
