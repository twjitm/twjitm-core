package com.twjitm.kafka.worker;


import io.netty.channel.group.ChannelGroup;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * Created by Marvin on 2017/9/23.
 */
public class PushWorker implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(PushWorker.class);
    SchedulerFactory sf = null;
    Scheduler sched = null;
    private ConsumerRecord<String, String> consumerRecord;

    public PushWorker(ConsumerRecord<String, String> record) {
        this.consumerRecord = record;
    }

    @Override
    public void run() {
        try {
            Thread.currentThread().setName("PushWorker");
            String[] valueArray = consumerRecord.value().split(":");
            String key = valueArray[0];
            LOGGER.info("PushWorker key={},valueArray={}", key, consumerRecord.value());
            if (RedisUserKey.Barage_Key.equals(key)) {
                BarageOnOffIndicationProto.BarageOnOffIndication.Builder barageOnOffIndication
                        = BarageOnOffIndicationProto.BarageOnOffIndication.newBuilder();
                barageOnOffIndication.setPlotId(valueArray[2]);
                barageOnOffIndication.setState(Integer.parseInt(valueArray[1]));
                ChannelGroup allChannels = OnlineUserLogics.allChannels();
                MessageUtils.sendBarageOnOffIndication(allChannels, barageOnOffIndication.build());
            } else if (RedisUserKey.Activity_Notify.equals(key)) {
                List<DemonWeakIndicationProto.DemonWeakIndication.DemonWeakMsg> demonWeakMsgs = new ArrayList<>();
                String[] activitNotifyMsgs = valueArray[2].split("\\|");
                for (String messgae : activitNotifyMsgs) {
                    String[] split = messgae.split("-");
                    int activityId = Integer.parseInt(split[0]);
                    int duration = Integer.parseInt(split[1]);
                    DemonWeakIndicationProto.DemonWeakIndication.DemonWeakMsg demonWeakMsg = DemonWeakIndicationProto.DemonWeakIndication.DemonWeakMsg.newBuilder()
                            .setId(activityId)
                            .setDuration(duration)
                            .build();
                    demonWeakMsgs.add(demonWeakMsg);
                }
                DemonWeakIndicationProto.DemonWeakIndication demonWeakIndication = DemonWeakIndicationProto.DemonWeakIndication.newBuilder()
                        .addAllDemonWeakMsgList(demonWeakMsgs)
                        .build();
                ChannelGroup allChannels = OnlineUserLogics.allChannels();
                MessageUtils.sendDemonWeakIndication(allChannels, demonWeakIndication);
            } else if (RedisUserKey.SendAllSVR_key.equals(key)) {
                //全服邮件刷新
                SysMailLogic.loadSysMail(true);
                RedPointIndicationIndicationProto.RedPointIndication.RedPointTypeAndIds heroArmyEquipId =
                        RedPointIndicationIndicationProto.RedPointIndication.RedPointTypeAndIds
                                .newBuilder()
                                .setType(13)
                                .build();
                RedPointIndicationIndicationProto.RedPointIndication.Builder redPointIndication =
                        RedPointIndicationIndicationProto.RedPointIndication.newBuilder();
                redPointIndication.addRedPointTypeAndIds(heroArmyEquipId);
                ChannelGroup allChannels = OnlineUserLogics.allChannels();
                MessageUtils.sendAllMailRedPointIndication(allChannels, redPointIndication.build());
            } else if (RedisUserKey.Clight_Key.equals(key)) {
                ChatIndicationProto.ChatIndication chatIndication = getChatIndicationProto(Integer.parseInt(valueArray[1]), valueArray[2]);
                try {
                    sendLight(valueArray[2], chatIndication);
                } catch (Exception e) {
                    LOGGER.info("run->msg={}", e.getMessage(), e);
                }
            } else if (RedisUserKey.Clear_Chat_Key.equals(key)) {
                //1:好友聊天 2:世界频道 3: 工会聊天
                int type = Integer.parseInt(valueArray[1]);
                ChatClearIndicationProto.ChatClearIndication chatClearIndication;
                ChannelGroup allChannels = OnlineUserLogics.allChannels();
                switch (type) {
                    case 1:
                        FriendLogic.getInstance().clearSenAndRecvChat(Integer.parseInt(valueArray[2]));
                        chatClearIndication = ChatClearIndicationProto.ChatClearIndication.newBuilder()
                                .setUid(Integer.parseInt(valueArray[2]))
                                .setType(1)
                                .build();
                        MessageUtils.sendChatClearIndication(allChannels, chatClearIndication);
                        break;
                    case 2:
                        chatClearIndication = ChatClearIndicationProto.ChatClearIndication.newBuilder()
                                .setUid(Integer.parseInt(valueArray[2]))
                                .setType(2)
                                .build();
                        MessageUtils.sendChatClearIndication(allChannels, chatClearIndication);
                        break;
                    case 3:
                        chatClearIndication = ChatClearIndicationProto.ChatClearIndication.newBuilder()
                                .setUid(Integer.parseInt(valueArray[2]))
                                .setType(3)
                                .build();
                        MessageUtils.sendChatClearIndication(allChannels, chatClearIndication);
                        break;
                    default:
                }
            } else if (RedisUserKey.CFamily_Info_Key.equals(key)) {
                int familyId = Integer.parseInt(valueArray[1]);
                int changeType = Integer.parseInt(valueArray[2]);
                String changeContent = valueArray[3];
                FamilyLogic.getInstance().updateFamilyName(familyId,changeType,changeContent);
            } else if (consumerRecord.key().equals(MyKafkaConstance.FRIEND_MSG)) {
                valueArray = consumerRecord.value().split("\\|");
                int sendId = Integer.parseInt(valueArray[1]);
                boolean online = OnlineUserLogics.isOnline(sendId);
                LOGGER.info("GameServer FRIEND_MSG PushWorker uid=>{},isOnline=>{}", sendId, online);
                if (online) {
                    int msgTyep = Integer.parseInt(valueArray[0]);
                    if (msgTyep == GlobalsDef.APPLY_FRIEND) {
                        FriendAddInviteIndicationProto.FriendAddInviteIndication.Builder builder = FriendAddInviteIndicationProto.FriendAddInviteIndication.newBuilder();
                        JsonFormat.merge(valueArray[2], builder);
                        FriendAddInviteIndicationProto.FriendAddInviteIndication friendAddInviteIndication = builder.build();
                        MessageUtils.sendFriendAddInviteIndication(sendId, friendAddInviteIndication);
                        LOGGER.info("FRIEND_MSG PushWorker uid=>{},success", sendId);
                    }
                }
            } else if (consumerRecord.key().equals(MyKafkaConstance.FAMILY_MSG)) {
                valueArray = consumerRecord.value().split("\\|");
                int sendId = Integer.parseInt(valueArray[1]);
                boolean online = OnlineUserLogics.isOnline(sendId);
                if (online) {
                    int msgTyep = Integer.parseInt(valueArray[0]);
                    if (msgTyep == GlobalsDef.FAMILY_AGREE) {
                        FamilyAgreedIndicationProto.FamilyAgreedIndication.Builder builder = FamilyAgreedIndicationProto.FamilyAgreedIndication.newBuilder();
                        JsonFormat.merge(valueArray[2], builder);
                        FamilyAgreedIndicationProto.FamilyAgreedIndication familyAgreedIndication = builder.build();
                        MessageUtils.sendFamilyAgreedIndication(sendId, familyAgreedIndication);
                    } else if (msgTyep == GlobalsDef.FAMILY_RED) {
                        FamilyHandOutRedIndicationProto.FamilyHandOutRedIndication.Builder builder =
                                FamilyHandOutRedIndicationProto.FamilyHandOutRedIndication.newBuilder();
                        JsonFormat.merge(valueArray[2], builder);
                        FamilyHandOutRedIndicationProto.FamilyHandOutRedIndication familyHandOutRedIndication = builder.build();
                        MessageUtils.sendFamilyHandOutRedIndication(sendId, familyHandOutRedIndication);
                    } else if (msgTyep == GlobalsDef.FAMILY_OFFICE) {
                        FamilyNoticeIndicationProto.FamilyTenureIndication.Builder builder = FamilyNoticeIndicationProto.FamilyTenureIndication
                                .newBuilder();
                        JsonFormat.merge(valueArray[2], builder);
                        FamilyNoticeIndicationProto.FamilyTenureIndication familyHandOutRedIndication = builder.build();
                        MessageUtils.familyNoticeIndication(sendId, familyHandOutRedIndication);
                    } else if (msgTyep == GlobalsDef.FAMILY_KICK_OUT) {
                        FamilyAgreedIndicationProto.FamilyAgreedIndication.Builder builder = FamilyAgreedIndicationProto.FamilyAgreedIndication.newBuilder();
                        JsonFormat.merge(valueArray[2], builder);
                        FamilyAgreedIndicationProto.FamilyAgreedIndication familyAgreedIndication = builder.build();
                        MessageUtils.sendFamilyAgreedIndication(sendId, familyAgreedIndication);
                    } else if (msgTyep == GlobalsDef.FAMILY_UPDATE_JOIN_TYPE) {
                        FamilyUpdateJoinTypeIndicationProto.FamilyUpdateJoinTypeIndication.Builder builder = FamilyUpdateJoinTypeIndicationProto.FamilyUpdateJoinTypeIndication.newBuilder();
                        JsonFormat.merge(valueArray[2], builder);
                        FamilyUpdateJoinTypeIndicationProto.FamilyUpdateJoinTypeIndication familyUpdateJoinTypeIndication = builder.build();
                        MessageUtils.updateFamilyJoinTypeIndication(sendId, familyUpdateJoinTypeIndication);
                    } else if (msgTyep == GlobalsDef.FAMILY_UPDATE_NOTICE) {
                        FamilyNoticeUpdateIndicationProto.FamilyNoticeUpdateIndication.Builder builder = FamilyNoticeUpdateIndicationProto.FamilyNoticeUpdateIndication.newBuilder();
                        JsonFormat.merge(valueArray[2], builder);
                        FamilyNoticeUpdateIndicationProto.FamilyNoticeUpdateIndication familyNoticeUpdateIndication = builder.build();
                        MessageUtils.sendFamilyNoticeIndication(sendId, familyNoticeUpdateIndication);
                    } else if (msgTyep == GlobalsDef.FAMILY_UPDATE_NAME) {
                        FamilyUpdateNameIconIndicationProto.FamilyUpdateNameIconIndication.Builder builder = FamilyUpdateNameIconIndicationProto.FamilyUpdateNameIconIndication.newBuilder();
                        JsonFormat.merge(valueArray[2], builder);
                        FamilyUpdateNameIconIndicationProto.FamilyUpdateNameIconIndication familyUpdateNameIconIndication = builder.build();
                        MessageUtils.updateFamilyNameIconIndication(sendId, familyUpdateNameIconIndication);
                    } else if (msgTyep == GlobalsDef.FAMILY_OPEN_BOSS) {
                        FamilyOpenBossIndicationProto.FamilyOpenBossIndication.Builder builder = FamilyOpenBossIndicationProto.FamilyOpenBossIndication.newBuilder();
                        JsonFormat.merge(valueArray[2], builder);
                        FamilyOpenBossIndicationProto.FamilyOpenBossIndication familyOpenBossIndication = builder.build();
                        MessageUtils.sendFamilyOpenBossIndication(sendId, familyOpenBossIndication);
                    } else if (msgTyep == GlobalsDef.FAMILY_TRANSFER) {
                        FamilyNoticeIndicationProto.FamilyTenureIndication.Builder builder = FamilyNoticeIndicationProto.FamilyTenureIndication.newBuilder();
                        JsonFormat.merge(valueArray[2], builder);
                        FamilyNoticeIndicationProto.FamilyTenureIndication familyTenureIndication = builder.build();
                        MessageUtils.familyNoticeIndication(sendId, familyTenureIndication);
                    }
                }
            }else if (consumerRecord.key().equals(MyKafkaConstance.TEAM_ATTACK_MSG)) {
                valueArray = consumerRecord.value().split("\\|");
                int sendId = Integer.parseInt(valueArray[1]);
                boolean online = OnlineUserLogics.isOnline(sendId);
                if (online) {
                    int msgTyep = Integer.parseInt(valueArray[0]);
                    if(msgTyep == GlobalsDef.TEAM_ATTACK_JOIN_OR_OUT){
                        TeamAttacJoinOrOutIndicationProto.TeamAttacJoinOrOutIndication.Builder builder = TeamAttacJoinOrOutIndicationProto.TeamAttacJoinOrOutIndication.newBuilder();
                        JsonFormat.merge(valueArray[2],builder);
                        TeamAttacJoinOrOutIndicationProto.TeamAttacJoinOrOutIndication build = builder.build();
                        MessageUtils.teamAttackJoinOrOutNoticeIndication(sendId, build);
                    }
                    if(msgTyep == GlobalsDef.TEAM_ATTACK_INVITE){
                        TeamAttackInviteIndicationProto.TeamAttackInviteIndication.Builder builder = TeamAttackInviteIndicationProto.TeamAttackInviteIndication.newBuilder();
                        JsonFormat.merge(valueArray[2],builder);
                        TeamAttackInviteIndicationProto.TeamAttackInviteIndication build = builder.build();
                        MessageUtils.teamAttackInviteIndication(sendId, build);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("run->msg={}", e.getMessage(), e);
        }
    }

    public void sendLight(String lightInfo, ChatIndicationProto.ChatIndication chatIndication) throws Exception {
        String[] split = lightInfo.split("\\|");
        sf = new StdSchedulerFactory();
        sched = sf.getScheduler();
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("lightInfo", chatIndication);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = Long.parseLong(split[0]) * 1000;
        long currentTiem = System.currentTimeMillis() / 1000;
        if (time >= currentTiem) {
            String d = format.format(time);
            Date date = format.parse(d);
            String cron = CronDateUtils.getCron(date);
            JobDetail sendLightJob = newJob(SendLightJob.class).withIdentity("SendLightJob" + currentTiem, "group7")
                    .usingJobData(jobDataMap).build();
            CronTrigger sendLightTrigger = newTrigger().withIdentity("sendLightTrigger" + currentTiem, "group7").withSchedule(cronSchedule(cron)).build();
            Date date1 = sched.scheduleJob(sendLightJob, sendLightTrigger);
            LOGGER.info("sendLight->date1={}", date1);
        }

    }

    private ChatIndicationProto.ChatIndication getChatIndicationProto(int serverId, String valueArray) {
        String[] split = valueArray.split("\\|");
        ChatIndicationProto.ChatIndication chatIndication = ChatIndicationProto.ChatIndication.newBuilder()
                .setSenderId(1001)
                .setSenderServerId(serverId)
                .setSenderName("IDIP")
                .setSenderlevel(100)
                .setSenderimg("i_h3_41")
                .setSendervip(10)
                .setTimes(Integer.parseInt(split[0]))
                .setMsgType(0)
                .setMsg(split[2])
                .setIsFamilySystem(2)
                .setEndTime(Integer.parseInt(split[1]))
                .setPriorityLevel(Integer.parseInt(split[3]))
                .setFrequency(Integer.parseInt(split[4]))
                .setIsSystem(Integer.parseInt(split[6]))
                .setClientVersion(split[7])
                .build();
        return chatIndication;
    }
}
