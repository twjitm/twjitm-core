package com.twjitm.kafka.consumer;


import com.twjitm.kafka.MyKafkaProperties;

/**
 * Created by Marvin on 2017/9/23.
 */
public abstract class MyConsumer {

    public abstract void start(MyKafkaProperties myKafkaProperties,
                               ServerProperties serverProperties)  throws Exception;

}
