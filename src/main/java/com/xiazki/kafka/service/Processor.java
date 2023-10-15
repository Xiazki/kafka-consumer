package com.xiazki.kafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;

public interface Processor<K, V> {

    /**
     * 处理一批数据，如果实现getPartitionValue，则一批数据都是按照该分区值聚合的结果
     * <p>
     * 数量为Config#processBatchSize配置大小。
     *
     * @param records 消息数量
     */
    void process(List<ConsumerRecord<K, V>> records);
}
