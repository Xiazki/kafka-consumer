package com.xiazki.kafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface OrderlyProcessor extends Processor {

    /**
     * 用于分区路由的值
     *
     * @param record 消息
     * @return 分区值
     */
    <K, V> String getPartitionValue(ConsumerRecord<K, V> record);
}
