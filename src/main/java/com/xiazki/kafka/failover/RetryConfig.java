package com.xiazki.kafka.failover;

public class RetryConfig {

    /**
     * 重试策略
     */
    private RetryPolicy retryPolicy = new DefaultRetryPolicy();

    /**
     * 恢复策略
     */
    private RecoverCallback recoverCallback;

    /**
     * 失败等待的时间间隔
     */
    private long failWaitInterval = 50;

    public RetryPolicy getRetryPolicy() {
        return retryPolicy;
    }

    public void setRetryPolicy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
    }

    public RecoverCallback getRecoverCallback() {
        return recoverCallback;
    }

    public void setRecoverCallback(RecoverCallback recoverCallback) {
        this.recoverCallback = recoverCallback;
    }

    public long getFailWaitInterval() {
        return failWaitInterval;
    }

    public void setFailWaitInterval(long failWaitInterval) {
        this.failWaitInterval = failWaitInterval;
    }
}
