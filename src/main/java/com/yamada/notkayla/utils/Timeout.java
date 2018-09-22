package com.yamada.notkayla.utils;

public class Timeout{
    private int delay;
    private Thread runnable;
    private boolean canceled = true;
    public Timeout(int delay, Runnable run){
        this.delay = delay;
        runnable = new Thread(run);
    }

    public void cancel(){
        canceled = true;
    }
    public void start() throws InterruptedException {
        Thread.sleep(delay);
        if (!canceled) runnable.start();
    }
}
