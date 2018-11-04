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
@Command(name = "urban", group = "fun")
public class UrbanCommand {
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        if (!event.getChannel().isNSFW()) {
            event.getChannel().sendMessage("You need to be in a NSFW channel for this.").queue();
            return;
        }
        EmbedBuilder embed = new EmbedBuilder();
        StringBuilder term = new StringBuilder();
        int index = 0;
        boolean page_number = false;
        CloseableHttpClient client = HttpClientBuilder.create().build();
        try {
            try {
                index = Integer.parseInt(args[args.length - 1]);
                page_number = true;
            } catch (Exception e) {
                // Left empty for obvious reasons.
            }
            // !yurban test
            if (args.length == 2) {
                term = new StringBuilder(args[1]);
            }
            // !yurban united states
            else {
                if (!page_number) {
                    for (int i = 1; i < args.length; i++) {
                        term.append(args[i]).append(" ");
                    }
                }
                else {
                    for (int i = 1; i < args.length - 1; i++)
                        term.append(args[i]).append(" ");
                }
                term = new StringBuilder(term.substring(0, term.length() - 1));
            }
        } catch (Exception e) {
            event.getChannel().sendMessage("Please specify a term for the Urban Dictionary.").queue();
            return;
        }

        try {
            CloseableHttpResponse response = client.execute(
                    new HttpGet(String.format("http://api.urbandictionary.com/v0/define?term=%s", term.toString().replace(" ", "+"))));
            String responseBody = EntityUtils.toString(response.getEntity()).replace("\n", "");
            JSONObject json = new JSONObject(responseBody);
            JSONArray arr = json.getJSONArray("list");
            String definition = arr.getJSONObject(index).getString("definition");
            embed.setTitle(arr.getJSONObject(index).getString("word"));
            embed.setDescription(definition);
            if (arr.getJSONObject(index).getString("example").length() >= 1024) {
                String example = arr.getJSONObject(index).getString("example").substring(0, 1020);
                embed.addField("Example: ", example + "...", false);
            }
            else embed.addField("Example: ", arr.getJSONObject(index).getString("example"), false);
            embed.addField(":thumbsup: ", String.valueOf(arr.getJSONObject(index).getInt("thumbs_up")), true);
            embed.addField(":thumbsdown: ", String.valueOf(arr.getJSONObject(index).getInt("thumbs_down")), true);
            embed.setFooter(String.format("Requested by %s", event.getAuthor().getName()), event.getAuthor().getAvatarUrl());
            event.getChannel().sendMessage(embed.build()).queue();
        } catch (Exception e) {
            event.getChannel().sendMessage("That term could not be found on Urban Dictionary...").queue();
        }
    }
}
