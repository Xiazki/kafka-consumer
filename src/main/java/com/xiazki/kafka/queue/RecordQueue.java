package com.xiazki.kafka.queue;

import com.xiazki.kafka.service.RecordData;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;

/**
 * 队列
 */
public class RecordQueue {

    private String queueId;

    public RecordData<?, ?> poll(Integer size) {
        return null;
    }


    public void add(ConsumerRecord<?, ?> record) {

    }

    public void clear() {

    }
}
