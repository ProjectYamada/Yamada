package com.yamada.notkayla.commands.mod;

import com.yamada.notkayla.commands.Command;
import com.yamada.notkayla.utils.GettingFiles;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

@Command(name="purge",description = "Batch deletes messages, must be over one, can handle more than 100 messages at once. ", group="mod")
public class PurgeCommand {
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args){
        if (!(event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE) || event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_HISTORY))){
            event.getChannel().sendMessage("I need both the **Manage Messages** and **Read Message History** permissions").queue();
        }
        if (!event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            GettingFiles.didYouJustTryThat(event.getChannel(),event.getAuthor().getIdLong());
        }
        if (args[1] == null) {
            event.getChannel().sendMessage("How many messages do you want to delete?").queue();
            return;
        }
        int sel;
        try {
            sel = Integer.parseInt(args[1]);
        } catch (Exception e) {
            event.getChannel().sendMessage("Please supply a number above 2.").queue();
            return;
        }
        int rem = (sel%100)+1;//+1 so we can account for the user's command
        int loops = ((sel-rem)/100)+1;//+1 so we can include the purges that are under 100 messages
        for (int i=0;loops>i;i++){
            MessageHistory history = event.getChannel().getHistory();
            int retCount = loops -1 == i ? rem : 100;
            List<Message> messages = history.retrievePast(retCount).complete();
            event.getChannel().deleteMessages(messages).complete();
        }
    }
}
