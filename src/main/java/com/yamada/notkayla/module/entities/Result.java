package com.yamada.notkayla.module.entities;

public class Result {
    private boolean success;
    private String message;
    public Result(boolean successful,String message){

    }
    public Result(boolean successful,String message,Throwable cause){

    }

    public String getMessage() {
        return message;
    }

    public boolean wasSuccessful() {
        return success;
    }
}
