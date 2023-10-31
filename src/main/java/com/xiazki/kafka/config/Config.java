package com.xiazki.kafka.config;

import com.xiazki.kafka.failover.RetryConfig;

import java.util.Properties;

/**
 * 配置项
 */
public class Config {

    /**
     * kafka属性
     */
    private Properties properties;

    /**
     * 线程数量
     */
    private Integer threadNum = 1;

    /**
     * 最大缓存消息数量
     */
    private Integer maxMessageNum = 4096;

    /**
     * 默认处理的一批处理的数量
     */
    private Integer processBatchSize = 1;

    private RetryConfig retryConfig = new RetryConfig();

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Integer getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(Integer threadNum) {
        this.threadNum = threadNum;
    }

    public Integer getMaxMessageNum() {
        return maxMessageNum;
    }

    public void setMaxMessageNum(Integer maxMessageNum) {
        this.maxMessageNum = maxMessageNum;
    }

    public Integer getProcessBatchSize() {
        return processBatchSize;
    }

    public void setProcessBatchSize(Integer processBatchSize) {
        this.processBatchSize = processBatchSize;
    }

    public RetryConfig getRetryConfig() {
        return retryConfig;
    }

    public void setRetryConfig(RetryConfig retryConfig) {
        this.retryConfig = retryConfig;
    }
}
