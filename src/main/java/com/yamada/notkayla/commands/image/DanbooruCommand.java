package com.yamada.notkayla.commands.image;

import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.awt.*;
import java.io.IOException;
import org.json.*;

public class DanbooruCommand implements Command {
    @Override
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        if (!event.getChannel().isNSFW()) {
            event.getChannel().sendMessage("You need to be in a NSFW channel for this.").submit();
            return;
        }
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(0xe91e63);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            CloseableHttpResponse response = httpClient.execute(
                    new HttpGet("https://danbooru.donmai.us/posts/random.json?search[-status]=deleted"));
            String responseBody = EntityUtils.toString(response.getEntity()).replace("\n", "");
            JSONObject json = new JSONObject(responseBody);
            embed.setTitle("Successfully got an image.", json.getString("file_url"));
            embed.setDescription("If you can't see this image, click the title.");
            embed.setImage(json.getString("large_file_url"));
        } catch (IOException e) {
            e.printStackTrace();
            embed.setColor(new Color(0xff0000));
            embed.setTitle("An Error Occurred");
            embed.setDescription(String.format("```\n%s\n```", e.getMessage()));
            event.getChannel().sendMessage(embed.build()).queue();
            return;
        }
        embed.setFooter(String.format("Requested by %s", event.getAuthor().getName()),
                event.getAuthor().getAvatarUrl());
        event.getChannel().sendMessage(embed.build()).submit();
    }
}
