package com.xiazki.kafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;

public class RecordData<K, V> {

    List<ConsumerRecord<K, V>> records;

    int size;


    public List<ConsumerRecord<K, V>> getRecords() {
        return records;
    }

    public void setRecords(List<ConsumerRecord<K, V>> records) {
        this.records = records;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
