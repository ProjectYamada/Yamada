package com.yamada.notkayla.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.yamada.notkayla.Kayla;
import com.yamada.notkayla.commands.Command;
import com.yamada.notkayla.module.modules.audio.MusicModule;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

@Command(name="queue",group="music")
public class QueueCommand {
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args){
        Kayla.music.getQueue(event, args);
    }
}
