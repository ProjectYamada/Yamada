package com.yamada.notkayla.commands.mod;

import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class BanCommand implements Command {

    @Override
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {

        try {
            event.getGuild().getController().ban(event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0))).queue();
        }catch (Exception e) {
            event.getChannel().sendMessage("I couldn't ban the specified user.").queue();
            return;
        }
        event.getChannel().sendMessage("I successfully banned " + event.getMessage().getMentionedUsers().get(0).getName() + ".").queue();

    }

}
