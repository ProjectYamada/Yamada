package com.yamada.notkayla;

import com.yamada.notkayla.commands.CommandRegistry;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Kayla {
    static Logger log = Logger.getLogger("Kayla");
    private static JDA jda;
    static CommandRegistry registry = new CommandRegistry();
    public static void main(String[] args){
        File config = new File("./config.yml");
        log.log(Level.INFO,"Logging in");
        try {
            jda = new JDABuilder(AccountType.BOT).setToken("haha we need a config ").build();
        } catch (LoginException e){
            log.log(Level.SEVERE,"kayla is cool ™");
            e.printStackTrace();
        }
        jda.addEventListener(new Events());
    }
}
