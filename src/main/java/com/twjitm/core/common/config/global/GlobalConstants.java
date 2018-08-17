package com.twjitm.core.common.config.global;

/**
 * Created by 文江 on 2017/11/25.
 */
public class GlobalConstants {


    public static class ConfigFile {
        //http服务器配置文件
        public static String HTTP_SERVER_CONFIG = "bean\\http_service_config.xml";

        public static String SERVICE_TYPE = "udp";
    }

    public static class Thread {
        public static final String MESSAGE_QUEUE_EXECUTOR = "message_queue_executor";

        public static final String GAME_MESSAGE_QUEUE_EXCUTE = "game_message_queue_excute";
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
        public static final  int SESSION_HEART_CHECK_NUMBER=HEART_BASE_SIZE*5;

        public static final int NETTY_NET_HTTP_MESSAGE_THREAD_CORE_NUMBER = 5;

    }

    public static class NettyNetServerConfig {
        public static class TCP {
            public static final int SERVER_PORT = 9090;
            public static final String SERVER_IP = "127.0.0.1";
            public static final String BOSS_THREAD_NAME="tcp_boss_thread_name";
            public static final String WORKER_THREAD_NAME="tcp_worker_thread_name";

        }
        public static class UDP{
            public static final int SERVER_PORT = 9099;
            public static final String SERVER_IP = "127.0.0.1";
            public static final String EVENT_THREAD_NAME="udp_event_thread_name";
        }
        public static  class  HTTP{
            public static final int SERVER_PORT = 8080;
            public static final String SERVER_IP = "127.0.0.1";
            public static final String BOSS_THREAD_NAME="http_boss_thread_name";
            public static final String WORKER_THREAD_NAME="http_worker_thread_name";
        }
    }


}
