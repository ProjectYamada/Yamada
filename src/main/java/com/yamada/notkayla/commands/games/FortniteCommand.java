package com.yamada.notkayla.commands.games;

import com.yamada.notkayla.Yamada;
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
    private CloseableHttpClient client = HttpClientBuilder.create().build();
    private String key = (String) Yamada.configuration.get("fortnite-api");

    private String[] platforms = new String[]{
            "pc", "psn", "xb1"
    };
    private static boolean contains(String[] arr, String item) {
        for (String n : arr) {
            if (item.equals(n)) {
                return true;
            }
        }
        return false;
    }
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        String platform;
        String user;
        try {
            platform = args[1].toLowerCase();
            if (!contains(platforms,platform)) {
                event.getChannel().sendMessage("Available platforms: `pc` or `psn` or `xb1`").queue();
            }
            user = args[2];
        } catch (Exception e) {
            event.getChannel().sendMessage("Please specify a platform and user.\nPlatforms: `pc` or `psn` or `xb1`\nUsage: "+
                    Yamada.configuration.get("prefix")+this.getClass().getAnnotation(Command.class).name()+ " <platform> <username>").queue();
            return;
        }

        try {

            HttpGet http = new HttpGet(String.format("https://api.fortnitetracker.com/v1/profile/%s/%s", platform, user));
            http.setHeader("TRN-Api-Key", key);

            CloseableHttpResponse response = client.execute(http);

            event.getChannel().sendMessage(Integer.toString(response.getStatusLine().getStatusCode())).queue();

        } catch (Exception e) {
            event.getChannel().sendMessage("sad gamer moment :cry:").queue();
        }
    }

}
