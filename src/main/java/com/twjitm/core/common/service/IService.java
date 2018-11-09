package com.twjitm.core.common.service;

/**
 * @author twjitm - [Created on 2018-07-26 20:30]
 * @company https://github.com/twjitm/
 * @jdk java version "1.8.0_77"
 */
public interface IService {
    public String getId();

    public void startup() throws Exception;

    public void shutdown() throws Exception;

}
