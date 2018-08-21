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
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        EmbedBuilder embed = new EmbedBuilder();
        String term = "";
        int index = 1;
        embed.setTitle("Test Definition");
        CloseableHttpClient client = HttpClientBuilder.create().build();
        try {
            term = args[1];
            try {
                index = Integer.parseInt(args[2]);
            } catch (Exception e) {
                event.getChannel().sendMessage("If your term is multiple words, replace each space with an underscore.").queue();
                return;
            }
        } catch (Exception e) {
            event.getChannel().sendMessage("Please specify a term for the Urban Dictionary.").queue();
            return;
        }

        try {
            CloseableHttpResponse response = client.execute(
                    new HttpGet(String.format("http://api.urbandictionary.com/v0/define?term=%s", term.replace("_", " "))));
            String responseBody = EntityUtils.toString(response.getEntity()).replace("\n", "");
            JSONObject json = new JSONObject(responseBody);
            JSONArray arr = json.getJSONArray("list");
            String definition = arr.getJSONObject(index).getString("definition");
            embed.addField("Definition: ", definition, false);
            if (arr.getJSONObject(index).getString("example").length() >= 1024) {
                String example = arr.getJSONObject(index).getString("example").substring(0, 1020);
                embed.addField("Example: ", example + "...", false);
            }
            else embed.addField("Example: ", arr.getJSONObject(index).getString("example"), false);
            embed.addField(":thumbsup: ", String.valueOf(arr.getJSONObject(index).getInt("thumbs_up")), true);
            embed.addField(":thumbsdown: ", String.valueOf(arr.getJSONObject(index).getInt("thumbs_down")), true);
            embed.setFooter(String.format("Requested by %s", event.getAuthor().getName()), event.getAuthor().getAvatarUrl());
            event.getChannel().sendMessage(embed.build()).queue();
        } catch (IOException e) {
            event.getChannel().sendMessage("That term could not be found on Urban Dictionary...").queue();
        }
    }
}
