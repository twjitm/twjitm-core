package com.twjitm.core.utils.uuid;


import java.util.concurrent.atomic.AtomicLong;

/**
 * @author twjitm - [Created on 2018-07-24 19:41]
 * @company https://github.com/twjitm/
 * @jdk java version "1.8.0_77"
 */
public class LongIdGenerator {
    protected AtomicLong id_gen = new AtomicLong(0);

    public long generateId(){
        return id_gen.incrementAndGet();
    }



}
