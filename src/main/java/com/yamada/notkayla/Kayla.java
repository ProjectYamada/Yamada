package com.yamada.notkayla;

import com.yamada.notkayla.commands.CommandRegistry;
import com.yamada.notkayla.module.modules.audio.MusicModule;
import net.dv8tion.jda.bot.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.bot.sharding.ShardManager;
import org.reflections.Reflections;
import org.yaml.snakeyaml.Yaml;

import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Kayla {
    public static Logger log = Logger.getLogger("Kayla");
    private static ShardManager shards;
    public static Reflections refl = new Reflections();
    public static Map configuration;
    private static Yaml yaml = new Yaml();
    public static MusicModule music = new MusicModule();

    @SuppressWarnings("unchecked")
    public static void main(String[] args){
        Kayla.log.log(Level.INFO,"Setting up config");
        try{
            Path curdir = Paths.get(System.getProperty("user.dir"));
            Path config = Paths.get(curdir.toString(),"config.yml");
            configuration = (Map) yaml.load(new FileInputStream(config.toFile()));
        } catch (FileNotFoundException e) {
            Kayla.log.log(Level.SEVERE, "FileNotFoundError: file 'config.yml' does not exist");
            e.printStackTrace();
            System.exit(1);
        }
        log.log(Level.INFO,"Logging in");
        try {
            shards = new DefaultShardManagerBuilder().setToken((String) configuration.get("token")).addEventListeners(new Events()).build();
        } catch (LoginException e){
            log.log(Level.SEVERE,"kayla is cool ™");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
