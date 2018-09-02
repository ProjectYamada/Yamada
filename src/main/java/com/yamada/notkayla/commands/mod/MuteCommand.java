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
        if (!event.getMember().hasPermission(Permission.KICK_MEMBERS)) GettingFiles.didYouJustTryThat(event.getChannel(),event.getAuthor().getIdLong());
        try {
            event.getGuild().getRoles().add(role.createCopy().setPermissions().setColor(0xff0000).complete());
            //event.getGuild().getController().addRolesToMember(event.getMessage().getMentionedMembers().get(0), event.getGuild().getRoles().get(event.getGuild().getRoles().size() - 1)).queue();
        } catch (Exception e) {
            event.getChannel().sendMessage("I couldn't mute the specified user.").queue();
            return;
        }

    }
}
