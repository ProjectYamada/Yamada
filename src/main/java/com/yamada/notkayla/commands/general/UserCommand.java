package com.yamada.notkayla.commands.general;

import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;

@Command(name="user",group="general",description="View information on a server member")
public class UserCommand {
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        //TODO: Test this code
        User user;
        Member member;
        try {
            List<User> mentionedUsers = event.getMessage().getMentionedUsers();
            List<Member> mentionedMembers = event.getMessage().getMentionedMembers();
            if (mentionedUsers.size() > 1) event.getChannel().sendMessage("You specified more than one user. We will only get information about the first mentioned user.").queue();
            user = mentionedUsers.get(0);
            member = mentionedMembers.get(0);
        } catch (Exception e) {
            user = event.getAuthor();
            member = event.getMember();
        }
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Information collected!");
        embed.setDescription("Here's what we know about this user!");
        embed.setColor(new Color(0xe91e63));
        embed.setFooter(String.format("Hello, %s", event.getAuthor().getName()), event.getAuthor().getAvatarUrl());
        embed.setThumbnail(event.getAuthor().getAvatarUrl());
        embed.addField("User ID: ", user.getAvatarId(), false);
        embed.addField("Roles", String.valueOf(member.getRoles()), false);
        embed.addField("Joined on: ", String.valueOf(user.getCreationTime()), false);
        event.getChannel().sendMessage(embed.build()).queue();
    }
}
