package com.yamada.notkayla.commands.mod;

import com.yamada.notkayla.commands.Command;
import com.yamada.notkayla.utils.GettingFiles;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

@Command(name = "euthanize", description = "An alias to ban that doesn't send the banned user the provided reason.", group = "mod")
public class EuthanizeCommand {
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args){
        StringBuilder reason = new StringBuilder();
        if (!event.getMember().hasPermission(Permission.BAN_MEMBERS)) GettingFiles.didYouJustTryThat(event.getChannel(),event.getAuthor().getIdLong());
        try {
            if (args.length == 2) {
                event.getGuild().getController().ban(event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0)), 0).queue();
            }
            else {
                for (int i = 2; i < args.length; i++) {
                    reason.append(args[i]).append(" ");
                }
                event.getGuild().getController().ban(event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0)), 0, String.valueOf(reason)).queue();
            }
        } catch (Exception e) {
            event.getChannel().sendMessage("I couldn't euthanized the specified user.").queue();
            return;
        }
        event.getChannel().sendMessage("I successfully euthanized " + event.getMessage().getMentionedUsers().get(0).getName() + ".").queue();
    }
}