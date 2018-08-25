package com.yamada.notkayla.commands;

import com.yamada.notkayla.Kayla;
import com.yamada.notkayla.module.SanaeClassLoader;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.reflections.Reflections;

import javax.script.ScriptEngineManager;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;

public class CommandRegistry {
    public ScriptEngineManager sf = new ScriptEngineManager();
    private HashMap<String,RegCommand> commands = new HashMap<>();
    private SanaeClassLoader classLoader = new SanaeClassLoader();
    Class[] runClasses = new Class[3];

    public CommandRegistry(){
        runClasses[0] = JDA.class;
        runClasses[1] = GuildMessageReceivedEvent.class;
        runClasses[2] = Array.class;
    }

    public void register() throws NoSuchMethodException {
        Reflections r = new Reflections("com.yamada.notkayla.commands");
        Set<Class<?>> annotCommands = r.getTypesAnnotatedWith(com.yamada.notkayla.commands.Command.class);
        Kayla.log.log(Level.INFO,annotCommands.toString());
        for (Class<?> cmd : annotCommands) {
            Kayla.log.log(Level.INFO,cmd.getCanonicalName());
            Kayla.log.log(Level.INFO,cmd.getAnnotation(Command.class).name());
            commands.put(cmd.getAnnotation(Command.class).name(),new RegCommand(cmd.getPackage().getName()+"."+cmd.getSimpleName()));
        }
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
        String packageName;
        Class<?> cmd;
        public Method run;
        boolean loaded;
        RegCommand(String aPackage) throws NoSuchMethodException {
            packageName=aPackage;
            cmd = classLoader.loadClass(packageName);
            run = (cmd).getMethod("run");
        }
        public void unload(){
            if (!loaded || cmd == null) {
            }else{

            }
        }
    }
}