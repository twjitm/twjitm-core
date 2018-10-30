package com.twjitm.core.spring;

import com.twjitm.core.common.config.global.NettyGameServiceConfig;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * spring service manager
 *
 * @author twjtim
 */

public class SpringServiceManager {
    static Logger logger = Logger.getLogger(SpringServiceManager.class);

    public static void init() {
        NettyGameServiceConfig gameServiceConfig=new NettyGameServiceConfig();
        gameServiceConfig.init();
        logger.info("--------------------init spring bootstrap----------------");
        List<String> application = new ArrayList<>();
        application.add("classpath:spring/applicationContext.xml");
        application.add("classpath:spring/applicationContext-spring.xml");
        application.add("classpath:spring/applicationContext-async-task.xml");
        if(gameServiceConfig.isKakfkaOpen()){
            application.add("classpath:spring/applicationContext-kafka-producer.xml");
            application.add("classpath:spring/applicationContext-kafka.consumer.xml");
        }

        String[] strings = new String[application.size()];
        for (int i=0;i<application.size();i++) {
                strings[i]=application.get(i);
        }
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(strings);


        logger.info("--------------------init spring end------------------");
        springLoadService = (SpringLoadServiceImpl) applicationContext.getBean("springLoadService");


    }

    public static SpringLoadServiceImpl springLoadService;

    public static void start() {
        try {
            springLoadService.startup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shutdown() {
        try {
            logger.info("shutdown local server begin");
            springLoadService.shutdown();
            logger.info("shutdown local server success");
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.error("shutdown local server error", e.getCause());
            }
            e.printStackTrace();
        }
    }

    public static SpringLoadServiceImpl getSpringLoadService() {
        return springLoadService;
    }

}
