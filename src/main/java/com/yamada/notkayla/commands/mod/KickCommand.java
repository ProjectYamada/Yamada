package com.yamada.notkayla.commands.mod;

import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class KickCommand implements Command {

  @Override
  public void run(JDA bot, GuildMessageRecievedEvent event, String[] args) {
    
    try {
      event.getGuild().getController().kick(event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0))).queue();
    catch (Exception e) {
      event.getChannel().sendMessage("I couldn't kick the specified user.").queue();
      return;
    }
    event.getChannel().sendMessage("I successfully kicked " + event.getMessage().getMentionedUsers().get(0).getName() + ".").queue();

  }

}
