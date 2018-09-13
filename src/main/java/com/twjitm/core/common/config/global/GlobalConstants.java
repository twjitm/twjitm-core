package com.twjitm.core.common.config.global;

import java.util.concurrent.TimeUnit;

/**
 * Created by 文江 on 2017/11/25.
 */
public class GlobalConstants {


    public static class ConfigFile {
        /**
         * udp服务配置
         */
        public static final String UDP_SERVER_CONFIG_FILE_PATH = "bean/netty-udp-config.xml";
        public static final String RPC_SERVER_CONFIG_FILE_PATH = "bean/netty-rpc-config.xml";
        /**
         * http服务器配置文件
         */
        public static String HTTP_SERVER_CONFIG = "bean/netty-http-config.xml";

        public static String NETTY_FILE_EXT = ".class";
        public static final String GAME_SERVER_RPROERTIES_FILE_PATH = "bean/game-properties.properties";
        /**
         * rpc
         */
        public static final String RPC_SERVER_REGISTER_CONFIG = "bean/rpc-server-register.xml";
        /**
         * rpc service
         */
        public static final String RPC_SERVICE_CONFIG = "bean/rpc-service-register.xml";
        /**
         * zookeeper
         */
        public static final String ZOOKEEPER_PROPERTIES_FILE_PATH = "bean/game-zookeeper.properties";
        /**
         * kafka线程配置文件
         */
        public static  final String KAFKA_PROPERTIES_FILE_PATH="bean/game-kafka.properties";

    }

    public static class Thread {
        public static final String MESSAGE_QUEUE_EXECUTOR = "message_queue_executor";

        public static final String GAME_MESSAGE_QUEUE_EXECUTOR = "game_message_queue_executor";

        public static final String RPC_HANDLER = "game_message_rpc_handler";
        /**
         * rpc代理消息执行线程
         */
        public static final String RPC_PROXY_MESSAGE_EXECUTOR = "rpc_proxy_message_executor";

        public static final String GAME_ASYNC_CALL = "game_async_call";

        public static final String DETECT_RPC_PEND_ING = "detect_rpc_pend_ing";

        public static final String MESSAGE_UDP_ORDER_EXECUTOR = "message_udp_order_executor";

        public static final String POLL_LIFE_CYCLE = "poll_life_cycle";

        /**
         * kafka消息消费者处理线程
         */
        public static final String GAME_KAFKA_TASK_EXECUTOR = "game_kafka_task_executor";

    }

    public static class UDPServiceConfig {
        public static final boolean IS_UDP_MESSAGE_ORDER_QUEUE_FLAG = false;

    }

    public static class GameServiceRuntime {
        public static final boolean IS_OPEN = true;
    }

    public class NettyNet {
        /**
         * 心跳间隔
         */
        public static final int HEART_BASE_SIZE = 1;
        /**
         * 心跳写时间超时(单位秒)
         */
        public static final int SESSION_HEART_WRITE_TIMEOUT = HEART_BASE_SIZE * 60;
        /**
         * 心跳写时间超时(单位秒)
         */
        public static final int SESSION_HEART_READ_TIMEOUT = HEART_BASE_SIZE * 60;
        /**
         * 心跳读写时间超时(单位秒)
         */
        public static final int SESSION_HEART_ALL_TIMEOUT = HEART_BASE_SIZE * 60;

        /**
         * 心跳檢測次數
         */
        public static final int SESSION_HEART_CHECK_NUMBER = HEART_BASE_SIZE * 5;

        public static final int NETTY_NET_HTTP_MESSAGE_THREAD_CORE_NUMBER = 5;

    }

    public static class NettyNetServerConfig {
        public static class TCP {
            public static final String BOSS_THREAD_NAME = "tcp_boss_thread_name_";
            public static final String WORKER_THREAD_NAME = "tcp_worker_thread_name_";
            public static final String SERVER_NAME = "TCP_GAME_SERVER";
        }

        public static class UDP {
            public static final String EVENT_THREAD_NAME = "udp_event_thread_name_";
            public static final String SERVER_NAME = "UDP_GAME_SERVER";

        }

        public static class HTTP {
            public static final String BOSS_THREAD_NAME = "http_boss_thread_name_";
            public static final String WORKER_THREAD_NAME = "http_worker_thread_name_";
            public static final String SERVER_NAME = "HTTP_GAME_SERVER";

        }

        public static class RPC {
            public static final String BOSS_THREAD_NAME = "rpc_boss_thread_name_";
            public static final String WORKER_THREAD_NAME = "rpc_worker_thread_name_";
            public static final String SERVER_NAME = "RPC_GAME_SERVER";

        }

    }

    public static class PollTimer {
        public static final int tickDuration = 20;
        public static final TimeUnit timeUnit = TimeUnit.SECONDS;

        public static final int ticksPerPoll = 60;
    }


}
