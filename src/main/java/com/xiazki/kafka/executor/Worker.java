package com.xiazki.kafka.executor;

import com.xiazki.kafka.queue.RecordQueue;
import com.xiazki.kafka.service.Processor;
import com.xiazki.kafka.queue.QueueSizeCoordinator;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class Worker<K, V> extends Thread {

    /**
     * 处理队列
     */
    private RecordQueue<K, V> recordQueue;

    /**
     * 处理器
     */
    private Processor<K, V> processor;

    /**
     * 最近处理的记录
     */
    private ConsumerRecord<K, V> leastRecord;

    private QueueSizeCoordinator queueSizeCoordinator;

    public Worker(RecordQueue<K, V> recordQueue, Processor<K, V> processor, QueueSizeCoordinator queueSizeCoordinator) {

    }

    @Override
    public void run() {
        super.run();
    }
}
