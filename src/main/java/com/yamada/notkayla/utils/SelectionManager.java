package com.yamada.notkayla.utils;

import net.dv8tion.jda.core.entities.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class SelectionManager {
    //direct access is not ok for these two maps hence the privacy
    private static Map<Long,Selection> selectionMap = new HashMap<>();

    public static Selection requestSelection(Long uid,int lowest,int highest){
        Selection selection = new Selection();
        selectionMap.put(uid,selection);
        return selection;
    }
    public static boolean select(User user, int userSelection){
        if (selectionMap.containsKey(user.getIdLong())) {
            Selection selection = selectionMap.get(user.getIdLong());
            selection.selected = userSelection;
            selection.hasSelected.countDown();
        }
        return false;
    }

    private static class Selection{
        int selected = 0;  /// zero is null equivalent | 0 == not a number or not between the scale provided in request
        final int lowest; //must not be zero
        final int highest; // must not be lower than lowest
        Selection(int lowest, int highest){
            this.lowest = lowest;
            this.highest = highest;
        }
        CountDownLatch hasSelected = new CountDownLatch(1);
        public int get() throws InterruptedException {
            hasSelected.await();
            return selected;
        }
    }
}
