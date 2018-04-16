package com.twjitm.core.spring;

import com.twjitm.core.service.test.TestService;
import com.twjitm.core.service.user.UserService;

import javax.annotation.Resource;

/**
 * Created by ÎÄ½­ on 2018/4/16.
 */
public class SpringLoadManager {
    @Resource
    private TestService testService;
    @Resource
    private UserService userService;

    public TestService getTestService() {
        return testService;
    }

    public UserService getUserService() {
        return userService;
    }

}
