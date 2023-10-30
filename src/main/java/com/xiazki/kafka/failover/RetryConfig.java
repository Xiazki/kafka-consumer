package com.xiazki.kafka.failover;

public class RetryConfig {

    private RetryPolicy retryPolicy;

    private RecoverCallback recoverCallback;

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
}
