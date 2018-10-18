package com.yamada.notkayla.commands.music;

import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeSearchProvider;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.BasicAudioPlaylist;
import com.yamada.notkayla.Kayla;
import com.yamada.notkayla.commands.Command;
import com.yamada.notkayla.utils.SelectionManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;

@Command(name="play", description = "Play something.", group = "music")
public class PlayCommand {
    private YoutubeSearchProvider searchProvider = new YoutubeSearchProvider(new YoutubeAudioSourceManager());

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
        int size = playlist.getTracks().size() > 5 ? 5 : playlist.getTracks().size();
        for (int i = 0; i < size; i++) {
            track[i] = playlist.getTracks().get(i);
            embed.addField((i+1)+". **"+track[i].getInfo().title +" by "+ track[0].getInfo().author + "**","**["+track[i].getInfo().uri+"](" + track[i].getInfo().uri + ")**",false);
        }
        embed.setTitle(playlist.getName().replace("+", " "));
        embed.setDescription("Use !ysel <number> to select a song.");
        embed.setFooter(String.format("Requested by %s", event.getAuthor().getName()), event.getAuthor().getAvatarUrl());
        event.getChannel().sendMessage(embed.build()).submit();
        SelectionManager.Selection selection = SelectionManager.requestSelection(event.getAuthor().getIdLong(), 1, 5,1);
        selection.get().whenComplete((integer, throwable) -> Kayla.music.loadAndPlay(event, track[integer -1].getInfo().uri));
    }
}
