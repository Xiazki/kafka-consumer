package com.xiazki.kafka.failover;

/**
 * 默认的重试策略，重试次数大于retryCount或者重试时间大于interval时，不再重试
 */
public class DefaultRetryPolicy implements RetryPolicy {

    private int retryCount = 3;

    private int time = 120000;

    @Override
    public <K, V> boolean canRetry(RetryContext<K, V> retryContext) {
        return retryCount > retryContext.getRetryCount() || retryContext.getRetryTime() < getRetryTime();
    }

    @Override
    public long getRetryTime() {
        return time;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public void setTime(int interval) {
        this.time = interval;
    }
}
