package com.yamada.notkayla.commands.general;

import com.yamada.notkayla.Kayla;
import com.yamada.notkayla.commands.Command;
import com.yamada.notkayla.commands.CommandRegistry;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

@Command(name = "help",group="general",description = "You're viewing it")
public class HelpCommand {
    private EmbedBuilder embed = new EmbedBuilder();
    Map<String,Group> groupDefs = new HashMap<String, Group>(){{
        put("mod",new Group("Moderation"));
        put("general",new Group("General"));
        put("image",new Group("Image"));
        put("fun",new Group("Fun"));
    }};
    public HelpCommand(){
        embed.setColor(new Color(0xe91e63));
        embed.setTitle("Need some help?");
        Kayla.registry.commands.forEach((cmdName,cmd)->{
            Command command = cmd.cmd.getAnnotation(Command.class);
            if (command.hidden()) return;

        });
    }

    /**
     * things that can be set on init
     * - title
     * - color
     * - fields
     * and can't
     * - description (prefix)
     * - footer
     * - thumbnail
     */

    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        embed.setDescription("I'm Yamada, and my prefix is `!y`. I hope to make your server a better place!");
        embed.setThumbnail(event.getGuild().getSelfMember().getUser().getAvatarUrl());
        embed.setFooter(String.format("Hello, %s", event.getAuthor().getName()), event.getAuthor().getAvatarUrl());
        embed.addField("Image", "`dog` - Fetches a random dog\n`cat` - Fetches a random cat\n`duck` - Fetches a random duck", false);
        embed.addField("Fun", "`meme` - Fetches a random meme\n`urban` - Look up Urban Dictionary definitions", false);
        embed.addField("Anime", "`danbooru` - Fetches an image from danbooru", false);
        embed.addField("Moderation", "`kick` - Kicks the specified user from your server\n`ban` - Bans the specified user from your server", false);
        event.getChannel().sendMessage(embed.build()).queue();

    }
    private class Group{
        String name;
        Map<String, String> commands;
        public Group(String name) {
            this.name = name;
        }
    }
}
/*
@bot.event
async def on_message(msg):
    your code here
#
bot.run("token")
*/