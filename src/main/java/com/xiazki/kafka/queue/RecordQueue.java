package com.xiazki.kafka.queue;

import com.xiazki.kafka.service.RecordData;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 队列
 */
public class RecordQueue {

    private String queueId;

    private BlockingQueue<RecordData<?, ?>> queue;

    public RecordQueue(String queueId) {
        this.queueId = queueId;
        queue = new LinkedBlockingDeque<>();
    }

    public RecordData<?, ?> poll(Integer size) {
        return null;
    }


    public void add(ConsumerRecord<?, ?> record) {

    }

    public void clear() {

    }
}
