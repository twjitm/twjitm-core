package com.twjitm.test;

/**
 * Created by ÎÄ½­ on 2018/6/7.
 */
public class TestHashMap {
    public static void main(String[] args) {
        System.out.println(2 ^ 2);

        int cap = 16;
        int n = cap - 1;

        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        System.out.println(n);
    }

}
