package com.twjitm.core.annotation;


import com.twjitm.core.enums.MessageComm;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 消息分离注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MessageCommandAnntation {
    MessageComm messagecmd();
}
