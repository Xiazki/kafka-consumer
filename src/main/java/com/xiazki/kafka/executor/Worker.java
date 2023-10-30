package com.xiazki.kafka.executor;

import com.xiazki.kafka.queue.RecordQueue;
import com.xiazki.kafka.service.Processor;
import com.xiazki.kafka.queue.QueueSizeCoordinator;
import com.xiazki.kafka.service.RecordData;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;

public class Worker<K,V> extends Thread {

    /**
     * 处理队列
     */
    private RecordQueue recordQueue;

    /**
     * 处理器
     */
    private Processor processor;

    /**
     * 最近处理的记录
     */
    private ConsumerRecord<?, ?> leastRecord;

    private QueueSizeCoordinator queueSizeCoordinator;


    private int processSize;

    private volatile boolean running = true;

    public Worker(RecordQueue recordQueue, Processor processor, QueueSizeCoordinator queueSizeCoordinator, int processSize) {
        this.recordQueue = recordQueue;
        this.processor = processor;
        this.queueSizeCoordinator = queueSizeCoordinator;
        this.processSize = processSize;
    }

    @Override
    public void run() {

        while (running) {
            RecordData<?, ?> consumerRecords = recordQueue.poll(processSize);
            //todo 处理模型 failover 机制
            processor.process(consumerRecords);
//            queueSizeCoordinator.release(consumerRecords.size());
//            leastRecord = consumerRecords.get(consumerRecords.size() - 1);
        }
    }

    public void close() {
        this.running = false;
    }
}
