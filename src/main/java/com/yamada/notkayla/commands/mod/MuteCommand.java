package com.yamada.notkayla.commands.mod;

import com.yamada.notkayla.commands.Command;
import com.yamada.notkayla.utils.GettingFiles;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.IPermissionHolder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

@Command(name="mute",group="mod",description="Mutes a user.")
public class MuteCommand {
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        if (!event.getMember().hasPermission(Permission.KICK_MEMBERS)) GettingFiles.didYouJustTryThat(event.getChannel(),event.getAuthor().getIdLong());
        try {
            if (args.length == 2) {
                //bot.getCategoryById(event.getMessage().getCategory().getId()).getManager().putPermissionOverride();
                //event.getMessage().getMentionedMembers().get(0);

            }
        } catch (Exception e) {
            event.getChannel().sendMessage("I couldn't mute the specified user.").queue();
            return;
        }

    }
}
