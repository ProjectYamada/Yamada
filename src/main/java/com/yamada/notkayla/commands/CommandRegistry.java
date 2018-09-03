package com.yamada.notkayla.commands;

import com.yamada.notkayla.Kayla;
import com.yamada.notkayla.module.SanaeClassLoader;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;

import javax.script.ScriptEngineManager;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public class CommandRegistry {
    public ScriptEngineManager sf = new ScriptEngineManager();
    public HashMap<String,RegCommand> commands = new HashMap<>();
    private SanaeClassLoader classLoader = new SanaeClassLoader();

    public void register() throws InstantiationException, IllegalAccessException {
        Reflections r = new Reflections("com.yamada.notkayla.commands");
        Set<Class<?>> annotCommands = r.getTypesAnnotatedWith(com.yamada.notkayla.commands.Command.class);
        Class<?> help = null;
        for (Class<?> cmd : annotCommands) {
            if (cmd.getAnnotation(Command.class).name().equals("help")) {
                help = cmd;
                continue;
            }
            Kayla.log.log(Level.INFO,cmd.getAnnotation(Command.class).name());
            commands.put(cmd.getAnnotation(Command.class).name(),new RegCommand(cmd.getPackage().getName()+"."+cmd.getSimpleName()));
        }
        if (help != null) {
            Kayla.log.log(Level.INFO, help.getAnnotation(Command.class).name());
            commands.put(help.getAnnotation(Command.class).name(),new RegCommand(help.getPackage().getName()+"."+help.getSimpleName()));
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
        if (regCommand.loaded)regCommand.run.invoke(regCommand.instance,bot,event,args);
    }
    public void reload(String commandName) throws InstantiationException, IllegalAccessException {
        if (has(commandName)) {
            RegCommand reg = get(commandName);
            reg.unload();
            reg.load();
        }
    }

    public void load(String commandName) throws InstantiationException, IllegalAccessException {
        if (!has(commandName)) {
            RegCommand reg = get(commandName);
            reg.load();
        }

    }
    public void unload(String commandName) {
        if (has(commandName)) {
            RegCommand reg = get(commandName);
            reg.unload();
        }
    }

    @SuppressWarnings("unchecked")
    public class RegCommand{
        String packageName;
        public Class<?> cmd;
        Object instance;
        public Method run;
        boolean loaded;
        RegCommand(String aPackage) throws IllegalAccessException, InstantiationException {
            packageName=aPackage;
            cmd = classLoader.loadClass(packageName);
            instance = cmd.newInstance();
            run = new ArrayList<>(ReflectionUtils.getMethods(cmd, ReflectionUtils.withName("run"))).get(0);
            loaded = true;
        }
        void unload(){
            if (instance != null || cmd != null) {
                instance = null;
                cmd = null;
            }
            loaded = false;
        }
        void load() throws IllegalAccessException, InstantiationException {
            if (instance == null) {
                cmd = classLoader.loadClass(packageName);
                instance = cmd.newInstance();
            }
            loaded = true;
        }
    }
}