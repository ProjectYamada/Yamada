package com.yamada.notkayla.module.modules.audio;

import com.yamada.notkayla.module.Adapter;
import net.dv8tion.jda.core.entities.TextChannel;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class MusicAdapter extends Adapter {
    public MusicAdapter(Map config) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        super("com.yamada.notkayla.module.modules.audio.MusicModule.java",config);
    }

    public void loadAndPlay(TextChannel channel, String join) {
    }
}
