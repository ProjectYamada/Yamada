package com.yamada.notkayla.commands.music;

import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeSearchProvider;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.BasicAudioPlaylist;
import com.yamada.notkayla.Kayla;
import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

@Command(name="play", description = "Play something.", group = "music")
public class PlayCommand {
    private YoutubeSearchProvider searchProvider = new YoutubeSearchProvider(new YoutubeAudioSourceManager());
//    private String display_track = "";

    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        EmbedBuilder embed = new EmbedBuilder();
        if (args.length == 1){
            event.getChannel().sendMessage("Not enough arguments provided").queue();
            return;
        }
        AudioTrack[] track = new AudioTrack[5];

        // !yplay Gee Girls Generation
        StringBuilder term = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            term.append(args[i]).append("+");
        }

        // Our search results return as a playlist. We will only get the first 5 in that list.
        BasicAudioPlaylist playlist = (BasicAudioPlaylist) searchProvider.loadSearchResult(term.toString().substring(0, term.length() - 1));
        for (int i = 0; i < 5; i++) {
            track[i] = playlist.getTracks().get(i);
            embed.addField("**[" + (i+1) + ". " + track[i].getInfo().title + "]","(" + track[i].getInfo().uri + ")**",false);
        }
        embed.setTitle(playlist.getName().replace("+", " "));
//        embed.addField("Results: \n", display_track, false);
        embed.setFooter(String.format("Requested by %s", event.getAuthor().getName()), event.getAuthor().getAvatarUrl());
        event.getChannel().sendMessage(embed.build()).queue();

//        Clear the used variables.
//        embed.clear();
//        display_track = "";

        // TODO: Add user input so we don't default to first thing on the search results.
        Kayla.music.loadAndPlay(event, track[0].getInfo().uri);
    }
}
