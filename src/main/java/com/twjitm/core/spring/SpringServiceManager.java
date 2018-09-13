package com.twjitm.core.spring;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring service manager
 *
 * @author twjtim
 */

public class SpringServiceManager {
    static Logger logger = Logger.getLogger(SpringServiceManager.class);

    public static void init() {
        logger.info("--------------------init spring bootstrap----------------");

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "classpath:spring/applicationContext.xml",
                "classpath:spring/applicationContext-spring.xml",
                "classpath:spring/applicationContext-async-task.xml",
                "classpath:spring/applicationContext-kafka-producer.xml",
                "classpath:spring/applicationContext-kafka.consumer.xml");

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
