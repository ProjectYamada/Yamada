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
import java.util.logging.Logger;

@Command(name = "help",group="general",description = "You're viewing it")
public class HelpCommand {
    private EmbedBuilder embed = new EmbedBuilder();
    private Map<String,Group> groupDefs = new HashMap<>() {{
        put("mod", new Group("Moderation","mod"));
        put("general", new Group("General","general"));
        put("image", new Group("Image","image"));
        put("fun", new Group("Fun","anime"));
        put("music", new Group("Music","music"));
        put("anime",new Group("Anime","anime"));
    }};
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
        int page = args.length == 1 ? 0 : Integer.parseInt(args[1]);
        Message complete = event.getChannel().sendMessage(generateEmbed(event,page)).complete();
        Events.listenForReaction(complete,(a)->{

        });
    }

    private Map<String,MessageEmbed.Field[]> fields= new HashMap<>();
    private MessageEmbed generateEmbed(GuildMessageReceivedEvent event,int page){
        EmbedBuilder embed = this.embed;
        embed.setColor(new Color(0xe91e63));
        Group group = new ArrayList<>(groupDefs.values()).get(page);
        embed.setDescription("I'm Yamada, and my prefix is `!y`. I hope to make your server a better place!\n\n**"+group.name+"**");
        embed.setTitle("Need some help?");
        embed.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
        embed.setFooter(String.format("Hello, %s", event.getAuthor().getName()), event.getAuthor().getAvatarUrl());
        if (fields.size() == 0) for (CommandRegistry.RegCommand regCommand : Events.registry.getCommands().values()) {
            Command cmd = regCommand.cmd.getAnnotation(Command.class);
            if (cmd.hidden()) continue;
            if (!groupDefs.containsKey(cmd.group())) {
                groupDefs.put(cmd.group(),new Group("`"+cmd.group()+" (unset group, report this)`",cmd.group()));
            }
            groupDefs.get(cmd.group()).commands.put("**"+Yamada.configuration.get("prefix")+cmd.name()+"**",cmd.description());
        }
        group.commands.forEach((name,description)-> embed.addField(name,description,false));
        return embed.build();
    }
    private class Group{
        String name;
        String locName;
        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        Map<String, String> commands;
        Group(String name,String locName) {
            this.name = name; this.locName = locName;
        }
    }
}