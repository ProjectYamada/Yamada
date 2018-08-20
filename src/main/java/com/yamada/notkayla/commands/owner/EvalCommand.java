package com.yamada.notkayla.commands.owner;

import com.yamada.notkayla.Kayla;
import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class EvalCommand implements Command {
//todo finish eval
    @Override
    public void run(JDA bot, GuildMessageReceivedEvent event) {
        String arg = event.getMessage().getContentRaw().substring("!yeval ".length());//todo dont use fucking hardcoded prefix if we dont have database support
        //in which case go ahead
        ScriptEngine se = Kayla.registry.sf.getEngineByExtension("JavaScript");
        try {
            se.eval(arg);
        } catch (ScriptException e) {
            StringBuilder tbuild = new StringBuilder();
            StackTraceElement[] trace = e.getStackTrace();
            for (StackTraceElement traceElement : trace)
                tbuild.append("\tat " + traceElement);
            event.getChannel().sendMessage("```"+e.getMessage()+"```").submit();
            e.printStackTrace();
        }
    }
}
