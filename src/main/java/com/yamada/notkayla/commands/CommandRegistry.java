package com.yamada.notkayla.commands;

import com.yamada.notkayla.module.SanaeClassLoader;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import javax.script.ScriptEngineManager;
import java.util.HashMap;
import java.util.Set;

public class CommandRegistry {
    public ScriptEngineManager sf = new ScriptEngineManager();
    private HashMap<String,RegCommand> commands = new HashMap<>();
    private SanaeClassLoader classLoader = new SanaeClassLoader();

    public void register(){
        Reflections r = new Reflections(new ConfigurationBuilder().addClassLoader(classLoader).addClassLoader(ClassLoader.getSystemClassLoader()));
        Set<Class<?>> annotCommands = r.getTypesAnnotatedWith(Command.class);
        for (Class<?> cmd : annotCommands) {
            commands.put("",new RegCommand(cmd.getPackage()+"."+cmd.getSimpleName(),classLoader.loadClass(cmd.getPackage()+"."+cmd.getSimpleName())));
        }
/*        if(has(commandName)) throw new KeyAlreadyExistsException("What???");
        Kayla.log.log(Level.INFO, String.format("%s is now registered", commandName));*/
    }

    public boolean has(String commandName) {
        return commands.containsKey(commandName);
    }

    public RegCommand get(String commandName){
        return commands.get(commandName);
    }

    public void run(String commandName, JDA bot, GuildMessageReceivedEvent event, String[] args){

    }
    public void reload(String commandName) {
    }

    public class RegCommand{
        String packageName;
        Object cmd;
        public RegCommand(String aPackage, Class<?> aClass){
            packageName=aPackage;
        }
    }
}