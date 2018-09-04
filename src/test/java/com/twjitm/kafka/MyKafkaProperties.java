package com.twjitm.kafka;


import org.springframework.stereotype.Service;

/**
 * Created by Marvin on 2017/9/20.
 */
@Service
public class MyKafkaProperties {

    private String serversUri = "127.0.0.1:9092";

    private String producerAcks = "all";

    private int producerRetries = 0;

    private int producerBatchSize = 16384;

    private int producerLinger = 1;

    private int producerBufferMemory = 33554432;

    private String consumerGroupId = "ysj";

    private boolean consumerEnableAutoCommit = true;

    private int consumerAutoCommitInterval = 1000;

    private int consumerSessionTimeout = 30000;

    private int consumerMaxPollRecords = 100;

    private String saslMechanism = "PLAIN";

    private String saslJaasConfig = "";

    private String securityProtocol = "SASL_PLAINTEXT";



    public String getServersUri() {
        return serversUri;
    }

    public void setServersUri(String serversUri) {
        this.serversUri = serversUri;
    }

    public String getProducerAcks() {
        return producerAcks;
    }

    public void setProducerAcks(String producerAcks) {
        this.producerAcks = producerAcks;
    }

    public int getProducerRetries() {
        return producerRetries;
    }

    public void setProducerRetries(int producerRetries) {
        this.producerRetries = producerRetries;
    }

    public int getProducerBatchSize() {
        return producerBatchSize;
    }

    public void setProducerBatchSize(int producerBatchSize) {
        this.producerBatchSize = producerBatchSize;
    }

    public int getProducerLinger() {
        return producerLinger;
    }

    public void setProducerLinger(int producerLinger) {
        this.producerLinger = producerLinger;
    }

    public int getProducerBufferMemory() {
        return producerBufferMemory;
    }

    public void setProducerBufferMemory(int producerBufferMemory) {
        this.producerBufferMemory = producerBufferMemory;
    }

    public String getConsumerGroupId() {
        return consumerGroupId;
    }

    public void setConsumerGroupId(String consumerGroupId) {
        this.consumerGroupId = consumerGroupId;
    }

    public boolean isConsumerEnableAutoCommit() {
        return consumerEnableAutoCommit;
    }

    public void setConsumerEnableAutoCommit(boolean consumerEnableAutoCommit) {
        this.consumerEnableAutoCommit = consumerEnableAutoCommit;
    }

    public int getConsumerAutoCommitInterval() {
        return consumerAutoCommitInterval;
    }

    public void setConsumerAutoCommitInterval(int consumerAutoCommitInterval) {
        this.consumerAutoCommitInterval = consumerAutoCommitInterval;
    }

    public int getConsumerSessionTimeout() {
        return consumerSessionTimeout;
    }

    public void setConsumerSessionTimeout(int consumerSessionTimeout) {
        this.consumerSessionTimeout = consumerSessionTimeout;
    }

    public int getConsumerMaxPollRecords() {
        return consumerMaxPollRecords;
    }

    public void setConsumerMaxPollRecords(int consumerMaxPollRecords) {
        this.consumerMaxPollRecords = consumerMaxPollRecords;
    }

    public String getSaslMechanism() {
        return saslMechanism;
    }

    public void setSaslMechanism(String saslMechanism) {
        this.saslMechanism = saslMechanism;
    }

    public String getSaslJaasConfig() {
        return saslJaasConfig;
    }

    public void setSaslJaasConfig(String saslJaasConfig) {
        this.saslJaasConfig = saslJaasConfig;
    }

    public String getSecurityProtocol() {
        return securityProtocol;
    }

    public void setSecurityProtocol(String securityProtocol) {
        this.securityProtocol = securityProtocol;
    }
}
