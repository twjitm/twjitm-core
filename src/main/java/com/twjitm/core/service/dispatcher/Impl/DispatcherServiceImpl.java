package com.twjitm.core.service.dispatcher.Impl;


import com.twjitm.core.common.logic.handler.BaseHandler;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetMessage;
import com.twjitm.core.common.netstack.entity.AbstractNettyNetProtoBufMessage;
import com.twjitm.core.service.dispatcher.IDispatcherService;
import com.twjitm.core.spring.SpringServiceManager;
import com.twjitm.core.utils.logs.LoggerUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author twjitm
 * @date 2018/4/27
 * 分发器 ,消息在上层通过消息队列，获取消息，然后进行处理
 * <p>
 * ------------------------------
 * msg1,mes2,msg3,msg4,msg5......
 * ------------------------------
 * head                      last
 */
@Service
public class DispatcherServiceImpl implements IDispatcherService {
    private Logger logger = LoggerUtils.getLogger(DispatcherServiceImpl.class);


    @Override
    public AbstractNettyNetProtoBufMessage dispatcher(AbstractNettyNetMessage message) {
        int commId = message.getNetMessageHead().getCmd();
        BaseHandler baseHandler = SpringServiceManager.getSpringLoadService().getMessageRegistryFactory()
                .getHandler(commId);//handlerMap.get(commId);
        if (baseHandler == null) {
            return null;
        }
        Method method = baseHandler.getMethod(commId);
        method.setAccessible(true);
        try {
            Object object = method.invoke(baseHandler,
                    message);
            AbstractNettyNetProtoBufMessage baseMessage = null;
            if (object != null) {
                baseMessage = (AbstractNettyNetProtoBufMessage) object;
            }

            if(logger.isDebugEnabled()){
                logger.info("invoke message successful message comm id is:"+commId);
            }
            return baseMessage;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
