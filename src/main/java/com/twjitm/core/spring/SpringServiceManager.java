package com.twjitm.core.spring;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring Æô¶¯Èë¿Ú
 *
 * @author twjtim
 */

public class SpringServiceManager {
    static Logger logger = Logger.getLogger(SpringServiceManager.class);

    public static void init() {
        logger.info("load spring start");
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml", "classpath:spring/applicationContext-spring.xml");
        logger.info("load spring end");
        springLoadService = (SpringLoadServiceImpl) applicationContext.getBean("springLoadService");
        springLoadService.getTestService().say();
        springLoadService.getDispatcherService().getMessage("test dispatcher");
    }

    public static SpringLoadServiceImpl springLoadService;

    public static void start(){
        try {
            springLoadService.startup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
