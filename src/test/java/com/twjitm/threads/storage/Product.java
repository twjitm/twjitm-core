package com.twjitm.threads.storage;

/**
 * Created by ÎÄ½­ on 2018/5/22.
 */
public class Product extends Thread {
    private Storage storage;
    private int num;

    public Product(Storage storage, int num) {
        this.storage = storage;
        this.num = num;
    }


    @Override
    public void run() {
       storage.produceofwn(num);
        // storage.produceOfLock(num);
    }


}
