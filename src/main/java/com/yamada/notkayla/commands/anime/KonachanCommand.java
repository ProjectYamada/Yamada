package com.yamada.notkayla.commands.anime;

import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

@Command(name="konachan",description = "Gets an image from Konachan", group = "anime")
public class KonachanCommand {
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        String params = "?tags=-status%3Adeleted+-loli+-shota&limit=100";
        if (!event.getChannel().isNSFW()) {
            event.getChannel().sendMessage("You need to be in a NSFW channel for this.").queue();
            return;
        }
        // Check for any tags, then run them.
        try {
            if (args[1].contains("loli") || args[1].contains("shota")) {
                event.getChannel().sendMessage("We can't display images with \"loli\" or \"shota\" tags.").queue();
                return;
            }
            params = String.format("?tags=-status:deleted+-loli+-shota+%s&limit=100", args[1]);
            try {
                if (args[2].contains("explicit"))
                    params = String.format("?tags=-status:deleted+-loli+-shota+rating:e+%s&limit=100", args[1]);
                else if (args[2].contains("questionable"))
                    params = String.format("?tags=-status:deleted+-loli+-shota+rating:q+%s&limit=100", args[1]);
                else if (args[2].contains("safe"))
                    params = String.format("?tags=-status:deleted+-loli+-shota+rating:s+%s&limit=100", args[1]);
                else event.getChannel().sendMessage("Please specify a valid rating. Ratings include safe, questionable, and explicit.").queue();
            } catch (Exception e) {
                // Intentionally left empty.
            }
        } catch (Exception e) {
            // Intentionally left empty.
        }
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(0xe91e63);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            CloseableHttpResponse response = httpClient.execute(
                    new HttpGet(String.format("https://konachan.com/post/index.json%s", params)));
            String responseBody = EntityUtils.toString(response.getEntity()).replace("\n", "");
            JSONObject json = new JSONObject(responseBody);
            embed.setTitle("Successfully got an image.", json.getString("file_url"));
            embed.setDescription("If you can't see this image, click the title.");
            embed.setImage(json.getString("large_file_url"));
        } catch (Exception e) {
            /*e.printStackTrace();
            embed.setColor(new Color(0xff0000));
            embed.setTitle("An Error Occurred");
            embed.setDescription(String.format("```\n%s\n```", e.getMessage()));
            event.getChannel().sendMessage(embed.build()).queue();*/
            event.getChannel().sendMessage("We could not find any images with that tag.").queue();
            return;
        }
        embed.setFooter(String.format("Requested by %s", event.getAuthor().getName()),
                event.getAuthor().getAvatarUrl());
        event.getChannel().sendMessage(embed.build()).queue();
    }
}
