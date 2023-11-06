package com.xiazki.kafka.service;

import com.xiazki.kafka.executor.MessageExecutorService;

public class OffsetService {

    private MessageExecutorService messageExecutorService;

    public OffsetService(MessageExecutorService messageExecutorService) {
        this.messageExecutorService = messageExecutorService;
    }

    public void destroy(){

    }

}
