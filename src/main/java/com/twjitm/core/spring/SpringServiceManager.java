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
        springLoadManager = (SpringLoadManager) applicationContext.getBean("springLoadManager");
        springLoadManager.getTestService().say();
        springLoadManager.getDispatcherService().getMessage("test dispatcher");

    }

    public static SpringLoadManager springLoadManager;

}
