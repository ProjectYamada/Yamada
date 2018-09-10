package com.yamada.notkayla.commands.mod;

import com.yamada.notkayla.commands.Command;
import com.yamada.notkayla.utils.GettingFiles;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.io.File;

@Command(name = "ban", group = "mod",description = "Bans the specified user from your server")
public class BanCommand {
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        StringBuilder reason = new StringBuilder();
        if (!event.getMember().hasPermission(Permission.BAN_MEMBERS))
        {
            GettingFiles.didYouJustTryThat(event.getChannel(),event.getAuthor().getIdLong());
            return;
        }
        try {
            if (args.length == 2) {
                event.getGuild().getController().ban(event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0)), 0).queue();
            }
            else {
                for (int i = 2; i < args.length; i++) {
                    reason.append(args[i]).append(" ");
                }
                bot.getPrivateChannelById(event.getMember().getUser().getId()).sendMessage("You have been banned for this reason: " + reason).submit();
                event.getGuild().getController().ban(event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0)), 0, String.valueOf(reason)).queue();
            }
        } catch (Exception e) {
            event.getChannel().sendMessage("I couldn't ban the specified user.").queue();
            return;
        }
        event.getChannel().sendMessage("I successfully banned " + event.getMessage().getMentionedUsers().get(0).getName() + ".").queue();

    }

}
