package com.yamada.notkayla.commands.fun;

import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class UrbanCommand implements Command {
    @Override
    public void run(JDA bot, GuildMessageReceivedEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        //TODO: Allow this command to handle arguments.
        //String arg = event.getArgs();
        //System.out.println(arg);
        embed.setTitle("Test Definition");
        CloseableHttpClient client = HttpClientBuilder.create().build();
        try {
            CloseableHttpResponse response = client.execute(
                    new HttpGet(String.format("http://api.urbandictionary.com/v0/define?term=%s", "test")));
            String responseBody = EntityUtils.toString(response.getEntity()).replace("\n", "");
            JSONObject json = new JSONObject(responseBody);
            JSONArray arr = json.getJSONArray("list");
            String definition = arr.getJSONObject(0).getString("definition");
            System.out.println(definition);
            System.out.println(responseBody);
            embed.addField("Definition: ", definition, false);
            embed.addField("Example: ", arr.getJSONObject(0).getString("example"), false);
            embed.addField(":thumbsup: ", String.valueOf(arr.getJSONObject(0).getInt("thumbs_up")), true);
            embed.addField(":thumbsdown: ", String.valueOf(arr.getJSONObject(0).getInt("thumbs_down")), true);
            embed.setFooter(String.format("Requested by %s", event.getAuthor().getName()), event.getAuthor().getAvatarUrl());
            event.getChannel().sendMessage(embed.build()).queue();
        } catch (IOException e) {
            event.getChannel().sendMessage("That term could not be found on Urban Dictionary...").queue();
        }
    }
}
