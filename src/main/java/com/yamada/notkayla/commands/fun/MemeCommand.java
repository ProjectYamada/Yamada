package com.yamada.notkayla.commands.fun;

import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import org.json.*;
import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Command(name="meme",group="fun",description="Fetches a random meme")
public class MemeCommand {
    private static CloseableHttpResponse response;
    private static String responseBody;
    private static JSONObject jsonResponse;
    public Map<String,Integer> retries = new HashMap<>();
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) throws IOException {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0xe91e63));
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            HttpGet getRequest = new HttpGet("https://api.reddit.com/r/memes/random");
            getRequest.setHeader("User-Agent", "web:com.yamada.notkayla:v0.1 (by /u/akireee)");
            response = client.execute(getRequest);
            responseBody = EntityUtils.toString(response.getEntity()).replace("\n", "");
            jsonResponse = new JSONArray(responseBody).getJSONObject(0).getJSONObject("data").getJSONArray("children").getJSONObject(0).getJSONObject("data");
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new IOException("Request to reddit api was unsuccessful");
            }
        } catch (IOException e) {
            if (retries.get(event.getMessageId()) == 5) {
                e.printStackTrace();
                embed.setColor(new Color(0xff0000));
                embed.setTitle("An Error Occurred");
                embed.setDescription(String.format("```\n%s\n```", e.getMessage()));
                event.getChannel().sendMessage(embed.build()).queue();
                return;
            }
        }
        embed.setAuthor(jsonResponse.getString("title"), "https://reddit.com" + jsonResponse.getString("permalink"));
        embed.setImage(jsonResponse.getString("url"));
        embed.setFooter("\uD83D\uDC4D " + Integer.toString(jsonResponse.getInt("score")), null);
        event.getChannel().sendMessage(embed.build()).queue();

    }
}