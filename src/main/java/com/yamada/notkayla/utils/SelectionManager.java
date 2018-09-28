package com.yamada.notkayla.utils;

import net.dv8tion.jda.core.entities.User;

import java.util.HashMap;
import java.util.Map;

public class SelectionManager {
    private class SelRequest {
    }

    public static Map<User,SelRequest> selectionMap = new HashMap<>();

    public static SelRequest requestSelection(){
        return null;//TODO: manage selection requests
    }
}
