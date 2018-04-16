package com.twjitm.core.service.test.Impl;

import com.twjitm.core.service.test.TestService;
import org.springframework.stereotype.Service;

/**
 * Created by нд╫╜ on 2018/4/16.
 */
@Service
public class TestServiceImpl implements TestService {
    public void say() {
        System.out.println("hello world");
    }
}
