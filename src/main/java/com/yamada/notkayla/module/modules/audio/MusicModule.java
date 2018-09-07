package com.yamada.notkayla.module.modules.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import com.yamada.notkayla.module.Module;
import net.dv8tion.jda.core.audio.AudioSendHandler;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Module(name="audio",guarded = false)
public class MusicModule {
    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;

    public MusicModule() {
        playerManager = new DefaultAudioPlayerManager();
        musicManagers = new HashMap<>();

        AudioSourceManagers.registerLocalSource(playerManager);
        AudioSourceManagers.registerRemoteSources(playerManager);
    }

    // From the 1.3.0 demo of Lavaplayer.
    private synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        long guildId = Long.parseLong(guild.getId());
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    public void loadAndPlay(TextChannel channel, String trackURL) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

        playerManager.loadItemOrdered(musicManager, trackURL, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                channel.sendMessage("Now playing: " + track.getInfo().title).queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }

                channel.sendMessage("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();

                play(channel.getGuild(), musicManager, firstTrack);
            }

            @Override
            public void noMatches() {
                channel.sendMessage("Can't find any matches. :(").queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("Not only did the loading fail, but what comes next will be a total disaster.").queue();
            }
        });
    }
    private void play(Guild guild, GuildMusicManager musicManager, AudioTrack track) {
        connectToFirstVoiceChannel(guild.getAudioManager());

        musicManager.scheduler.queue(track);
    }

    @SuppressWarnings("unused")
    private void skipTrack(TextChannel channel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.nextTrack();

        channel.sendMessage("Skipped to next track.").queue();
    }

    private static void connectToFirstVoiceChannel(AudioManager audioManager) {
        if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
            List<VoiceChannel> voiceChannels = audioManager.getGuild().getVoiceChannels();
            VoiceChannel voiceChannel = null;
            if(voiceChannels.size() != 0) voiceChannel = voiceChannels.get(0);
            if (voiceChannel == null) return;
            audioManager.openAudioConnection(voiceChannel);
        }
    }

    class GuildMusicManager {
        /**
         * Audio player for the guild.
         */
        final AudioPlayer player;
        /**
         * Track scheduler for the player.
         */
        final TrackScheduler scheduler;

        /**
         * Creates a player and a track scheduler.
         * @param manager Audio player manager to use for creating the player.
         */
        GuildMusicManager(AudioPlayerManager manager) {
            player = manager.createPlayer();
            scheduler = new TrackScheduler(player);
            player.addListener(scheduler);
        }

        /**
         * @return Wrapper around AudioPlayer to use it as an AudioSendHandler.
         */
        AudioPlayerSendHandler getSendHandler() {
            return new AudioPlayerSendHandler(player);
        }
    }

    // The following classes are pulled directly from the lavaplayer JDA demo, and possibly violate Sanae's module model. no longer violates my model :)
// TODO: Find a way to simplify this.
    class TrackScheduler extends AudioEventAdapter {
        private final AudioPlayer player;
        private final BlockingQueue<AudioTrack> queue;

        /**
         * @param player The audio player this scheduler uses
         */
        TrackScheduler(AudioPlayer player) {
            this.player = player;
            this.queue = new LinkedBlockingQueue<>();
        }

        /**
         * Add the next track to queue or play right away if nothing is in the queue.
         *
         * @param track The track to play or add to queue.
         */
        public void queue(AudioTrack track) {
            // Calling startTrack with the noInterrupt set to true will start the track only if nothing is currently playing. If
            // something is playing, it returns false and does nothing. In that case the player was already playing so this
            // track goes to the queue instead.
            if (!player.startTrack(track, true)) {
                queue.offer(track);
            }
        }

        /**
         * Start the next track, stopping the current one if it is playing.
         */
        void nextTrack() {
            // Start the next track, regardless of if something is already playing or not. In case queue was empty, we are
            // giving null to startTrack, which is a valid argument and will simply stop the player.
            player.startTrack(queue.poll(), false);
        }

        @Override
        public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
            // Only start the next track if the end reason is suitable for it (FINISHED or LOAD_FAILED)
            if (endReason.mayStartNext) {
                nextTrack();
            }
        }
    }
    /**
     * This is a wrapper around AudioPlayer which makes it behave as an AudioSendHandler for JDA. As JDA calls canProvide
     * before every call to provide20MsAudio(), we pull the frame in canProvide() and use the frame we already pulled in
     * provide20MsAudio().
     */
    class AudioPlayerSendHandler implements AudioSendHandler {
        private final AudioPlayer audioPlayer;
        private AudioFrame lastFrame;

        /**
         * @param audioPlayer Audio player to wrap.
         */
        AudioPlayerSendHandler(AudioPlayer audioPlayer) {
            this.audioPlayer = audioPlayer;
        }

        @Override
        public boolean canProvide() {
            if (lastFrame == null) {
                lastFrame = audioPlayer.provide();
            }

            return lastFrame != null;
        }

        @Override
        public byte[] provide20MsAudio() {
            if (lastFrame == null) {
                lastFrame = audioPlayer.provide();
            }

            byte[] data = lastFrame != null ? lastFrame.getData() : null;
            lastFrame = null;

            return data;
        }

        @Override
        public boolean isOpus() {
            return true;
        }
    }
}