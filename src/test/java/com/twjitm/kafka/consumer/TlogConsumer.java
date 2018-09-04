package com.twjitm.kafka.consumer;

import com.jmfy.xianxia.kafka.KafkaConsumerHandler;
import com.jmfy.xianxia.kafka.MyKafkaProperties;
import com.jmfy.xianxia.server.ServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Marvin on 2017/9/23.
 */
@Component
public class TlogConsumer extends MyConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlogConsumer.class);

    private static final int workerNum = 4;

    @Override
    public void start(final MyKafkaProperties myKafkaProperties,ServerProperties serverProperties) throws Exception {

        String tlogTopic = "msg_" + serverProperties.getId();
        List<String> topicList = new ArrayList<>();
        topicList.add(tlogTopic);
        final List<String> finalTopicList = topicList;

        Thread tlogConsumerThread = new Thread(
                new Runnable() {
                    KafkaConsumerHandler kafkaPushMsgConsumer = new KafkaConsumerHandler(myKafkaProperties,finalTopicList);
                    @Override
                    public void run() {
                        try {
                        	  Thread.currentThread().setName("TlogConsumer");
                            kafkaPushMsgConsumer.execute(workerNum);
                        } catch (Exception e) {
                            LOGGER.error("run->msg={}", e.getMessage(), e);
                        }
                    }
                }
        );

        tlogConsumerThread.start();
        LOGGER.info("start->TlogConsumer start");

    }
}
