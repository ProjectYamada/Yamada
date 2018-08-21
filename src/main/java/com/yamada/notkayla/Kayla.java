package com.yamada.notkayla;

import com.yamada.notkayla.commands.CommandRegistry;
import com.yamada.notkayla.commands.fun.*;
import com.yamada.notkayla.commands.general.*;
import com.yamada.notkayla.commands.image.*;
import com.yamada.notkayla.commands.anime.*;
import com.yamada.notkayla.commands.owner.PullCommand;
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
    public static Logger log = Logger.getLogger("Kayla");
    public static JDA bot;
    public static CommandRegistry registry = new CommandRegistry();
    public static void main(String[] args){
        File configFile = new File("./config.yml");
        Config.init(configFile);
        log.log(Level.INFO,"Logging in");
        try {
            bot = new JDABuilder(AccountType.BOT).setToken((String) Config.configuration.get("token")).addEventListener(new Events()).build();
            bot.awaitReady().getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB,Game.playing("with "+bot.getGuilds().size() + " guilds - !yhelp"));
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

    private static void registerCommands() {
        registry.register("test",new TestCommand());
        registry.register("help",new HelpCommand());
        registry.register("info", new InfoCommand());
        registry.register("ping", new PingCommand());
        registry.register("pull", new PullCommand());
        registry.register("user", new UserCommand());
        registry.register("dog", new RandomDogCommand());
        registry.register("cat", new RandomCatCommand());
        registry.register("duck", new RandomDuckCommand());
        registry.register("urban", new UrbanCommand());
        registry.register("danbooru", new DanbooruCommand());
        registry.register("meme", new MemeCommand());
        registry.register("yt", new YoutubeCommand());
    }
}
