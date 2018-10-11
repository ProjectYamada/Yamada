package com.yamada.notkayla.utils;

public class Timeout{
    private int delay;
    private Thread runnable;
    private boolean canceled = false;
    public Timeout(int delay, Runnable run){
        this.delay = delay;
        runnable = new Thread(run);
    }

    public void cancel(){
        canceled = true;
    }
    public void start() {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ignored) {
            }
            if (!canceled) runnable.start();
        }).start();
    }
}
