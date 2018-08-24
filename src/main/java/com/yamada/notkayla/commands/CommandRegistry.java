package com.yamada.notkayla.commands;

import com.yamada.notkayla.Kayla;
import com.yamada.notkayla.module.SanaeClassLoader;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.reflections.Reflections;

import javax.script.ScriptEngineManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;

public class CommandRegistry {
    public ScriptEngineManager sf = new ScriptEngineManager();
    private HashMap<String,RegCommand> commands = new HashMap<>();
    private SanaeClassLoader classLoader = new SanaeClassLoader();

    public void register(){
        Reflections r = new Reflections();
        Set<Class<?>> annotCommands = r.getTypesAnnotatedWith(Command.class);
        for (Class<?> cmd : annotCommands) {
            commands.put(cmd.getAnnotation(Command.class).name(),new RegCommand(cmd.getPackage()+"."+cmd.getSimpleName()));
        }
        Kayla.log.log(Level.INFO,commands.toString());
        Kayla.log.log(Level.INFO,annotCommands.toString());
/*        if(has(commandName)) throw new KeyAlreadyExistsException("What???");
        Kayla.log.log(Level.INFO, String.format("%s is now registered", commandName));*/
    }

    public boolean has(String commandName) {
        return commands.containsKey(commandName);
    }

    public RegCommand get(String commandName){
        return commands.get(commandName);
    }

    public void run(String commandName, JDA bot, GuildMessageReceivedEvent event, String[] args) throws InvocationTargetException, IllegalAccessException {
        RegCommand regCommand = get(commandName);
        regCommand.run.invoke(regCommand.cmd,bot,event,args);
    }
    public void reload(String commandName) {

    }

    public class RegCommand{
        public String packageName;
        public Object cmd;
        public Method run;
        public boolean loaded;
        public RegCommand(String aPackage){
            packageName=aPackage;
            cmd = classLoader.loadClass(packageName);
        }
        public void unload(){

        }
    }
}