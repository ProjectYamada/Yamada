package com.yamada.notkayla.commands.anime;

import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

@Command(name="kiss",group="anime",description = "Kiss a player becaus why not?")
public class KissCommand {
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        User member;
        try {
            member = event.getMessage().getMentionedUsers().get(0);
        } catch (Exception e) {
            event.getChannel().sendMessage("You need to mention a user. :stuck_out_tongue:").queue();
            return;
        }
        CloseableHttpClient client = HttpClients.createDefault();
        EmbedBuilder embed = new EmbedBuilder();
        try {
            CloseableHttpResponse response = client.execute(new HttpGet("https://nekos.life/api/v2/img/kiss"));
            String responseBody = EntityUtils.toString(response.getEntity()).replace("\n", "");
            JSONObject json = new JSONObject(responseBody);
            embed.setTitle("Kiss!", json.getString("url"));
            embed.setDescription(String.format("%s kissed %s. Awww...", event.getAuthor().getName(), member.getName()));
            embed.setImage(json.getString("url"));
        } catch (Exception e) {
            // EMPTY WOO HOO
        }
        embed.setFooter(String.format("How intimate %s", event.getAuthor().getName()), event.getAuthor().getAvatarUrl());
        event.getChannel().sendMessage(embed.build()).queue();
    }
}
