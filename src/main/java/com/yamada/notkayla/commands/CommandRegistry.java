package com.yamada.notkayla.commands;

import com.yamada.notkayla.Kayla;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.script.ScriptEngineManager;
import java.util.HashMap;
import java.util.logging.Level;

public class CommandRegistry {
    public ScriptEngineManager sf = new ScriptEngineManager();
    private HashMap<String,Command> commands = new HashMap<>();

    public void register(String commandName,Command command){
        if(has(commandName)) throw new KeyAlreadyExistsException("What???");
        commands.put(commandName,command);
        Kayla.log.log(Level.INFO, String.format("%s is now registered", commandName));
    }

    public boolean has(String commandName) {
        return commands.containsKey(commandName);
    }

    public Command get(String commandName){
        return commands.get(commandName);
    }
}