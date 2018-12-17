package com.yamada.notkayla.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.yamada.notkayla.Yamada;
import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

@Command(name="pause",group="music",description = "Pause or play the current track.")
public class PPCommand {
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args){
        if (!event.getMember().getVoiceState().inVoiceChannel()) {
            event.getChannel().sendMessage("You must be in "+(event.getGuild().getSelfMember().getVoiceState().inVoiceChannel()?"my":"a")
            +" voice chat to use my music commands.").queue();
            return;
        }
        EmbedBuilder embed = new EmbedBuilder();
        List<AudioTrack> queue = Yamada.music.getQueue(event);
        if (queue.isEmpty()) {
            event.getChannel().sendMessage("There is nothing to pause.").queue();
        }
        else Yamada.music.pause(event);
    }
}
