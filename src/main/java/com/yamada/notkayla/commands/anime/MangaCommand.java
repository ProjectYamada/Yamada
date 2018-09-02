package com.yamada.notkayla.commands.anime;

import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

@Command(name="manga", group="anime")
public class MangaCommand {
    public MangaCommand(){}

    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {

        // API is http://api.jikan.moe/

        EmbedBuilder embed = new EmbedBuilder();
        StringBuilder term = new StringBuilder();
        CloseableHttpClient client = HttpClientBuilder.create().build();

        try {
            if (args.length < 2) {
                term = new StringBuilder(args[1]);
            } else {
                for (int i = 1; i < args.length; i++) {
                    term.append(args[i]).append(" ");
                }
            }
        } catch (Exception e) {
            event.getChannel().sendMessage("Please specify a manga for me to search for.").queue();
            return;
        }

        try {
            CloseableHttpResponse response = client.execute(
                    new HttpGet(String.format("https://api.jikan.moe/search/manga/%s/1", term.toString().replace(" ", "%20"))));
            String responseBody = EntityUtils.toString(response.getEntity()).replace("\n", "");
            JSONObject json = new JSONObject(responseBody);
            System.out.println(responseBody);
            JSONArray arr = json.getJSONArray("result");
            JSONObject obj = arr.getJSONObject(0);
            embed.setTitle(obj.getString("title"));
            embed.setDescription(obj.getString("description"));
            embed.addField("Type", obj.getString("type"), true);
            embed.addField("Score", obj.getInt("score"), true);
            embed.setThumbnail(obj.getString("image_url"));
            embed.addField("Volumes", Integer.toString(obj.getInt("volumes")), true);
            embed.addField("Members", Integer.toString(obj.getInt("members")), true);
            embed.setFooter(String.format("Requested by %s", event.getAuthor().getName()), event.getAuthor().getAvatarUrl());
            event.getChannel().sendMessage(embed.build()).queue();
        } catch (IOException e) {
            event.getChannel().sendMessage("No manga were found.").queue();
        }

    }

}
