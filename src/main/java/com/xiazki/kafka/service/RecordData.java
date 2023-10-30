package com.xiazki.kafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;

public class RecordData<K, V> {

    List<ConsumerRecord<K, V>> records;

    int size;
}
