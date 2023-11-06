package com.xiazki.kafka.dispatcher;

import com.xiazki.kafka.queue.QueueManager;
import com.xiazki.kafka.queue.RecordQueue;
import com.xiazki.kafka.rebalance.QueueReBalance;
import com.xiazki.kafka.rebalance.SequenceQueueReBalance;
import com.xiazki.kafka.service.OrderlyProcessor;
import com.xiazki.kafka.service.Processor;
import com.xiazki.kafka.service.RecordData;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.List;

public class RecordDispatcher {
    private QueueManager queueManager;

    private QueueReBalance queueReBalance;

    private Processor processor;


    /**
     * @param queueManager
     */
    public RecordDispatcher(QueueManager queueManager, Processor processor) {
        this.queueManager = queueManager;
        this.processor = processor;
        this.queueReBalance = new SequenceQueueReBalance();
    }

    public RecordDispatcher(QueueManager queueManager, Processor processor, QueueReBalance queueReBalance) {
        this.queueManager = queueManager;
        this.queueReBalance = queueReBalance;
        this.processor = processor;
    }

    /**
     * 分发数据
     *
     * @param records 数据记录列表
     */
    public <K, V> void distribute(ConsumerRecords<K, V> records) {
        for (ConsumerRecord<K, V> record : records) {
            queueManager.getQueueSizeCoordinator().require(1);
            List<String> queueIdList = queueManager.getQueueIdList();
            String partitionValue = processor instanceof OrderlyProcessor ? ((OrderlyProcessor) processor).getPartitionValue(record) : null;
            String queueId = queueReBalance.select(partitionValue, queueIdList);
            RecordQueue queue = queueManager.getQueue(queueId);
            queue.add(record);
        }
    }

}
