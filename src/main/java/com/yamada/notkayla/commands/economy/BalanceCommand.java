package com.yamada.notkayla.commands.economy;

import com.yamada.notkayla.Kayla;
import com.yamada.notkayla.commands.Command;
import com.yamada.notkayla.module.modules.database.DatabaseAdapter;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

@Command(name="bal",description = "Please add details Allen",group="economy")
public class BalanceCommand {
    private DatabaseAdapter a;
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args){
        event.getChannel().sendMessage("this isn't implemented yet sorry").queue();
        if (a==null)a=(DatabaseAdapter) Kayla.getAdapter("database");
    }
}
