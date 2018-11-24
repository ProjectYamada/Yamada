package com.yamada.notkayla.commands;

import com.yamada.notkayla.Yamada;
import com.yamada.notkayla.module.SanaeClassLoader;
import lombok.Getter;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;

import javax.script.ScriptEngineManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

public class CommandRegistry {
    public ScriptEngineManager sf = new ScriptEngineManager();
    @Getter public HashMap<String,RegCommand> commands = new HashMap<>();
    private SanaeClassLoader classLoader = new SanaeClassLoader();

    public void register() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        Reflections r = new Reflections("com.yamada.notkayla.commands");
        Set<Class<?>> annotCommands = r.getTypesAnnotatedWith(com.yamada.notkayla.commands.Command.class);
        Class<?> help = null;
        for (Class<?> cmd : annotCommands) {
            if (cmd.getAnnotation(Command.class).name().equals("help")) {
                help = cmd;
                continue;
            }
            Yamada.log.log(Level.INFO,cmd.getAnnotation(Command.class).name());
            commands.put(cmd.getAnnotation(Command.class).name(),new RegCommand(cmd.getPackage().getName()+"."+cmd.getSimpleName()));
        }
        if (help != null) {
            Yamada.log.log(Level.INFO, help.getAnnotation(Command.class).name());
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
        regCommand.run.invoke(regCommand.instance,bot,event,args);
    }
    public void reload(String commandName) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        if (has(commandName)) {
            RegCommand reg = get(commandName);
            Yamada.log.log(Level.INFO,reg.cmd.getAnnotation(Command.class).name());
            commands.remove(commandName);
            commands.put(reg.cmd.getAnnotation(Command.class).name(),new RegCommand(reg.cmd.getPackage().getName()+"."+reg.cmd.getSimpleName()));
        }
    }

    public void load(String commandName) {
        //to redo
    }

    public void unload(String commandName) {
        //to redo
    }

    @SuppressWarnings("unchecked")
    public class RegCommand{
        String packageName;
        public Class<?> cmd;
        Object instance;
        public Method run;
        RegCommand(String aPackage) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
            packageName=aPackage;
            cmd = classLoader.loadClass(packageName);
            boolean i = aPackage.contains("HelpCommand");
            instance = i ? cmd.getConstructor(Map.class).newInstance(commands) : cmd.getConstructor().newInstance();
            run = new ArrayList<>(ReflectionUtils.getMethods(cmd, ReflectionUtils.withName("run"))).get(0);
        }
    }
}