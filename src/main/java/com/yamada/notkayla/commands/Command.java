package com.yamada.notkayla.commands;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public @interface Command {
    String name();
    String description() default "Not yet set.";
    String group();
    boolean hidden() default false;
}
