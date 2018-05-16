package com.twjitm.core.common.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by 文江 on 2017/10/24.
 * 获得短连接服务
 */
public class ControllerService implements ILocalService {
    public static void init() {
        initUserService();

    }


    private static void initUserService() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext();
        classPathXmlApplicationContext.getBean("");
    }

    public String getId() {
        return null;
    }

    public void startup() throws Exception {

    }

    public void shutdown() throws Exception {

    }
}
