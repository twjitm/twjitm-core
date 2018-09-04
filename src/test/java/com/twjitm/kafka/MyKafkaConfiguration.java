package com.twjitm.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Marvin on 2017/9/20.
 */
@Configuration(value = "myKafkaConfiguration")
public class MyKafkaConfiguration {

    @Autowired
    private MyKafkaProperties myKafkaProperties;

    public MyKafkaProperties getMyKafkaProperties() {
        return myKafkaProperties;
    }

    public void setMyKafkaProperties(MyKafkaProperties myKafkaProperties) {
        this.myKafkaProperties = myKafkaProperties;
    }
}
