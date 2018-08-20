package com.yamada.notkayla.commands;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.HashMap;

public class CommandRegistry {
    private HashMap<String,Command> commands = new HashMap<>();

    public void register(String commandName,Command command){
        if(has(commandName)) throw new KeyAlreadyExistsException("What???");
        commands.put(commandName,command);
    }

    public boolean has(String commandName) {
        return commands.containsKey(commandName);
    }

    public Command get(String commandName){
        return commands.get(commandName);
    }
}