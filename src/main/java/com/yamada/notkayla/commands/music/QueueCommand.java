package com.yamada.notkayla.commands.music;

import com.yamada.notkayla.Kayla;
import com.yamada.notkayla.commands.Command;
import com.yamada.notkayla.module.modules.audio.MusicModule;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

@Command(name="queue",group="music")
public class QueueCommand {
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args){
        MusicModule.GuildMusicManager guildAudioPlayer = Kayla.music.getGuildAudioPlayer(event.getGuild(), event.getChannel());
        guildAudioPlayer.scheduler.queue.remainingCapacity();
    }
}
