package com.xiazki.kafka.service;

import com.xiazki.kafka.dispatcher.RecordDispatcher;
import com.xiazki.kafka.queue.QueueManager;
import com.xiazki.kafka.queue.QueueSizeCoordinator;
import com.xiazki.kafka.util.TimeWaiter;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;

public class PullService<K, V> extends Thread {

    KafkaConsumer<K, V> consumer;

    RecordDispatcher recordDispatcher;

    private volatile boolean running = false;

    public PullService(KafkaConsumer<K, V> consumer, RecordDispatcher recordDispatcher) {
        this.consumer = consumer;
        this.recordDispatcher = recordDispatcher;
    }

    @Override
    public void run() {
        this.pull();
    }

    /**
     * 拉去数据
     */
    public void pull() {
        while (running) {
            try {
                ConsumerRecords<K, V> consumerRecords = consumer.poll(Duration.ofMillis(50));
                if (consumerRecords != null) {
                    recordDispatcher.distribute(consumerRecords);
                }
            } catch (Throwable ex) {
                //todo 日志
                //防止线程空跑
                new TimeWaiter().waitTime(50);
            }
        }
    }

    /**
     * 销毁
     */
    public void destroy() {
        running = false;
        if (consumer != null) {
            consumer.close();
            consumer = null;
        }
    }
}
