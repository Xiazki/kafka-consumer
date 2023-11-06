package com.xiazki.kafka.executor;

import com.xiazki.kafka.failover.RetryConfig;
import com.xiazki.kafka.failover.RetryTemplate;
import com.xiazki.kafka.queue.QueueManager;
import com.xiazki.kafka.queue.RecordQueue;
import com.xiazki.kafka.service.Processor;

import java.util.ArrayList;
import java.util.List;

public class MessageExecutorService {

    private QueueManager queueManager;

    private int processBatchSize;

    private RetryTemplate retryTemplate;

    private List<Worker> workers = new ArrayList<>();

    public MessageExecutorService(QueueManager queueManager, Processor processor, RetryConfig retryConfig, int processBatchSize) {
        this.queueManager = queueManager;
        this.processBatchSize = processBatchSize;
        this.retryTemplate = new RetryTemplate(retryConfig, processor);
    }

    public synchronized void start() {
        for (Worker worker : workers) {
            worker.start();
        }
    }

    public synchronized void createWorker(RecordQueue queue) {
        Worker worker = new Worker(queue, queueManager.getQueueSizeCoordinator(), processBatchSize, retryTemplate);
        workers.add(worker);
    }

    public List<Worker> getAllWorkers() {
        return workers;
    }

    public synchronized void destroy() {
        for (Worker worker : workers) {
            worker.close();
        }
        workers.clear();
    }
}
