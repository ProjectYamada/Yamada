package com.yamada.notkayla.commands.fun;

import com.yamada.notkayla.Config;
import com.yamada.notkayla.Kayla;
import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import org.json.*;

import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class YoutubeCommand implements Command {
    private static CloseableHttpResponse response;
    private static String responseBody;
    private static JSONObject jsonResponse;

    @Override
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        String query = String.join(" ", args);
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0xe91e63));
        embed.setTitle("YouTube Search Result - " + query);
        CloseableHttpClient client = HttpClientBuilder.create().build();
        try {
            response = client.execute(new HttpGet("https://www.googleapis.com/youtube/v3/search?maxResults=1&part=snippet&type=video&key=" + Config.configuration.get("youtube-api") + "&q=" + query.replace(" ", "%20")));
            responseBody = EntityUtils.toString(response.getEntity()).replace("\n", "");
            //event.getChannel().sendMessage(String.format("```\n%s\n```", responseBody)).queue();
            jsonResponse = new JSONObject(responseBody);
            if (response.getStatusLine().getStatusCode() != 200) {
                Kayla.log.log(Level.SEVERE, responseBody);
                throw new IOException("Request to Youtube was unsuccessful: " + response.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
            embed.setColor(new Color(0xff0000));
            embed.setTitle("An Error Occurred");
            embed.setDescription(String.format("```\n%s\n```", e.getMessage()));
            event.getChannel().sendMessage(embed.build()).queue();
            return;
        }
        JSONObject result = jsonResponse.getJSONArray("items").getJSONObject(0);
        embed.setDescription("https://youtu.be/" + result.getJSONObject("id").getString("videoId"));
        embed.addField(result.getJSONObject("snippet").getString("title"), result.getJSONObject("snippet").getString("description"), false);
        embed.setImage(result.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("high").getString("url"));
        event.getChannel().sendMessage(embed.build()).queue();
    }
}
