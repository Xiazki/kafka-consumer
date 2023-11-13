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
                List<Map<TopicPartition, Long>> queueList = new ArrayList<>();
                List<Map<TopicPartition, Long>> processingList = new ArrayList<>();
                List<Map<TopicPartition, Long>> leastList = new ArrayList<>();
                for (Worker worker : allWorkers) {
                    Map<TopicPartition, Long> processingRecordMap = worker.getProcessingRecordMap();
                    if (processingRecordMap != null) {
                        processingList.add(processingRecordMap);
                    }
                    Map<TopicPartition, Long> leastRecordMap = worker.getLeastRecordMap();
                    if (leastRecordMap != null) {
                        leastList.add(leastRecordMap);
                    }
                    Map<TopicPartition, Long> queueMinOffsetMap = worker.getQueueMinOffsetMap();
                    if (queueMinOffsetMap != null) {
                        queueList.add(queueMinOffsetMap);
                    }
                }


                consumer.commitAsync();
            } catch (Throwable throwable) {

            }
        }
    }


    public Map<TopicPartition, Long> calcCommitOffset(List<Map<TopicPartition, Long>> queueList, List<Map<TopicPartition, Long>> processingList, List<Map<TopicPartition, Long>> leastList) {
        Map<TopicPartition, Long> queueMinMap = minOffset(queueList);
        Map<TopicPartition, Long> processingMinMap = minOffset(processingList);
        return null;
    }


    public Map<TopicPartition, Long> minOffset(List<Map<TopicPartition, Long>> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        Map<TopicPartition, Long> minOffsetMap = new HashMap<>();
        for (Map<TopicPartition, Long> map : list) {
            map.forEach((topicPartition, record) -> {
                Long offset = minOffsetMap.get(topicPartition);
                AckData ackData = notAckOffsetMap.get(topicPartition);

                if (ackData == null || record <= offset) {
                    minOffsetMap.put(topicPartition, record);
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
