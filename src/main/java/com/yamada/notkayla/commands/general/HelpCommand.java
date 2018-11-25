package com.yamada.notkayla.commands.general;

import com.yamada.notkayla.Events;
import com.yamada.notkayla.Yamada;
import com.yamada.notkayla.commands.Command;
import com.yamada.notkayla.commands.CommandRegistry;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import java.awt.Color;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Command(name = "help",group="general",description = "You're viewing it")
public class HelpCommand {
    private static Map<String,Group> groupDefs = new HashMap<>() {{
        put("general", new Group("General","general","Miscellaneous commands"));
        put("mod", new Group("Moderation","mod", "Moderation tools"));
        put("image", new Group("Image","image", "Image sending"));
        put("fun", new Group("Fun","fun", "It's \"Fun\""));
        put("music", new Group("Music","music","Music playback"));
        put("anime",new Group("Anime","anime","Anime related"));
    }};
    private static Group none = new Group("Command Groups", "none", String.format("%shelp <group name>",Yamada.configuration.get("prefix")));
    static {
        groupDefs.forEach((locName,group)-> none.commands.put(String.format("%s - `%s`", group.name, locName),group.description));
    }
//    public HelpCommand(Map<String,Object> commands) {
//        embed.setColor(new Color(0xe91e63));
//        embed.setDescription("I'm Yamada, and my prefix is `!y`. I hope to make your server a better place!");
//        embed.setTitle("Need some help?");//todo: set commands up :b:etter because i think it was my fault -Sanae
//        embed.addField("Image", "`dog` - Fetches a random dog\n`cat` - Fetches a random cat\n`duck` - Fetches a random duck", false);
//        embed.addField("Fun", "`meme` - Fetches a random meme\n`urban` - Look up Urban Dictionary definitions", false);
//        embed.addField("Anime", "`danbooru` - Fetches an image from danbooru", false);
//        embed.addField("Moderation", "`kick` - Kicks the specified user from your server\n`ban` - Bans the specified user from your server", false);
//        embed.addField("Music","`play` - Plays music using the specified search text\n`skip` - Skips a song in the queue\n`stop` - Stops the queue if not empty and disconnects from the voice chat",false);
//        embed.addField("Miscellaneous","`info` -",false);
//        Logger logger = Logger.getLogger("Help");
//        /*commands.forEach((cmdName,cmd)->{
//            try {
//                Command command = cmd.getClass().getField("cmd").get(cmd.getClass().getField("instance")).getClass().getAnnotation(Command.class);
//                logger.log(Level.INFO,command.toString() );
//                if (!command.hidden()) {
//                    //add command to group
//                    groupDefs.get(command.group()).commands.put(command.name(),command.description());
//                }
//            } catch (NoSuchFieldException | IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        });*/
//
//        /*
//            then loop through the groups and add them field-group name newline-`command` - descr + usage
//            we may have to do page handling, but that's not much of an issue
//        */
//    }

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
//        embed.setThumbnail(bot.getSelfUser().getAvatarUrl());
//        embed.setFooter(String.format("Hello, %s", event.getAuthor().getName()), event.getAuthor().getAvatarUrl());
        String page = "none";
        if (args.length != 1) page = args[1];
        event.getChannel().sendMessage(generateEmbed(event,page)).complete();
    }

    private boolean haveCommandsBeenPutIn = false;//extensive name
    private MessageEmbed generateEmbed(GuildMessageReceivedEvent event, String groupName) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0xe91e63));
        Group group = none;
        Yamada.log.log(Level.INFO,groupName);
        if (groupDefs.containsKey(groupName)) group = groupDefs.get(groupName);
        embed.setDescription("I'm Yamada, and my prefix is `!y`. I hope to make your server a better place!\n\n**" + group.name + "**\n"+group.description);
        embed.setTitle("Need some help?");
        embed.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
        embed.setFooter(String.format("Hello, %s", event.getAuthor().getName()), event.getAuthor().getAvatarUrl());
        if (!haveCommandsBeenPutIn) {
            for (CommandRegistry.RegCommand regCommand : Events.registry.getCommands().values()) {
                Command cmd = regCommand.cmd.getAnnotation(Command.class);
                if (cmd.hidden()) continue;
                Yamada.log.log(Level.INFO, String.format("Adding command %s to the group %s", cmd.name(), cmd.group()));
                if (!groupDefs.containsKey(cmd.group())) {
                    Group unset = new Group("`" + cmd.group() + "`", cmd.group(), "(unset group, report this)");
                    groupDefs.put(cmd.group(), unset);
                    none.commands.put(String.format("%s - `%s`", unset.name, cmd.group()),unset.description);
                }
                groupDefs.get(cmd.group()).commands.put("**" + Yamada.configuration.get("prefix") + cmd.name() + "**", cmd.description());
            }
            haveCommandsBeenPutIn = true;
        }
        group.commands.forEach((name, description) -> embed.addField(name, description, false));
        return embed.build();
    }
    private static class Group{
        String name;
        String locName;
        String description;
        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        Map<String, String> commands = new HashMap<>();
        Group(String name,String locName,String description) {
            this.name = name; this.locName = locName; this.description = description;
            if (locName.equals("none")) this.description = String.format(description,Yamada.configuration.get("prefix"));
        }
    }
}