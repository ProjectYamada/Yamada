package com.yamada.notkayla;

import com.yamada.notkayla.commands.CommandRegistry;
import com.yamada.notkayla.module.Adapter;
import com.yamada.notkayla.module.DatabaseAdapter;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.TextChannel;
import org.reflections.Reflections;
import org.yaml.snakeyaml.Yaml;

import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Kayla {
    public static Logger log = Logger.getLogger("Kayla");
    public static JDA bot;
    public static CommandRegistry registry = new CommandRegistry();
    public static Reflections refl = new Reflections();
    private static HashMap<String, Adapter> modules;
    public static Config config = new Config();
    public static Checks checks = new Checks();
    public static void main(String[] args){
        config.init();
        log.log(Level.INFO,"Logging in");
        try {
            bot = new JDABuilder(AccountType.BOT).setToken((String) config.configuration.get("token")).addEventListener(new Events()).build();
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

    public static class Config {
        private static Yaml yaml = new Yaml();
        public Map configuration;

        void init() {
            try{
                Path curdir = Paths.get(System.getProperty("user.dir"));
                Path config = Paths.get(curdir.toString(),"config.yml");
                configuration = (Map) yaml.load(new FileInputStream(config.toFile()));
                checks.owners = (ArrayList) configuration.get("owners");
            } catch (FileNotFoundException e) {
                log.log(Level.SEVERE, "FileNotFoundError: file 'config.yml' does not exist");
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    public static class Checks {
        ArrayList owners = new ArrayList();
        public boolean isNotAdmin(String id){
            return !owners.contains(id);
        }

        public boolean isNotNSFW(TextChannel channel) {
            return !channel.isNSFW();
        }
    }
}
