package com.yamada.notkayla.commands.general;

import com.yamada.notkayla.commands.Command;
import com.yamada.notkayla.utils.MiscTools;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Command(name="user",group="general",description="View information on a server member")
public class UserCommand {
    public static void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        //TODO: Test this code
        User user;
        Member member = null;
        List<Member> mentionedMembers = event.getMessage().getMentionedMembers();
        if (mentionedMembers.size() > 1) {
            event.getChannel().sendMessage("You specified more than one user. We will only get information about the first mentioned user.").queue();
            user = mentionedMembers.get(0).getUser();
            member = mentionedMembers.get(0);
        } else if (mentionedMembers.size() == 0){
            List<User> mentionedUsers = event.getMessage().getMentionedUsers();
            if (mentionedUsers.size() > 1) event.getChannel().sendMessage("You specified more than one user. We will only get information about the first mentioned user.").queue();
            if (mentionedUsers.size()==0){
                user = event.getAuthor();
                member = event.getMember();

            }else user = mentionedUsers.get(0);
        } else {
            user = mentionedMembers.get(0).getUser();
            member = mentionedMembers.get(0);
        }
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Information collected!");
        embed.setDescription("Here's what we know about this user!");
        embed.setColor(new Color(0xe91e63));
        embed.setFooter(String.format("Hello, %s", event.getAuthor().getName()), event.getAuthor().getAvatarUrl());
        embed.setThumbnail(user.getAvatarUrl());
        embed.addField("User ID: ", user.getId(), false);
        if (member != null)embed.addField("Roles", MiscTools.roleListToString(member.getRoles()), false);
        embed.addField("Joined on: ", user.getCreationTime().format(DateTimeFormatter.RFC_1123_DATE_TIME), false);
        event.getChannel().sendMessage(embed.build()).queue();
    }

}