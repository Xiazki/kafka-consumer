package com.xiazki.kafka.service;

import com.xiazki.kafka.executor.MessageExecutorService;
import com.xiazki.kafka.executor.Worker;
import com.xiazki.kafka.util.TimeWaiter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OffsetService extends Thread {

    private MessageExecutorService messageExecutorService;

    private long ackOffset;

    private volatile boolean running;

    private KafkaConsumer<?, ?> consumer;

    Map<TopicPartition, Long> ackOffsetMap = new HashMap<>();

    Map<TopicPartition, AckData> notAckOffsetMap = new HashMap<>();

    public OffsetService(MessageExecutorService messageExecutorService, KafkaConsumer<?, ?> consumer) {
        this.messageExecutorService = messageExecutorService;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        while (running) {
            try {
                List<Worker> allWorkers = messageExecutorService.getAllWorkers();
                if (allWorkers == null) {
                    new TimeWaiter().waitTime(100);
                    continue;
                }
                List<Map<TopicPartition, ConsumerRecord<?, ?>>> processingList = new ArrayList<>();
                List<Map<TopicPartition, ConsumerRecord<?, ?>>> leastList = new ArrayList<>();
                for (Worker worker : allWorkers) {
                    Map<TopicPartition, ConsumerRecord<?, ?>> processingRecordMap = worker.getProcessingRecordMap();
                    if (processingRecordMap != null) {
                        processingList.add(processingRecordMap);
                    }
                    Map<TopicPartition, ConsumerRecord<?, ?>> leastRecordMap = worker.getLeastRecordMap();
                    if (leastRecordMap != null) {
                        leastList.add(leastRecordMap);
                    }
                }

            } catch (Throwable throwable) {

            }
        }
    }

    public Map<TopicPartition, Long> minOffset(List<Map<TopicPartition, ConsumerRecord<?, ?>>> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        Map<TopicPartition, Long> minOffsetMap = new HashMap<>();
        for (Map<TopicPartition, ConsumerRecord<?, ?>> map : list) {
            map.forEach((topicPartition, record) -> {
                Long offset = minOffsetMap.get(topicPartition);
                AckData ackData = notAckOffsetMap.get(topicPartition);

                if (ackData == null || record.offset() <= offset) {
                    minOffsetMap.put(topicPartition, offset);
                }
            });
        }
        return minOffsetMap;
    }


    private void commit(Map<TopicPartition, OffsetAndMetadata> offsets) {
        consumer.commitSync(offsets);
    }


    public void destroy() {
        running = false;
    }

}
