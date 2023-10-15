package com.xiazki.kafka.queue;

public class QueueSizeCoordinator {

    private int recordNum;

    public QueueSizeCoordinator(int recordNum) {
        this.recordNum = recordNum;
    }

    public void require(int size) {
    }

    public void release() {
    }

}
