package com.yamada.notkayla.commands.mod;

import com.yamada.notkayla.commands.Command;
import com.yamada.notkayla.utils.GettingFiles;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.IPermissionHolder;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

@Command(name="mute",group="mod",description="Mutes a user.")
public class MuteCommand {
    IPermissionHolder holder;
    Role role;
    List<Role> roles;
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        if (!event.getMember().hasPermission(Permission.MANAGE_ROLES))
        {
            GettingFiles.didYouJustTryThat(event.getChannel(),event.getAuthor().getIdLong());
            return;
        }
        try {

        } catch (Exception e) {
            event.getChannel().sendMessage("I couldn't mute the specified user.").queue();
            return;
        }

    }
}
