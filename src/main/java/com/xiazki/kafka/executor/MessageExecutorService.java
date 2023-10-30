package com.xiazki.kafka.executor;

import com.xiazki.kafka.queue.QueueManager;
import com.xiazki.kafka.queue.RecordQueue;
import com.xiazki.kafka.service.Processor;

import java.util.List;

public class MessageExecutorService {

    private QueueManager queueManager;

    private Processor processor;

    private int processBatchSize;

    public MessageExecutorService(QueueManager queueManager, Processor processor, int processBatchSize) {
        this.queueManager = queueManager;
        this.processor = processor;
        this.processBatchSize = processBatchSize;
    }

    public void start() {

    }

    public void createWorker(RecordQueue queue) {

    }

    public List<Worker> getAllWorkers() {
        return null;
    }

    public void destory() {

    }
}
