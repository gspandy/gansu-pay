package com.gs.pay.util;

import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author ：xiaopingzhang8@creditease.cn
 * @Description ：zk工具类
 * @ClassName ：ZookeeperUtils
 * @Company ：普信恒业科技发展（北京）有限公司
 * @date ：2017/10/16 15:56
 */
public class ZookeeperUtils {
    private static final Logger log = LoggerFactory.getLogger(ZookeeperUtils.class);
    private ZooKeeper zk;
    /*zk集群地址*/
    private String servers;
    /*链接超时时间*/
    private int sessionTimeout = 40000;
    /*根路径基于什么目录开始创建*/
    private String rootPath;

    public ZookeeperUtils() {

    }

    public ZooKeeper getAliveZk() {
        ZooKeeper aliveZk = zk;
        if (aliveZk != null && aliveZk.getState().isAlive()) {
            return aliveZk;
        } else {
            //重新建立连接
            zkReconnect();
            return zk;
        }
    }

    public synchronized void zkReconnect() {
        //首先关闭连接
        close();
        //建立连接
        connect();
    }

    /**
     * 关闭zk连接
     */
    public synchronized void close() {
        if (zk != null) {
            try {
                zk.close();
            } catch (InterruptedException e) {
                log.error("==关闭连接异常InterruptedException", e);
            }
            zk = null;
        }
    }

    /**
     * 连接zk
     */
    public synchronized void connect() {
        if (zk == null && StringUtils.isNotBlank(servers)) {
            try {
                zk = new ZooKeeper(servers, sessionTimeout, null);
            } catch (IOException e) {
                log.error("==建立连接异常IOException", e);
            }
        }
    }

    /**
     * 获取数据
     *
     * @param path
     * @return
     */
    public String getData(String path) {
        String result = null;
        try {
            byte[] data = getAliveZk().getData(path, Boolean.TRUE, null);
            if (null != data) {
                result = new String(data, "UTF-8");
            }
        } catch (KeeperException e) {
            log.error("==获取数据异常path->{} KeeperException", path, e);
        } catch (InterruptedException e) {
            log.error("==获取数据异常path->{} InterruptedException", path, e);
        } catch (UnsupportedEncodingException e) {
            log.error("==获取数据异常path->{} UnsupportedEncodingException", path, e);
        }
        return result;
    }

    /**
     * 获取孩子
     *
     * @param path
     * @return
     */
    public List<String> getChildren(String path) {
        List<String> data = null;
        try {
            data = getAliveZk().getChildren(path, Boolean.TRUE);
        } catch (KeeperException e) {
            log.error("==获取孩子失败path->{} KeeperException", path, e);
        } catch (InterruptedException e) {
            log.error("==获取孩子失败path->{} InterruptedException", path, e);
        }
        return data;
    }

    /**
     * 创建路径
     *
     * @param path
     * @param data
     * @param mode 路径模式 临时目录 持久目录
     * @return
     */
    public String createPath(String path, String data, CreateMode mode) {
        String result = null;
        try {
            result = getAliveZk().create(path, data == null ? null : data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, mode);
        } catch (Exception e) {
            log.error("==创建节点{}异常", path, e);
        }
        return result;
    }

    /**
     * 使用zk充当分布式锁
     *
     * @param lockPath 加锁路径
     * @return
     */
    public boolean tryLock(String lockPath) {
        Boolean flag = Boolean.FALSE;
        try {
            String result = getAliveZk().create(lockPath, "lock".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            return StringUtils.equals(lockPath, result);
        } catch (KeeperException e) {
            if (e instanceof KeeperException.NodeExistsException) {
                log.error("==路径{}已存在", lockPath, e);
            }
        } catch (InterruptedException e) {
            log.error("==获取锁异常InterruptedException", e);
        }
        return flag;
    }

    /**
     * 释放锁
     *
     * @param lockPath 加锁路径
     * @return
     */
    public void unLock(String lockPath) {
        try {
            getAliveZk().delete(lockPath, -1);
        } catch (KeeperException e) {
            log.error("==释放锁异常KeeperException", e);
        } catch (InterruptedException e) {
            log.error("==释放锁异常InterruptedException", e);
        }
    }

    /**
     * 获取数据，加入监听
     *
     * @param watchPath
     * @param ifWatch
     * @return
     */
    public String getData(String watchPath, Boolean ifWatch) {
        try {
            byte[] data = getAliveZk().getData(watchPath, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    log.info(event.getPath());
                    log.info("" + event.getState());
                }
            }, null);
            return new String(data, "UTF-8");
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改值
     * @param watchPath
     */
    public void setData(String watchPath) {
        try {
            Stat stat = getAliveZk().setData(watchPath, "我们都是好孩子".getBytes(), -1);
            log.info("stat->{}",stat.toString());

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void watchData() {
        String watchPath ="/conf/watch";
        try {
            while (true) {
                byte[] data = getAliveZk().getData(watchPath, new Watcher() {
                    @Override
                    public void process(WatchedEvent event) {
                        log.info("监控路径->{}"+event.getPath());
                        log.info("" + event.getState());
                        log.info("==数据变化，刷新内存");

                    }
                }, null);
                Thread.sleep(5000);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void setServers(String servers) {
        this.servers = servers;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }
}
