package com.yamada.notkayla.utils;

import net.dv8tion.jda.core.entities.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

public class SelectionManager {
    //direct access is not ok for these two maps hence the privacy
    private static Map<Long,Selection> selectionMap = new HashMap<>();

    /**
     *
     * @param uid user id long
     * @param lowest lowest possible answer
     * @param highest highest possible answer
     * @param def default integer which will be selected if canceled
     * @return Selection allowing for requests to be answered
     */
    public static Selection requestSelection(Long uid,int lowest,int highest,int def){
        Selection selection = new Selection(lowest, highest, def);
        selectionMap.put(uid,selection);
        return selection;
    }

    public static boolean hasSelectionAvailable(Long id){
        return selectionMap.containsKey(id);
    }

    public static void select(Long user, int userSelection){
        if (selectionMap.containsKey(user)) {
            Selection selection = selectionMap.get(user);
            selection.selected = userSelection;
            selection.hasSelected.countDown();
        }
    }

    public static class Selection{
        int selected = 0;  /// zero is null equivalent | 0 == not a number or not between the scale provided in request
        final int lowest; //must not be zero
        final int highest; // must not be lower than lowest
        final int def; //will be chosen if number is not in range
        Selection(int lowest, int highest, int def){
            this.lowest = lowest;
            this.highest = highest;
            this.def = def;
        }
        CountDownLatch hasSelected = new CountDownLatch(1);
        public CompletableFuture<Integer> get() {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    hasSelected.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (selected == 0) selected = def;
                if (selected < lowest && selected > highest) selected = def;
                return selected;
            });
        }
    }
}
