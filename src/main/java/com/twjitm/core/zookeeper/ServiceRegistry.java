package com.twjitm.core.zookeeper;

import org.apache.log4j.Logger;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by  on 2018/4/17.
 * zookeeper
 */
public class ServiceRegistry {
    private static final Logger LOGGER = Logger.getLogger(ServiceRegistry.class);
    private CountDownLatch latch = new CountDownLatch(1);

    private String registryAddress = "0.0.0.0:2181";

    public ServiceRegistry(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public void register(String data) {
        if (data != null) {
            ZooKeeper zk = connectServer();
            if (zk != null) {
                AddRootNode(zk); // Add root node if not exist
                createNode(zk, data);
            }
        }
    }


    /**
     * ookeeper
     *
     * @return
     */
    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(registryAddress, Constant.ZK_SESSION_TIMEOUT, new Watcher() {
                public void process(WatchedEvent event) {
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            });
            latch.await();
        } catch (IOException e) {
            LOGGER.error("", e);
        } catch (InterruptedException ex) {
            LOGGER.error("", ex);
        }
        return zk;
    }

    /**
     *
     *
     * @param zk
     */
    private void AddRootNode(ZooKeeper zk) {
        try {
            Stat s = zk.exists(Constant.ZK_REGISTRY_PATH, false);
            if (s == null) {
                zk.create(Constant.ZK_REGISTRY_PATH, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException e) {
            LOGGER.error(e.toString());
        } catch (InterruptedException e) {
            LOGGER.error(e.toString());
        }
    }

    /**
     *
     *
     * @param zk
     * @param data
     */
    private void createNode(ZooKeeper zk, String data) {
        try {
            byte[] bytes = data.getBytes();
            String path = zk.create(Constant.ZK_DATA_PATH, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            LOGGER.debug("create zookeeper node ({} => {})" + path + data);
        } catch (KeeperException e) {
            LOGGER.error("", e);
        } catch (InterruptedException ex) {
            LOGGER.error("", ex);
        }
    }

    public void close(ZooKeeper zk) {
        if (zk != null) {
            try {
                zk.close();
            } catch (InterruptedException e) {
                LOGGER.error(e.toString());
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        ServiceRegistry registry=new ServiceRegistry("0.0.0.0:2181");
        ZooKeeper zooKeeper=registry.connectServer();
        registry.register("/twjitm");
       // registry.
    }
}

