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

@Command(name = "coffee", group = "image")
public class RandomCoffeeCommand {
    private static CloseableHttpResponse response;
    private static String body;
    private static JSONObject jsonRes;
    
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        EmbedBuilder embed = new EmbedBuilder().setColor(new Color(0xe91e63));
        CloseableHttpClient client = HttpClientBuilder
                .create()
                .build();
        
        try {
            response = client.execute(new HttpGet("https://coffee.alexflipnote.xyz/coffee.json"));
            body = EntityUtils.toString(response.getEntity()).replace("\n", "");
            if (response.getStatusLine().getStatusCode() != 200) throw new IOException("Request -> coffee.alexflipnote.xyz -> Unsuccessful");
        } catch (IOException e) {
            e.printStackTrace();
            embed.setColor(new Color(0xff0000))
                    .setTitle("An error has occured")
                    .setDescription(String.format("```\n%s```", e.getMessage()));
            event.getChannel().sendMessage(embed.build()).queue();
        }
        
        embed.setImage(jsonRes.getString("file"))
                .setFooter("Powered by coffee.alexflipnote.xyz", event.getAuthor().getEffectiveAvatarUrl());
        event.getChannel().sendMessage(embed.build()).queue();
    }
}
