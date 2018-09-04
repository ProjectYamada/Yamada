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

@Command(name = "slap", description = "Slaps a user.", group = "anime")
public class SlapCommand {
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
            CloseableHttpResponse response = client.execute(new HttpGet("https://nekos.life/api/v2/img/slap"));
            String responseBody = EntityUtils.toString(response.getEntity()).replace("\n", "");
            JSONObject json = new JSONObject(responseBody);
            embed.setTitle("Slap!", json.getString("url"));
            embed.setDescription(String.format("%s slapped %s. Oof...", event.getAuthor().getName(), member.getName()));
        } catch (Exception e) {
            // EMPTY WOO HOO
        }
        embed.setFooter(String.format("You're mean, %s", event.getAuthor().getName()), event.getAuthor().getAvatarUrl());
        event.getChannel().sendMessage(embed.build()).queue();
    }
}
