package com.xiazki.kafka.executor;

import com.xiazki.kafka.failover.RetryTemplate;
import com.xiazki.kafka.queue.RecordQueue;
import com.xiazki.kafka.service.Processor;
import com.xiazki.kafka.queue.QueueSizeCoordinator;
import com.xiazki.kafka.service.RecordData;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Worker extends Thread {

    /**
     * 处理队列
     */
    private RecordQueue recordQueue;

    /**
     * 正在处理的记录
     */
    private Map<TopicPartition, Long> processingRecordMap = new HashMap<>();

    /**
     * 记录最近处理成功的记录
     */
    private Map<TopicPartition, Long> leastRecordMap = new HashMap<>();


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
            Map<TopicPartition, Long> map = calcLastRecord(consumerRecords);
            processingRecordMap = map;
            retryTemplate.execute(consumerRecords);
            leastRecordMap = map;
        }
    }

    public void close() {
        this.running = false;
        retryTemplate.terminate();
    }

    public Map<TopicPartition, Long> getLeastRecordMap() {
        return leastRecordMap;
    }

    public Map<TopicPartition, Long> getProcessingRecordMap() {
        return processingRecordMap;
    }

    public Map<TopicPartition, Long> getQueueMinOffsetMap() {
        return recordQueue.getMinOffsetMap();
    }


    private Map<TopicPartition, Long> calcLastRecord(RecordData<?, ?> consumerRecords) {
        List<? extends ConsumerRecord<?, ?>> records = consumerRecords.getRecords();
        if (records == null || records.size() == 0) {
            return null;
        }
        return records.stream().collect(Collectors.toMap(record -> new TopicPartition(record.topic(), record.partition()), ConsumerRecord::offset, (o1, o2) -> o2));
    }
}
