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
    }


    public Map<TopicPartition, Long> getMinOffsetMap() {
        Map<TopicPartition, Long> minOffsetMap = new HashMap<>();
        for (ConsumerRecord<?, ?> consumerRecord : queue) {
            TopicPartition topicPartition = new TopicPartition(consumerRecord.topic(), consumerRecord.partition());
            Long offset = minOffsetMap.get(topicPartition);
            if (offset == null || offset >= consumerRecord.offset()) {
                minOffsetMap.put(topicPartition, consumerRecord.offset());
            }
        }
        return minOffsetMap;
    }

    public void clear() {

        queue.clear();
    }
}
