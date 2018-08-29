package com.yamada.notkayla;

import com.yamada.notkayla.commands.CommandRegistry;
import com.yamada.notkayla.module.Adapter;
import com.yamada.notkayla.module.DatabaseAdapter;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Kayla {
    public static Logger log = Logger.getLogger("Kayla");
    public static JDA bot;
    public static CommandRegistry registry = new CommandRegistry();
    public static Reflections refl = new Reflections();
    public static Map configuration;
    public static List<String> owners;
    private static HashMap<String, Adapter> modules;
    static Yaml yaml = new Yaml();

    public static void main(String[] args){
        Kayla.log.log(Level.INFO,"Setting up config");
        try{
            Path curdir = Paths.get(System.getProperty("user.dir"));
            Path config = Paths.get(curdir.toString(),"config.yml");
            configuration = (Map) yaml.load(new FileInputStream(config.toFile()));
            System.out.println(configuration.get("owners").getClass().getName());
            owners = (List<String>) configuration.get("owners");
        } catch (FileNotFoundException e) {
            Kayla.log.log(Level.SEVERE, "FileNotFoundError: file 'config.yml' does not exist");
            e.printStackTrace();
            System.exit(1);
        }
        log.log(Level.INFO,"Logging in");
        try {
            bot = new JDABuilder(AccountType.BOT).setToken((String) configuration.get("token")).addEventListener(new Events()).build();
            bot.awaitReady().getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB,Game.playing("with "+bot.getGuilds().size() + " guilds - !yhelp"));
            registerModules();
        } catch (LoginException e){
            log.log(Level.SEVERE,"kayla is cool ™");
            e.printStackTrace();
            System.exit(1);
        } catch (InterruptedException e) {
            log.log(Level.SEVERE,"mom don't interrupt me");
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
        registry.register();
        try {
            modules.put("database", new DatabaseAdapter());
        }catch (NullPointerException ignored){}
    }

    public static String reloadModule(String name){

        return "not ready yet kiddo";
    }

    public static String unloadModule(String s) {
        return "not ready yet kiddo";
    }

    public static boolean isNotAdmin(String id){
        log.log(Level.INFO,owners.toString());
        return !owners.contains(id);
    }
}
