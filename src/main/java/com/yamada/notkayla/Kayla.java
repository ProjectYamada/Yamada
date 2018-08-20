package com.yamada.notkayla;

import com.yamada.notkayla.commands.CommandRegistry;
import com.yamada.notkayla.commands.general.TestCommand;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Kayla {
    static Logger log = Logger.getLogger("Kayla");
    static JDA bot;
    public static CommandRegistry registry = new CommandRegistry();
    public static void main(String[] args){
        File configFile = new File("./config.yml");
        Config.init(configFile);
        log.log(Level.INFO,"Logging in");
        try {
            bot = new JDABuilder(AccountType.BOT).setToken((String) Config.configuration.get("token")).addEventListener(new Events()).build();
            bot.awaitReady().getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB,Game.watching("for Kaniel's whip and naenae 100k special"));
            registerCommands();
        } catch (LoginException e){
            log.log(Level.SEVERE,"kayla is cool ™");
            e.printStackTrace();
            System.exit(1);
        } catch (InterruptedException e) {
            log.log(Level.SEVERE,"i can't get ready for school mom");
            e.printStackTrace();
        }

    }

    private static void registerCommands(){
        registry.register("test",new TestCommand());
    }
}
