package com.yamada.notkayla;

import com.yamada.notkayla.commands.CommandRegistry;
import com.yamada.notkayla.module.Adapter;
import com.yamada.notkayla.module.DatabaseAdapter;
import net.dv8tion.jda.bot.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.bot.sharding.ShardManager;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import org.reflections.Reflections;
import org.yaml.snakeyaml.Yaml;

import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Kayla {
    public static Logger log = Logger.getLogger("Kayla");
    private static ShardManager shards;
    public static CommandRegistry registry = new CommandRegistry();
    public static Reflections refl = new Reflections();
    public static Map configuration;
    private static HashMap<String, Adapter> modules;
    private static Yaml yaml = new Yaml();

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
            shards.setGame(Game.playing("with "+shards.getGuilds().size() + " guilds - !yhelp"));
            shards.setStatus(OnlineStatus.DO_NOT_DISTURB);
            registerModules();
        } catch (LoginException e){
            log.log(Level.SEVERE,"kayla is cool ™");
            e.printStackTrace();
            System.exit(1);
        } catch (IllegalAccessException | InstantiationException e) {
            log.log(Level.SEVERE,"touhou 9 is a not bad game");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void registerModules() throws IllegalAccessException, InstantiationException {
        //todo: reflect modules like how i practiced
        registry.register();//the command system is a form of module
        try {
            modules.put("database", new DatabaseAdapter());
        }catch (NullPointerException ignored){}
    }

    public static String reloadModule(String name) {
        try {
            Adapter adapter = modules.get(name);
            if (adapter==null)return "That's not a module";
            adapter.reload();
        }catch (Exception e){
            //it catches general exceptions
        }
        return "not ready yet kiddo";
    }

    public static String unloadModule(String s) {
        //registry.unload(s);
        return "not ready yet kiddo";
    }
}
