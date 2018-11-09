package com.twjitm.core.utils.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author twjitm - [Created on 2018-07-27 11:03]
 * @company https://github.com/twjitm/
 * @jdk java version "1.8.0_77"
 */
@Service
public class BeanUtils   implements ApplicationContextAware {
    private static ApplicationContext ctx;
    @Override
    public void setApplicationContext(ApplicationContext arg0)throws BeansException {
        ctx = arg0;
    }

    public static Object getBean(String beanName) {
        if(ctx == null){
            throw new NullPointerException();
        }
        return ctx.getBean(beanName);
    }
}
