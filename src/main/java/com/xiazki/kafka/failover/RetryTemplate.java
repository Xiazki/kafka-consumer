package com.xiazki.kafka.failover;

import com.xiazki.kafka.service.Processor;
import com.xiazki.kafka.service.RecordData;
import com.xiazki.kafka.util.TimeWaiter;

public class RetryTemplate {

    private RetryPolicy retryPolicy = new DefaultRetryPolicy();

    private RecoverCallback recoverCallback;

    private RetryConfig retryConfig;

    private Processor processor;


    public RetryTemplate(RetryConfig config, Processor processor) {
        this.retryConfig = config;
        this.processor = processor;
        if (config.getRetryPolicy() != null) {
            retryPolicy = config.getRetryPolicy();
        }
        this.recoverCallback = config.getRecoverCallback();
    }

    /**
     * 根据重试策略来执行processor
     *
     * @param record 记录
     * @param <K>    k 类型
     * @param <V>    v 类型
     */
    public <K, V> void execute(RecordData<K, V> record) {
        RetryContext<K, V> retryContext = new RetryContext<>();
        retryContext.setRecord(record);
        retryContext.setRetryTime(0);
        long startTime = System.currentTimeMillis();
        int retryCount = 0;
        boolean success = false;
        TimeWaiter timeWaiter = new TimeWaiter();
        while (retryPolicy.canRetry(retryContext)) {
            long executeStartTime = System.currentTimeMillis();
            try {
                processor.process(record);
                success = true;
            } catch (Throwable throwable) {
                retryContext.setThrowable(throwable);
                retryContext.setRetryCount(++retryCount);
                retryContext.setRetryTime(System.currentTimeMillis() - startTime);
                //如果执行时间过短，休眠一段时间，防止cpu被打满
                if (System.currentTimeMillis() - executeStartTime < 10 && retryConfig.getFailWaitInterval() != 0) {
                    timeWaiter.waitTime(retryConfig.getFailWaitInterval());
                }
            }
            if (success) {
                break;
            }
        }
        if (!success && recoverCallback != null) {
            recoverCallback.recover(retryContext);
        }
    }

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
