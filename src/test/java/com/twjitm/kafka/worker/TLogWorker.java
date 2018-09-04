package com.twjitm.kafka.worker;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * Created by Marvin on 2017/9/23.
 */
public class TLogWorker implements Runnable{

    private static final Logger LOGGER = LoggerFactory.getLogger(TLogWorker.class);

    private ConsumerRecord<String, String> consumerRecord;

    public TLogWorker(ConsumerRecord<String, String> record) {
        this.consumerRecord = record;
    }

    @Override
    public void run() {
        try{
            if (consumerRecord.key().equals(MyKafkaConstance.TLOG_MSG)){
                String msg = consumerRecord.value()+"\n";
                try {
                    UDPClient.getInstance().writebytes(msg.getBytes("UTF-8"));
                    LOGGER.info("run->value={}", consumerRecord.value());
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("run->msg={}", e.getMessage(), e);
                }
            }
        }catch (Exception e){
            LOGGER.error("run->msg={}", e.getMessage(), e);
        }

    }

}
