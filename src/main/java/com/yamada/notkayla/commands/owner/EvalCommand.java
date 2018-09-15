package com.yamada.notkayla.commands.owner;

import com.yamada.notkayla.Events;
import com.yamada.notkayla.commands.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import javax.script.*;
import java.io.*;

@Command(name = "eval",group="owner",hidden=true)
public class EvalCommand {
    //todo finish eval
    private SimpleScriptContext ctx = new SimpleScriptContext();
    private EvalWriter w;

    public EvalCommand(){
        w = new EvalWriter();
        ctx.setReader(null);
        ctx.setWriter(w);
        ctx.setErrorWriter(w);
    }
    public void run(JDA bot, GuildMessageReceivedEvent event, String[] args) {//i returned the previous one because it didn't need to even get changed
        if(bot.getGuildById("481210197453438996").getMemberById(event.getAuthor().getId()) == null) return;//don't even say anything, just ignore the call
        if (w.tc == null)bot.getGuildById("481210197453438996").getTextChannels().forEach((tc)->{});
        String arg = String.join(" ", args);
        //in which case go ahead
        ScriptEngine se = Events.registry.sf.getEngineByName("JavaScript");
        se.setContext(ctx);
        try {
            se.eval(arg,ctx);
        } catch (ScriptException e) {
            StringBuilder tbuild = new StringBuilder();
            StackTraceElement[] trace = e.getStackTrace();
            for (StackTraceElement traceElement : trace)
                tbuild.append("\tat ").append(traceElement);
            event.getChannel().sendMessage("```"+tbuild.toString()+"```").submit();
            e.printStackTrace();
        }
    }
    class EvalWriter extends Writer {
        TextChannel tc;
        StringBuffer sb = new StringBuffer();
        EvalWriter(){
            sb.setLength(9970);// 9970 character string buffer to account for ```text```
        }
        @Override
        public void write(@NotNull char[] cbuf, int off, int len) {
            sb.append(cbuf).append("\n");
        }

        @Override
        public void flush() {
            for (int i=0;i<Math.ceil(sb.length()/5);i++){
                char[] chars = new char[2000];
                sb.getChars(3+(1996*i),(1996*i)-3,chars,0);
                chars[0] = '`';
                chars[1] = '`';
                chars[2] = '`';
                chars[1997] = '`';
                chars[1998] = '`';
                chars[1999] = '`';
                tc.sendMessage(new String(chars)).queue();
            }
            sb.delete(0,sb.length());
        }

        @Override//ignore this
        public void close() {}
    }
}
