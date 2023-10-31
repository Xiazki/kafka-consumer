package com.xiazki.kafka.executor;

import com.xiazki.kafka.failover.RetryTemplate;
import com.xiazki.kafka.queue.RecordQueue;
import com.xiazki.kafka.service.Processor;
import com.xiazki.kafka.queue.QueueSizeCoordinator;
import com.xiazki.kafka.service.RecordData;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;

public class Worker extends Thread {

    /**
     * 处理队列
     */
    private RecordQueue recordQueue;

    /**
     * 最近处理的记录
     */
    private ConsumerRecord<?, ?> leastRecord;

    private QueueSizeCoordinator queueSizeCoordinator;


    private int processBatchSize;

    private volatile boolean running = true;

    private RetryTemplate retryTemplate;

    public Worker(RecordQueue recordQueue, QueueSizeCoordinator queueSizeCoordinator, int processBatchSize, RetryTemplate retryTemplate) {
        this.recordQueue = recordQueue;
        this.queueSizeCoordinator = queueSizeCoordinator;
        this.processBatchSize = processBatchSize;
        this.retryTemplate = retryTemplate;
    }

    @Override
    public void run() {
        while (running) {
            RecordData<?, ?> consumerRecords = recordQueue.poll(processBatchSize);
            retryTemplate.execute(consumerRecords);
            this.leastRecord = calcLastRecord(consumerRecords);
        }
    }

    public void close() {
        this.running = false;
    }

    /**
     * 获取上次成功的记录
     *
     * @return 消息记录
     */
    public ConsumerRecord<?, ?> getLeastRecord() {
        return leastRecord;
    }

    private ConsumerRecord<?, ?> calcLastRecord(RecordData<?, ?> consumerRecords) {
        List<? extends ConsumerRecord<?, ?>> records = consumerRecords.getRecords();
        if (records == null || records.size() == 0) {
            return null;
        }
        return records.get(records.size() - 1);
    }
}
