package com.yamada.notkayla.commands.owner;

import com.yamada.notkayla.Kayla;
import com.yamada.notkayla.commands.Checks;
import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

public class EvalCommand implements Command {
//todo finish eval
    SimpleScriptContext ctx = new SimpleScriptContext();
    public EvalCommand(){
        EvalWriter w = new EvalWriter();
        ctx.setWriter(w);
        ctx.setErrorWriter(w);

    }
    @Override
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {
        if(!Checks.isAdmin(event.getAuthor().getId())) return;
        StringBuilder builder = new StringBuilder();
        for(String s : args) {
            builder.append(s);
        }
        String arg = builder.toString();
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
    class EvalWriter extends Writer {
        TextChannel tc = Kayla.bot.getTextChannelById("481528711720730634");
        @Override
        public void write(@NotNull char[] cbuf, int off, int len) {
            tc.sendMessage(cbuf.toString());
        }

        @Override
        public void flush() {

        }

        @Override
        public void close() {

        }
    }
}
