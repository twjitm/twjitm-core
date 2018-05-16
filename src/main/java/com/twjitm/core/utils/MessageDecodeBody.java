package com.twjitm.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 消息解码处理器
 */
public class MessageDecodeBody {
    //反射获取proto生成类的子类
    private static String clzzName="com.twjitm.core.proto.BaseMessageProto";

    public static void init() {
        try {
            Class clzz = ClassLoader.getSystemClassLoader().loadClass(clzzName);
            Class[]  clzzarray = clzz.getClasses();
            for(Class clzz1:clzzarray){
                Method[] meth = clzz1.getMethods();
               for(Method method:meth){
                   System.out.println( method.getName());
               }
            }
            Field[] array = clzz.getDeclaredFields();
                  for (Field field:array){

                  }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        init();
    }
}
