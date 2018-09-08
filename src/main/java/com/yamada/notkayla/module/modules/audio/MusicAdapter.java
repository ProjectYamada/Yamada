package com.yamada.notkayla.module.modules.audio;

import com.yamada.notkayla.module.Adapter;
import net.dv8tion.jda.core.entities.TextChannel;

public class MusicAdapter extends Adapter {
    public MusicAdapter() throws InstantiationException, IllegalAccessException {
        super("com.yamada.notkayla.module.modules.audio.MusicModule.java");
    }

    public void loadAndPlay(TextChannel channel, String join) {
    }
}
