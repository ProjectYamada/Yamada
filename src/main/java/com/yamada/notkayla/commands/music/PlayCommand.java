package com.yamada.notkayla.commands.music;

import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioTrack;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeSearchProvider;
import com.sedmelluq.discord.lavaplayer.track.BasicAudioPlaylist;
import com.yamada.notkayla.Kayla;
import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

@Command(name="play", description = "Play something.", group = "music")
public class PlayCommand {
    private StringBuilder term;
    private YoutubeSearchProvider searchProvider = new YoutubeSearchProvider(new YoutubeAudioSourceManager());
    private BasicAudioPlaylist playlist;
    private EmbedBuilder embed = new EmbedBuilder();
    private YoutubeAudioTrack[] track;

    //TODO: Add search capabilities.
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        term = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            term.append(args[i]).append("+");
        }
        playlist = (BasicAudioPlaylist) searchProvider.loadSearchResult(term.toString().substring(0, term.length() - 1));
        //track = playlist.getTracks().get(0).getInfo().title;
        embed.setTitle(playlist.getName().replace("+", " "));
        embed.addField("Results", String.valueOf(playlist.getTracks().size()), false);
        embed.setFooter(String.format("Requested by %s", event.getAuthor().getName()), event.getAuthor().getAvatarUrl());
        event.getChannel().sendMessage(embed.build()).queue();
        embed.clear();
        //Kayla.music.loadAndPlay(event.getChannel(), String.join("", args));
    }
}
