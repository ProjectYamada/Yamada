package com.yamada.notkayla.commands.general;

import com.yamada.notkayla.Kayla;
import com.yamada.notkayla.commands.Command;
import com.yamada.notkayla.commands.CommandRegistry;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import java.awt.Color;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Command(name = "help",group="general",description = "You're viewing it")
public class HelpCommand {
    private EmbedBuilder embed = new EmbedBuilder();
    private Map<String,Group> groupDefs = new HashMap<String, Group>(){{
        put("mod",new Group("Moderation"));
        put("general",new Group("General"));
        put("image",new Group("Image"));
        put("fun",new Group("Fun"));
    }};
    public HelpCommand(Map<String,Object> commands,Class regCommand) throws ClassNotFoundException {
        embed.setColor(new Color(0xe91e63));
        embed.setTitle("Need some help?");//todo: set commands up :b:etter because i think it was my fault -Sanae
        embed.addField("Image", "`dog` - Fetches a random dog\n`cat` - Fetches a random cat\n`duck` - Fetches a random duck", false);
        embed.addField("Fun", "`meme` - Fetches a random meme\n`urban` - Look up Urban Dictionary definitions", false);
        embed.addField("Anime", "`danbooru` - Fetches an image from danbooru", false);
        embed.addField("Moderation", "`kick` - Kicks the specified user from your server\n`ban` - Bans the specified user from your server", false);
        Logger logger = Logger.getLogger("Help");
        commands.forEach((cmdName,cmd)->{
            try {
                Field comman = regCommand.cast(cmd).getClass().getField("cmd");
                logger.log(Level.INFO,"Registered "+comman);
                Command command = comman.getDeclaringClass().getAnnotation(Command.class);
                logger.log(Level.INFO,comman.getDeclaringClass().getCanonicalName());
                logger.log(Level.INFO,command.toString() );
                if (!command.hidden()) {
                    //add command to group
                    groupDefs.get(command.group()).commands.put(command.name(),command.description());
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        });

        /*
            then loop through the groups and add them field-group name newline-`command` - descr + usage
            we may have to do page handling, but that's not much of an issue
        */
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
        embed.setThumbnail(bot.getSelfUser().getAvatarUrl());
        embed.setFooter(String.format("Hello, %s", event.getAuthor().getName()), event.getAuthor().getAvatarUrl());

        event.getChannel().sendMessage(embed.build()).queue();
    }
    private class Group{
        String name;
        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        Map<String, String> commands;
        Group(String name) {
            this.name = name;
        }
    }
}