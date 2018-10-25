package com.yamada.notkayla;

import com.yamada.notkayla.module.modules.audio.MusicModule;
import net.dv8tion.jda.bot.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.bot.sharding.ShardManager;
import org.reflections.Reflections;
import org.yaml.snakeyaml.Yaml;

import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Yamada {
    public static Logger log = Logger.getLogger("Yamada");
    private static ShardManager shardManager;
    public static Reflections refl = new Reflections();
    public static Map configuration;
    private static Yaml yaml = new Yaml();
    public static MusicModule music = new MusicModule();

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException, InterruptedException {
        Yamada.log.log(Level.INFO,"Pulling changes from Git before doing the stuffs");
        ProcessBuilder git = new ProcessBuilder("git","pull");
        git.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        git.redirectError(ProcessBuilder.Redirect.INHERIT);
        git.start().waitFor();
        Yamada.log.log(Level.INFO,"Setting up config");
        try{
            Path curdir = Paths.get(System.getProperty("user.dir"));
            Path config = Paths.get(curdir.toString(),"config.yml");
            configuration = (Map) yaml.load(new FileInputStream(config.toFile()));
        } catch (FileNotFoundException e) {
            Yamada.log.log(Level.SEVERE, "FileNotFoundError: file 'config.yml' does not exist");
            e.printStackTrace();
            System.exit(1);
        }
        log.log(Level.INFO,"Logging in");
        try {
            shardManager = new DefaultShardManagerBuilder().setToken((String) configuration.get("token")).addEventListeners(new Events()).build();
        } catch (LoginException e){
            log.log(Level.SEVERE,"kayla is cool ™");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
