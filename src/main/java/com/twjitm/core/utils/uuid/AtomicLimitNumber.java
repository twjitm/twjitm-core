package com.twjitm.core.utils.uuid;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author twjitm - [Created on 2018-07-26 20:46]
 * @company https://github.com/twjitm/
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
