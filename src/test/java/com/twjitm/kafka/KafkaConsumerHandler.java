
package com.twjitm.kafka;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.config.SaslConfigs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by Marvin on 2017/9/15.
 */

public class KafkaConsumerHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerHandler.class);

    private KafkaConsumer<String, String> consumer;

    private ExecutorService executors;

    private List<String> topicList;

    private static class DelayMessage {
        public String topKey;
        public String topValue;
        public long firstDealTime;
    }

    private ArrayList<DelayMessage> delayMessages = new ArrayList();
    private int curDelayMessageIndex = -1;


    public KafkaConsumerHandler(MyKafkaProperties myKafkaProperties, List<String> topicList) {
        Properties props = new Properties();
        props.put("bootstrap.servers", myKafkaProperties.getServersUri());
        //消费者的组id
        props.put("group.id", myKafkaProperties.getConsumerGroupId() + "_" + Application.coreIp);
        props.put("enable.auto.commit", myKafkaProperties.isConsumerEnableAutoCommit());
        props.put("auto.commit.interval.ms", myKafkaProperties.getConsumerAutoCommitInterval());
        props.put("session.timeout.ms", myKafkaProperties.getConsumerSessionTimeout());
        //poll的数量限制
//        props.put("max.poll.records", myKafkaProperties.getConsumer_maxPollRecords());
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

//        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, myKafkaProperties.getSecurityProtocol());
//        props.put(SaslConfigs.SASL_MECHANISM, myKafkaProperties.getSaslMechanism());
//        props.put(SaslConfigs.SASL_JAAS_CONFIG, myKafkaProperties.getSaslJaasConfig());

        this.topicList = topicList;

        consumer = new KafkaConsumer<String, String>(props);
        //订阅主题列表topic
        consumer.subscribe(topicList);

//        LOGGER.info("Consumer Properties:--->"+props.toString());

    }


    public void execute(int workerNum) {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("KafkaConsumerHandler-%d").build();
        executors = new ThreadPoolExecutor(workerNum,
                workerNum, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(10000),
                threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());


        while (true) {
            if (delayMessages.size() != 0) {
                for (int i = 0; i < delayMessages.size(); i++) {
                    DelayMessage delayMessage = delayMessages.get(i);
                    String topValue = delayMessage.topValue;
                    String[] split = topValue.split(":");
                    if (System.currentTimeMillis() / 1000 > (delayMessage.firstDealTime + Integer.parseInt(split[2]))) {
                        curDelayMessageIndex = i;
                        kickOldUser(Integer.parseInt(split[1]), Integer.parseInt(split[3]));
                        delayMessages.remove(curDelayMessageIndex);
                    }
                }
            }
            ConsumerRecords<String, String> records = consumer.poll(200);
            if (records.isEmpty()) {
                continue;
            }
            for (final ConsumerRecord<String, String> record : records) {
                if (!record.value().isEmpty()) {
                    Runnable worker = getWorkerByTopic(record);
                    if (worker != null) {
                        String[] split = record.value().split(":");
                        if (RedisUserKey.Kick_Old_Key.equals(split[0])) {
                            DelayMessage delayMessage = new DelayMessage();
                            delayMessage.topKey = split[0];
                            delayMessage.topValue = record.value();
                            delayMessage.firstDealTime = System.currentTimeMillis() / 1000;
                            delayMessages.add(delayMessage);
                        }
                        executors.submit(worker);
                    }
                }
            }
        }
    }

    private void kickOldUser(int uid, int type) {
        //type 0:全服玩家踢下线 1:单服玩家踢下线
        LOGGER.info("idipKickOldUser-->uid={},type={}", uid, type);
        if (type == 0) {
            kickOldAllUser();
        } else {
            PlayerLogic.getInstance().kickOldOneUser(uid);
        }
    }

    private void kickOldAllUser() {
        Set<Integer> onlineUserId = OnlineUserLogics.getOnlineIdSet();
        LOGGER.info("kickOldAllUser-->onlineNum={}", onlineUserId.size());
        for (Integer userId : onlineUserId) {
            if (OnlineUserLogics.isOnline(userId)) {
                ProtocolsManager.getInstance().kickOldUser(userId, "",
                        ErrorCode.IDIP_KIC_OLD, "IDIP_KIC_OLD!!!", 0);
                //清除缓存
                ProtocolsManager.getInstance().cleanResponseCache(userId, true);
                RedisCache.cleanCache(userId);
            }
        }
    }

    private Runnable getWorkerByTopic(ConsumerRecord<String, String> record) {
        if (record.key().startsWith("tlog")) {
            return new TLogWorker(record);
        }
        if (record.key().startsWith("push")) {
            return new PushWorker(record);
        }
//        else if (topic.startsWith("")){
//            return null;
//        }

        return null;
    }


    public void shutdown() {
        if (consumer != null) {
            consumer.close();
        }
        if (executors != null) {
            executors.shutdown();
            try {
                if (!executors.awaitTermination(10, TimeUnit.SECONDS)) {
                    LOGGER.info("shutdown->;Timeout.... Ignore for this case");
                }
            } catch (InterruptedException ignored) {
                LOGGER.error("shutdown->;Other thread interrupted this shutdown, ignore for this case.");
                Thread.currentThread().interrupt();
            }
        }
    }

}
