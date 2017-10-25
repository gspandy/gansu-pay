package com.gs.pay.zkwatch;

import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.common.PathUtils;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author ：xiaopingzhang8@creditease.cn
 * @Description ：
 * @ClassName ：ZookeeperWatch
 * @Company ：普信恒业科技发展（北京）有限公司
 * @date ：2017/10/25 11:35
 */
public class ZookeeperWatch implements Watcher {
    private static final Logger log = LoggerFactory.getLogger(ZookeeperWatch.class);
    private ZooKeeper zk;
    /*zk集群地址*/
    private String servers;
    /*链接超时时间*/
    private int sessionTimeout = 40000;
    private String separator = "/";
    private LinkedBlockingQueue<String> pathQueue = new LinkedBlockingQueue<>();
    private Stat stat = new Stat();

    @Override
    public void process(WatchedEvent event) {
        log.info("==触发watch回调机制：路径：{}，事件类型：{}", event.getPath(), event.getState());
        if(event.getState()==Event.KeeperState.SyncConnected){
            if(event.getType()==Event.EventType.None && null==event.getPath()){
                //添加监听
                addWatch();
            }else{
                try {
                    if (event.getType()== Event.EventType.NodeCreated){
                        System.out.println(event.getPath()+" created");
                        System.out.println(zk.exists(event.getPath(), true));
                        System.out.println(zk.getData(event.getPath(), false, stat));
                    }
                    else if (event.getType()== Event.EventType.NodeDataChanged){
                        System.out.println(event.getPath()+" updated");
                        System.out.println(zk.exists(event.getPath(), true));
                        System.out.println(zk.getData(event.getPath(), false, stat));
                    }
                    else if (event.getType()== Event.EventType.NodeDeleted){
                        System.out.println(event.getPath()+" deleted");
                        System.out.println(zk.exists(event.getPath(), true));
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * 初始化zk连接
     */
    @PostConstruct
    public void initConnect() {
        if (zk != null && zk.getState().isAlive()) {
            log.info("==连接已建立");
        } else {
            close();
            connect();
        }
        pathQueue.offer("/conf/watch");
        pathQueue.offer("/conf/nuggets");
        pathQueue.offer("/conf/cupid");
    }

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
                zk = new ZooKeeper(servers, sessionTimeout, this);
            } catch (IOException e) {
                log.error("==建立连接异常IOException", e);
            }
        }
    }

    /**
     * 监控某个节点
     *
     * @param watchNode
     */
    public void watchNode(String watchNode) {
        //首先校验输入参数
        PathUtils.validatePath(watchNode);
        String[] nodes = watchNode.split(separator);
        String path = "";
        try {
            for(int i = 1;i<nodes.length;i++) {
                path = path + separator + nodes[i];
                if (zk.exists(path, true) == null) {
                    log.info("==path：{}节点不存在，开始创建节点", path);
                    zk.create(path, nodes[i].getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pathQueue.offer(watchNode);
    }

    public void addWatch() {
        try {
            while (!pathQueue.isEmpty()){
                String path = pathQueue.take();
                Stat stat = zk.exists(path, true);
                log.info("==监听路径path：{}",path);
                if(stat==null){
                    log.info("path：{}不存在",path);
                    watchNode(path);
                }else{
                    log.info("==节点存在stat->",stat);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }


    public void setServers(String servers) {
        this.servers = servers;
    }
}
