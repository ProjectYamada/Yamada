package com.yamada.notkayla.commands.music;


import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeSearchProvider;
import com.yamada.notkayla.Kayla;
import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

@Command(name="play", description = "Play something.", group = "music")
public class PlayCommand {
    private StringBuilder term = new StringBuilder();
    //TODO: Add search capabilities.
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        //MusicAdapter music = (MusicAdapter) Kayla.modules.get("music");
        YoutubeSearchProvider searchProvider = new YoutubeSearchProvider(new YoutubeAudioSourceManager());
        for (int i = 1; i < args.length; i++) {
            System.out.println(args[i]);
            term.append(args[i]).append("+");
        }
        searchProvider.loadSearchResult(term.toString());
        Kayla.music.loadAndPlay(event.getChannel(), String.join("", args));
    }
}
