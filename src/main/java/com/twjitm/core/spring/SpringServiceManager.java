package com.twjitm.core.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *spring Æô¶¯Èë¿Ú
 * @author twjtim
 */

public class SpringServiceManager {

    public static void init() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext*.xml");
        springLoadManager = (SpringLoadManager) applicationContext.getBean("springLoadManager");
        springLoadManager.getTestService().say();
        springLoadManager.getDispatcherService().getMessage("test dispatcher");

    }
    public static SpringLoadManager springLoadManager;

}
