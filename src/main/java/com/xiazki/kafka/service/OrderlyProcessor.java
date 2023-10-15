package com.xiazki.kafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface OrderlyProcessor<K, V> extends Processor<K, V> {

    /**
     * 用于分区路由的值
     *
     * @param record 消息
     * @return 分区值
     */
    default String getPartitionValue(ConsumerRecord<K, V> record) {
        return null;
    }
}
