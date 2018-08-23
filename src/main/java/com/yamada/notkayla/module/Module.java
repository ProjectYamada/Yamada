package com.yamada.notkayla.module;

public @interface Module {
    String name();
    String description();
    boolean guarded();
}
