package com.yamada.notkayla.commands.image;

import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import java.awt.Color;
import java.io.IOException;

import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class RandomDuckCommand implements Command  {
    private static CloseableHttpResponse response;
    private static String responseBody;
    private static JSONObject jsonResponse;

    @Override
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0xe91e63));
        CloseableHttpClient client = HttpClientBuilder.create().build();
        try {
            response = client.execute(new HttpGet("https://random-d.uk/api/v1/quack"));
            responseBody = EntityUtils.toString(response.getEntity()).replace("\n", "");
            //event.getChannel().sendMessage(String.format("```\n%s\n```", responseBody)).queue();
            jsonResponse = new JSONObject(responseBody);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new IOException("Request to random-d.uk was unsuccessful");
            }
        } catch (IOException e) {
            e.printStackTrace();
            embed.setColor(new Color(0xff0000));
            embed.setTitle("An Error Occurred");
            embed.setDescription(String.format("```\n%s\n```", e.getMessage()));
            event.getChannel().sendMessage(embed.build()).queue();
            return;
        }
        embed.setImage(jsonResponse.getString("url"));
        embed.setFooter(jsonResponse.getString("message"), null);
        event.getChannel().sendMessage(embed.build()).queue();
    }
}
