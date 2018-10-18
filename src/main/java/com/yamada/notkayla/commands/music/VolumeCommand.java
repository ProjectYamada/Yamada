package com.yamada.notkayla.commands.music;

import com.yamada.notkayla.Kayla;
import com.yamada.notkayla.commands.Command;
import com.yamada.notkayla.module.modules.audio.MusicModule;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

@Command(name="vol",group="music")
public class VolumeCommand {
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args){
        if (args.length == 1){
            event.getChannel().sendMessage("Supply a number.").queue();
            return;
        }
        int volume = Integer.parseInt(args[1]);
        int v = volume > 150 ? 150 : volume;
        int v2 = v < 10 ? 10 : v;
        MusicModule.GuildMusicManager guildAudioPlayer = Kayla.music.getGuildAudioPlayer(event.getGuild(), event.getChannel());
        guildAudioPlayer.player.setVolume(v);
    }
}
