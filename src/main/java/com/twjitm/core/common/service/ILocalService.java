package com.twjitm.core.common.service;

/**
 * Created by 文江 on 2017/11/11.
 */
public interface ILocalService {
    public String getId();

    public void startup() throws Exception;

    public void shutdown() throws Exception;
}
