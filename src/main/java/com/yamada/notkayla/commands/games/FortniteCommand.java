package com.yamada.notkayla.commands.games;

import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.EmbedBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Command(name="fortnite", group="games")
public class FortniteCommand {

    public static Map configuration;
    private static Yaml yaml = new Yaml();
    String platform;
    String user;
    CloseableHttpClient client = HttpClientBuilder.create().build();

    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {

        try {
            Path curdir = Paths.get(System.getProperty("user.dir"));
            Path config = Paths.get(curdir.toString(), "config.yml");
            configuration = (Map) yaml.load(new FileInputStream(config.toFile()));
        } catch (FileNotFoundException e) {
            event.getChannel().sendMessage("This isn't it, chief.").queue();
            return;
        }

        try {
            platform = args[1];
            user = args[2];
        } catch (Exception e) {
            event.getChannel().sendMessage("Please specify a platform and user.").queue();
            return;
        }

        try {

            HttpGet http = new HttpGet(String.format("https://api.fortnitetracker.com/v1/profile/%s/%s", platform, user));
            http.setHeader("TRN-Api-Key","7889c63e-60bb-4a3e-a189-ef59d95ec54c");

            CloseableHttpResponse response = client.execute(http);

            event.getChannel().sendMessage(Integer.toString(response.getStatusLine().getStatusCode())).queue();

        } catch (Exception e) {
            event.getChannel().sendMessage("sad gamer moment :cry:").queue();
            return;
        }


    }

}
