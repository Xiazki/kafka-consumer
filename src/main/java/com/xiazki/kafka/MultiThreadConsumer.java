package com.xiazki.kafka;

import com.xiazki.kafka.config.Config;
import com.xiazki.kafka.dispatcher.RecordDispatcher;
import com.xiazki.kafka.executor.MessageExecutorService;
import com.xiazki.kafka.queue.QueueManager;
import com.xiazki.kafka.service.OffsetService;
import com.xiazki.kafka.service.OrderlyProcessor;
import com.xiazki.kafka.service.Processor;
import com.xiazki.kafka.service.PullService;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.List;

public class MultiThreadConsumer<K, V> {

    private Processor processor;

    private KafkaConsumer<K, V> consumer;

    private PullService<K, V> pullService;

    private QueueManager queueManager;

    private MessageExecutorService messageExecutorService;

    private RecordDispatcher<K, V> recordDispatcher;

    private OffsetService offsetService;


    /**
     * @param config
     * @param processor
     */
    public MultiThreadConsumer(Config config, Processor processor) {
        //初始化kafka consumer
        consumer = new KafkaConsumer<>(config.getProperties());
        //初始化队列
        queueManager = new QueueManager(config.getThreadNum(), config.getMaxMessageNum());
        //初始化分发器
        recordDispatcher = new RecordDispatcher<>(queueManager, processor instanceof OrderlyProcessor);
        //初始化拉服务
        pullService = new PullService<>(consumer, recordDispatcher);
        //初始化执行线程池
        messageExecutorService = new MessageExecutorService(queueManager, processor, config.getRetryConfig(), config.getProcessBatchSize());
        //初始化位点管理服务
        offsetService = new OffsetService(messageExecutorService);
    }

    public KafkaConsumer<K, V> getConsumer() {
        return consumer;
    }

    public synchronized void start(List<String> topics) {
        try {
            consumer.subscribe(topics);
            pullService.start();
            messageExecutorService.start();
        } catch (Throwable throwable) {
            destory();
        }
    }

    public synchronized void destory() {

        try {
            pullService.destory();
            offsetService.destory();
            queueManager.destory();
            messageExecutorService.destory();
        } catch (Throwable e) {

        } finally {
            consumer.close();
            pullService = null;
            offsetService = null;
            queueManager = null;
            messageExecutorService = null;
        }
    }


}
