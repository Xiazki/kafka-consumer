package com.xiazki.kafka.queue;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QueueManager {

    private QueueSizeCoordinator queueSizeCoordinator;

    private int queueNum = 10;

    private int queueSize = 1024;

    Map<String, RecordQueue> recordQueueMap = new ConcurrentHashMap<>();

    List<String> ququeIdList;

    public QueueManager(int queueNum, int queueSize) {
        if (queueNum != 0) {
            this.queueNum = queueNum;
        }
        if (queueSize != 0) {
            this.queueSize = queueSize;
        }

        this.ququeIdList = IntStream.range(0, this.queueNum).mapToObj(operand -> "work-queue-" + operand).collect(Collectors.toList());
    }

    /**
     * 获取队列id列表
     *
     * @return
     */
    public List<String> getQueueIdList() {
        return ququeIdList;
    }

    /**
     * 获取队列
     *
     * @param queueId 队列id
     * @return 队列
     */
    public RecordQueue getQueue(String queueId) {
        return recordQueueMap.computeIfAbsent(queueId, s -> new RecordQueue(queueId));
    }


    public QueueSizeCoordinator getQueueSizeCoordinator() {
        return queueSizeCoordinator;
    }

    /**
     * 销毁
     */
    public void destroy() {
        ququeIdList.clear();
        recordQueueMap.forEach((s, recordQueue) -> recordQueue.clear());
        recordQueueMap.clear();
    }
}
