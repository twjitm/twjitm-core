package com.twjitm.kafka.consumer;


import com.twjitm.kafka.KafkaConsumerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Marvin on 2017/9/23.
 */
@Component
public class PushConsumer extends MyConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PushConsumer.class);

    private static final int workerNum = 4;

    @Override
    public void start(final MyKafkaProperties myKafkaProperties,ServerProperties serverProperties) throws Exception {

        String pushTopic = "msg_" + serverProperties.getId();
        List<String> topicList = new ArrayList<>();
        topicList.add(pushTopic);
        final List<String> finalTopicList = topicList;

        Thread tlogConsumerThread = new Thread(
                new Runnable() {
                    KafkaConsumerHandler kafkaPushMsgConsumer = new KafkaConsumerHandler(myKafkaProperties,finalTopicList);
                    @Override
                    public void run() {
                        try {
                        	  Thread.currentThread().setName("PushConsumer");
                            kafkaPushMsgConsumer.execute(workerNum);
                        } catch (Exception e) {
                            LOGGER.error("run->msg={}", e.getMessage(), e);
                        }
                    }
                }
        );

        tlogConsumerThread.start();
        LOGGER.info("start->PushConsumer start");

    }
}
