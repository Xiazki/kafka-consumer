package com.xiazki.kafka.failover;

public interface RetryPolicy {

    /**
     * 是否可以重试
     *
     * @param retryContext 重试上下文
     * @return bool
     */
    <K, V> boolean canRetry(RetryContext<K, V> retryContext);

    /**
     * 获取重试时间间隔
     *
     * @return 重试间隔 毫秒级别
     */
    long getInterval();
}
