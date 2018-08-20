package com.twjitm;


import com.twjitm.core.spring.SpringServiceManager;

public class TestSpring {
    public static void initSpring(){
        SpringServiceManager.init();
        SpringServiceManager.start();
    }


}
