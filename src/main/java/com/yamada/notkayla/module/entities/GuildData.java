package com.yamada.notkayla.module.entities;

public class GuildData {
    private String id;
    private String prefix;
    private boolean customPrefix;
    //public String variable; set these when a new database thing is added
    public GuildData(String id, String prefix, boolean customPrefix){
        this.id = id;
        this.prefix = prefix;
        this.customPrefix = customPrefix;
    }

    public String getId() {
        return id;
    }
    public String getPrefix() {
        return prefix;
    }
    public boolean isCustomPrefix() {
        return customPrefix;
    }
    public void setCustomPrefix(boolean customPrefix) {
        this.customPrefix = customPrefix;
    }
}