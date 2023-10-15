package com.xiazki.kafka.service;

import com.xiazki.kafka.dispatcher.RecordDispatcher;
import com.xiazki.kafka.queue.QueueManager;
import com.xiazki.kafka.queue.QueueSizeCoordinator;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class PullService<K, V> extends Thread {

    KafkaConsumer<K, V> consumer;

    RecordDispatcher<K, V> recordDispatcher;

    QueueSizeCoordinator queueSizeCoordinator;

    public PullService(KafkaConsumer<K, V> consumer, RecordDispatcher<K, V> recordDispatcher) {
        this.consumer = consumer;
    }

    @Override
    public void run() {
        this.pull();
    }

    /**
     * 拉去数据
     */
    public void pull() {
    }

    public void destory() {

    }
}
