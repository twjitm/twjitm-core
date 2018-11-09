package com.twjitm.core.test;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by twjitm on 2018/11/9/11:35
 */
public class TestJson {

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        List<Text> testlist = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Text text = new Text();
            text.setContext("hhshah" + i);
            text.setIsdefault(true);
            text.setLanguage("en");
            testlist.add(text);
        }
        System.out.println(JSONArray.toJSONString(testlist));


    }


    public static class Text {
        private String language;
        private String context;
        private boolean isdefault;

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public boolean isIsdefault() {
            return isdefault;
        }

        public void setIsdefault(boolean isdefault) {
            this.isdefault = isdefault;
        }
    }
}
