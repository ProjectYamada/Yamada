package com.yamada.notkayla;

import com.yamada.notkayla.commands.CommandRegistry;
import com.yamada.notkayla.commands.fun.*;
import com.yamada.notkayla.commands.general.*;
import com.yamada.notkayla.commands.image.*;
import com.yamada.notkayla.commands.anime.*;
import com.yamada.notkayla.commands.mod.*;
import com.yamada.notkayla.commands.owner.EvalCommand;
import com.yamada.notkayla.commands.owner.PullCommand;
import com.yamada.notkayla.module.Adapter;
import com.yamada.notkayla.module.DatabaseAdapter;
import com.yamada.notkayla.module.Module;
import com.yamada.notkayla.module.SanaeClassLoader;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.reflections.Reflections;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Kayla {
    public static Logger log = Logger.getLogger("Kayla");
    public static JDA bot;
    public static CommandRegistry registry = new CommandRegistry();
    public static Reflections refl = new Reflections();
    private static HashMap<String, Adapter> modules;
    public static void main(String[] args){
        Config.init();
        log.log(Level.INFO,"Logging in");
        try {
            bot = new JDABuilder(AccountType.BOT).setToken((String) Config.configuration.get("token")).addEventListener(new Events()).build();
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
}
