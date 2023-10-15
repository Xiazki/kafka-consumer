package com.xiazki.kafka.rebalance;

import java.util.List;

public interface QueueReBalance {

    String select(String partitionValue, List<String> queueIdList);

}
