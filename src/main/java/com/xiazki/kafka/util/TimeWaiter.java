package com.xiazki.kafka.util;

public class TimeWaiter {


    public void waitTime(long time) {
        synchronized (this) {
            try {
                this.wait(time);
            } catch (InterruptedException e) {
                //todo log
            }
        }
    }

}
