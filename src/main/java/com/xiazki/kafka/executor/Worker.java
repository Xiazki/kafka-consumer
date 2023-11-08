package com.xiazki.kafka.executor;

import com.xiazki.kafka.failover.RetryTemplate;
import com.xiazki.kafka.queue.RecordQueue;
import com.xiazki.kafka.service.Processor;
import com.xiazki.kafka.queue.QueueSizeCoordinator;
import com.xiazki.kafka.service.RecordData;
import org.apache.kafka.clients.consumer.ConsumerRecord;

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
     * 最近处理的记录
     */
    private ConsumerRecord<?, ?> leastRecord;

    private Map<String, ConsumerRecord<?, ?>> leastRecordMap = new HashMap<>();
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
            calcLastRecord(consumerRecords);
        }
    }

    public void close() {
        this.running = false;
        retryTemplate.terminate();
    }

    /**
     * 获取上次成功的记录
     *
     * @return 消息记录
     */
    public ConsumerRecord<?, ?> getLeastRecord() {
        return leastRecord;
    }

    public Map<String, ConsumerRecord<?, ?>> getLeastRecordMap() {
        return leastRecordMap;
    }


    private void calcLastRecord(RecordData<?, ?> consumerRecords) {
        List<? extends ConsumerRecord<?, ?>> records = consumerRecords.getRecords();
        if (records == null || records.size() == 0) {
            return;
        }
        Map<String, ? extends ConsumerRecord<?, ?>> map = records.stream().collect(Collectors.toMap(record -> record.topic() + "-" + record.partition(), o -> o, (o1, o2) -> o2));
        leastRecordMap.putAll(map);
    }
}
