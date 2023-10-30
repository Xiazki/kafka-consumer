package com.xiazki.kafka.failover;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;

public class RetryContext<K, V> {

    private List<ConsumerRecord<K, V>> recordList;

    private Throwable throwable;

    private int retryCount;

    private long retryTime;


    public List<ConsumerRecord<K, V>> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<ConsumerRecord<K, V>> recordList) {
        this.recordList = recordList;
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
