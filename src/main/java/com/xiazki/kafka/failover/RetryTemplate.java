package com.xiazki.kafka.failover;

import com.xiazki.kafka.service.Processor;

public class RetryTemplate {

    private RetryPolicy retryPolicy;

    private RecoverCallback recoverCallback;


    public <K, V> void execute(Processor processor) {
        try {

        } catch (Throwable throwable) {

        }
    }

}
