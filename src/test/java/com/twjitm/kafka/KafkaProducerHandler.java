package com.twjitm.kafka;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.config.SaslConfigs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Created by Marvin on 2017/9/13.
 */
@Component
public class KafkaProducerHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerHandler.class);

    private Producer<String, String> producer;

    private ServerProperties serverProperties;

    private MyKafkaProperties myKafkaProperties;

    public KafkaProducerHandler() {
    }

    private static KafkaProducerHandler kafkaProducerHandler;

    public static KafkaProducerHandler getInstance() {
        if (kafkaProducerHandler == null) {
            kafkaProducerHandler = InnerClass.SINGLETON;
        }
        return kafkaProducerHandler;
    }


    private static class InnerClass {
        private static final KafkaProducerHandler SINGLETON = new KafkaProducerHandler();
    }

    public void init(MyKafkaProperties myKafkaProperties,ServerProperties serverProperties){

        this.myKafkaProperties = myKafkaProperties;
        this.serverProperties = serverProperties;

        Properties properties = new Properties();
        properties.put("bootstrap.servers", myKafkaProperties.getServersUri());
        properties.put("max.block.ms", 1000);
        properties.put("acks", myKafkaProperties.getProducerAcks());
        properties.put("retries",myKafkaProperties.getProducerRetries());
        properties.put("batch.size", myKafkaProperties.getProducerBatchSize());
        properties.put("linger.ms", myKafkaProperties.getProducerLinger());
        properties.put("buffer.memory", myKafkaProperties.getProducerBufferMemory());
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

//        properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, myKafkaProperties.getSecurityProtocol());
//        properties.put(SaslConfigs.SASL_MECHANISM, myKafkaProperties.getSaslMechanism());
//        properties.put(SaslConfigs.SASL_JAAS_CONFIG, myKafkaProperties.getSaslJaasConfig());

        this.producer = new KafkaProducer<>(properties);
    }

    public void sendMessage(String topic, String key, String msgContent){

        ProducerRecord<String, String> msg = new ProducerRecord<String, String>(topic, key, msgContent);

        this.producer.send(msg);

//        LOGGER.info("sent to kafka server...topic: " + topic +" ; msg : "+ msg.toString()+"\n");
    }

    public Producer<String, String> getProducer() {
        return producer;
    }

    public void setProducer(Producer<String, String> producer) {
        this.producer = producer;
    }

    public ServerProperties getServerProperties() {
        return serverProperties;
    }

    public void setServerProperties(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }
}
