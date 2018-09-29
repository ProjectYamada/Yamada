package com.yamada.notkayla.utils;

import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

public class SelectionManager {
    //direct access is not ok for these two maps hence the privacy
    private static Map<Long,Selection> selectionMap = new HashMap<>();
    private static Map<Long,Integer> selected;

    public static Selection requestSelection(Long uid,int lowest,int highest){
        Selection selection = new Selection();
        selectionMap.put(uid,selection);
        return selection;
    }
    public static boolean select(User user, int userSelection){
        if (selectionMap.containsKey(user.getIdLong())) {
            Selection selection = selectionMap.get(user.getIdLong());
            selection.hasSelected.countDown();
        }
        return false;
    }

    private static class Selection{
        public CountDownLatch hasSelected = new CountDownLatch(1);
        public void start(){

        }
        public void get(){

        }
    }
}
