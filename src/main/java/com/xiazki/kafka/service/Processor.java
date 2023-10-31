package com.xiazki.kafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;

public interface Processor {
    /**
     * 处理一批数据，如果实现getPartitionValue，则一批数据都是按照该分区值聚合的结果
     * <p>
     * 数量为Config#processBatchSize配置大小。
     *
     * @param record 消息数量
     */
    <K, V> void process(RecordData<K, V> record);

}
