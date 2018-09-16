package com.yamada.notkayla.module.entities;

public class UserData {
    private String id;
    private long coins;
    public UserData(String uid, long coins) {
        id = uid;
        this.coins = coins;
    }

    public long getCoins() {
        return coins;
    }
    public String getId() {
        return id;
    }
}
