package com.twjitm.test;

import com.alibaba.fastjson.JSON;
import com.twjitm.core.player.dao.PlayerDao;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by twjitm on 2018/6/7.
 */
public class TestHashMap {
    public static void main(String[] args) {
        System.out.println(teste("a","b",new String[]{"c","b","ddddd"}));
        System.out.println(teste("a","b",new Object[]{new User(),"b","ddddd"}));
            }

    public static String teste(String a, String b, Object[] objects) {

        Map<String, Object> map = new HashMap<>();
        map.put(a, a);
        map.put(b, b);
        map.put("c", objects);
        String result=JSON.toJSONString(map);

        Object result2 = JSON.toJSON(map);
        System.out.println(result2.getClass().getName());
        System.out.println(result2.toString());
        return result;
    }

}
