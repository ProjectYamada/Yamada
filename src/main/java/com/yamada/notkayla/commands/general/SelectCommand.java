package com.yamada.notkayla.commands.general;

import com.yamada.notkayla.commands.Command;
import com.yamada.notkayla.utils.SelectionManager;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

@Command(name="sel",group = "general")
public class SelectCommand {
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        //TODO: Handle selection from SelectionManager
        if (!SelectionManager.hasSelectionAvailable(event.getAuthor().getIdLong())) {
            event.getChannel().sendMessage("You don't have anything to select!").queue();
            return;
        }
        if (args[1] == null) {
            event.getChannel().sendMessage("Select").queue();
            return;
        }
        int sel;
        try {
            sel = Integer.parseInt(args[1]);
        } catch (Exception e) {
            event.getChannel().sendMessage("Selection is not a proper number, choosing default selection.").queue();
            sel = 0;
        }
        SelectionManager.select(event.getAuthor().getIdLong(), sel);
    }
}
