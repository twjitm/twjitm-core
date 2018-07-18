package com.twjitm.threads.storage;

/**
 * Created by ÎÄ½­ on 2018/5/22.
 */
public class Consume extends Thread {
    private Storage storage;
    private int num;

    public Consume(Storage storage, int num) {
        this.storage = storage;
        this.num = num;
    }

    @Override
    public void run() {
      //  storage.consumeOfLock(num);
             storage.consumeofwn(num);
    }
}
