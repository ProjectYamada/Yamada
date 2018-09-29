package com.yamada.notkayla.commands.general;

import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

@Command(name="sel",group = "general")
public class SelectCommand {
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args){
        //TODO: Handle selection from SelectionManager
    }
}
