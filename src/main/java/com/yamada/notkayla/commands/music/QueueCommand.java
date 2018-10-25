package com.yamada.notkayla.commands.music;

import com.yamada.notkayla.Yamada;
import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

@Command(name="queue",group="music")
public class QueueCommand {
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args){
        Yamada.music.getQueue(event, args);
    }
}
