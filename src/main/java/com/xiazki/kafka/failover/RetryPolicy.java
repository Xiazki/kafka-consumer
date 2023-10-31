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
     * 获取重试最大时间
     *
     * @return 尝试重试最大时间 毫秒级别
     */
    long getRetryTime();
}
