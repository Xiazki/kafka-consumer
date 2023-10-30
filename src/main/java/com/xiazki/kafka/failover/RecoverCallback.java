package com.xiazki.kafka.failover;

public interface RecoverCallback {

    /**
     * 回调
     *
     * @param retryContext 重试上下文
     * @param <K>
     * @param <V>
     */
    <K, V> void recover(RetryContext<K, V> retryContext);

}
