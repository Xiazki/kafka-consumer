package com.xiazki.kafka.failover;

import com.xiazki.kafka.service.RecordData;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;

public class RetryContext<K, V> {

    private RecordData<K, V> record;

    private Throwable throwable;

    private int retryCount;

    private long retryTime;

    public RecordData<K, V> getRecord() {
        return record;
    }

    public void setRecord(RecordData<K, V> record) {
        this.record = record;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public long getRetryTime() {
        return retryTime;
    }

    public void setRetryTime(long retryTime) {
        this.retryTime = retryTime;
    }
}
