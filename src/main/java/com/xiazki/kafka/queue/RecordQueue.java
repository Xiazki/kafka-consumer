package com.xiazki.kafka.queue;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;

/**
 * 队列
 */
public class RecordQueue<K, V> {

    private String queueId;

    public List<ConsumerRecord<K, V>> poll(Integer size) {
        return null;
    }


    public void add(ConsumerRecord<K, V> record) {

    }

    public void clear() {

    }
}
