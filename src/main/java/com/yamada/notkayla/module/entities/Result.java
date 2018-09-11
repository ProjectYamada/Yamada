package com.yamada.notkayla.module.entities;

public class Result {
    private boolean success;
    private String message;
    private Object resultingObject = null;
    private Throwable cause = null;
    public Result(boolean successful,String message){
        success = successful;
        this.message = message;
    }
    public Result(boolean successful,String message,Object obj){
        success = successful;
        this.message = message;
        resultingObject = obj;
    }
    public Result(boolean successful,String message,Object obj,Throwable cause){
        success = successful;
        this.message = message;
        resultingObject = obj;
        this.cause = cause;
    }

    public String getMessage() {
        return message;
    }

    public boolean wasSuccessful() {
        return success;
    }

    public Object getResult() {
        return resultingObject;
    }

    public Throwable getCause() {
        return cause;
    }
}
