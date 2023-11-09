package com.xiazki.kafka.queue;

import com.xiazki.kafka.service.RecordData;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 队列
 */
public class RecordQueue {

    private String queueId;

    private BlockingQueue<ConsumerRecord<?, ?>> queue;

    private Map<TopicPartition, Long> minOffsetMap = new HashMap<>();

    public RecordQueue(String queueId) {
        this.queueId = queueId;
        queue = new LinkedBlockingDeque<>();
    }

    public RecordData<?, ?> poll(Integer size) {
        return null;
    }


    public void add(ConsumerRecord<?, ?> record) {
        if (record == null) {
            return;
        }
        queue.add(record);
        TopicPartition topicPartition = new TopicPartition(record.topic(), record.partition());
        Long offset = minOffsetMap.get(topicPartition);
        if (offset == null || record.offset() < offset) {
            minOffsetMap.put(topicPartition, offset);
        }
    }

    public void clear() {

    }
}
