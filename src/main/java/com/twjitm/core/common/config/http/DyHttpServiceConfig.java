package com.twjitm.core.common.config.http;

import org.dom4j.Attribute;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 文江 on 2017/11/25.
 * http配置实体
 */
public class DyHttpServiceConfig {
    private  int id;
    private String name;
    private String ip;
    private  int port;
  private List<DyHttpServiceConfig> serviceConfigList;
    public DyHttpServiceConfig() {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 加载服务器配置
     * @param root
     */
    public void load(Element root) {
        Iterator<Element> it = root.elementIterator();
        List<DyHttpServiceConfig> list=new ArrayList<DyHttpServiceConfig>();
        while (it.hasNext()) {
            Element element = it.next();
            List<Attribute> attributes = element.attributes();
            DyHttpServiceConfig dyHttpServiceConfig=new DyHttpServiceConfig();
            dyHttpServiceConfig.setId(Integer.parseInt(attributes.get(0).getValue()));
            dyHttpServiceConfig.setName(attributes.get(1).getValue());
            dyHttpServiceConfig.setPort(Integer.parseInt(attributes.get(2).getValue()));
            dyHttpServiceConfig.setIp(attributes.get(3).getValue());
            list.add(dyHttpServiceConfig);
        }
        this.serviceConfigList=list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<DyHttpServiceConfig> getServiceConfigList() {
        return serviceConfigList;
    }


}
