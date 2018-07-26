package com.twjitm.core.utils.uuid;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author EGLS0807 - [Created on 2018-07-26 20:46]
 * @company http://www.g2us.com/
 * @jdk java version "1.8.0_77"
 */
public class AtomicLimitNumber {
    /**
     * 原子数量
     */
    private AtomicLong atomicLong;

    public AtomicLimitNumber() {
        this.atomicLong = new AtomicLong();
    }

    public long increment(){
        return this.atomicLong.incrementAndGet();
    }

    public long decrement(){
        return this.atomicLong.decrementAndGet();
    }

}
