package com.xiazki.kafka.queue;

import java.util.List;

public class QueueManager {

    private QueueSizeCoordinator queueSizeCoordinator;

    public QueueManager(int queueNum, int queueSize) {

    }

    /**
     * 获取队列id列表
     *
     * @return
     */
    public List<String> getQueueIdList() {
        return null;
    }

    /**
     * 获取队列
     *
     * @param queueId 队列id
     * @param <K>     消息key
     * @param <V>     消息value
     * @return 队列
     */
    public <K, V> RecordQueue<K, V> getQueue(String queueId) {
        return null;
    }


    public QueueSizeCoordinator getQueueSizeCoordinator() {
        return queueSizeCoordinator;
    }

    /**
     * 创建队列
     */
    public void createQueue() {

    }

    /**
     * 销毁
     */
    public void destory() {

    }
}
