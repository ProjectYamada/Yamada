package com.yamada.notkayla.commands.music;

import com.yamada.notkayla.Kayla;
import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

@Command(name="skip", description = "Skip a track.", group = "music")
public class SkipCommand {
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        Kayla.music.skipTrack(event.getChannel());
    }
}
