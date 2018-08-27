package com.yamada.notkayla.utils;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.requests.restaction.MessageAction;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Random;

public class GettingFiles {

    public static InputStream image(){
        CloseableHttpClient cli = HttpClients.createDefault();
        try {
            CloseableHttpResponse res = cli.execute(new HttpGet("https://cdn2.desu-usergeneratedcontent.xyz/g/image/1530/34/1530341410768.png"));
            return res.getEntity().getContent();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Smug yamada for the people who think they can get away with the shit you call them out for.
     * @param tc
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void didYouJustTryThat(TextChannel tc, long id){
        String[] smugQuips = new String[]{"Did you really think you could get away with that?","Why would you be able to do that?","Nope.","You aren't allowed to do that"};
        int quip = new Random(id).nextInt();
        MessageAction ma = tc.sendMessage(smugQuips[quip]);
        InputStream img = image();
        if(img !=null)ma.addFile(Objects.requireNonNull(image()), "didyoueventhinkthatwouldw.ork").queue();
        else ma.queue();
    }
}
