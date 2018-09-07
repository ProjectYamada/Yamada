package com.yamada.notkayla.commands.music;


import com.yamada.notkayla.Kayla;
import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

@Command(name="play", description = "Play something.", group = "music")
public class PlayCommand {
    //TODO: Complete this command.
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        Kayla.musicModule.loadAndPlay(event.getChannel(), args[1]);
    }
}
