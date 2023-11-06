package com.xiazki.kafka.rebalance;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SequenceQueueReBalance implements QueueReBalance {

    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public String select(String partitionValue, List<String> queueIdList) {
        return queueIdList.get(counter.incrementAndGet() % queueIdList.size());
    }
}
