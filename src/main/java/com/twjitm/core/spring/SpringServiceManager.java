package com.twjitm.core.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by ÎÄ½­ on 2018/4/16.
 */

public class SpringServiceManager {

    public static void init() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext*.xml");
        SpringLoadManager springLoadManager = (SpringLoadManager) applicationContext.getBean("springLoadManager");
        springLoadManager.getTestService().say();
    }

}
