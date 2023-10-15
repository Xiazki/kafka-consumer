package com.xiazki.kafka.dispatcher;

import com.xiazki.kafka.queue.QueueManager;
import com.xiazki.kafka.rebalance.QueueReBalance;
import com.xiazki.kafka.service.Processor;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public class RecordDispatcher<K, V> {
    private QueueManager queueManager;

    private QueueReBalance queueReBalance;


    /**
     * @param queueManager
     * @param isOrder
     */
    public RecordDispatcher(QueueManager queueManager, boolean isOrder) {
        this.queueManager = queueManager;
    }

    public RecordDispatcher(QueueManager queueManager, QueueReBalance queueReBalance) {
        this.queueManager = queueManager;
        this.queueReBalance = queueReBalance;
    }

    /**
     * 分发数据
     *
     * @param records 数据记录列表
     */
    public void distribute(ConsumerRecords<K, V> records) {

    }

}
