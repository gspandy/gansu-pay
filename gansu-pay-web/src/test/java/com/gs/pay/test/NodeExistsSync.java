package com.gs.pay.test;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * @author ：xiaopingzhang8@creditease.cn
 * @Description ：
 * @ClassName ：NodeExistsSync
 * @Company ：普信恒业科技发展（北京）有限公司
 * @date ：2017/10/25 15:41
 */
public class NodeExistsSync implements Watcher {

    private static ZooKeeper zooKeeper;
    private static Stat stat = new Stat();
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        zooKeeper = new ZooKeeper("192.168.99.100:2181",50000,new NodeExistsSync());
        System.out.println(zooKeeper.getState().toString());
        Thread.sleep(Integer.MAX_VALUE);
    }

    private void doSomething(ZooKeeper zooKeeper){
        try{
            Stat stat = zooKeeper.exists("/node_5", true);
            if(stat == null){
                System.out.println("节点不存在");
            }else{
                System.out.println("doSomething-stat:" + stat);
            }
        }catch(Exception e){
        }
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("事件状态:" + event.getState() +",事件类型:" +  event.getType() +",事件涉及路径:" + event.getPath());
        if (event.getState()== Event.KeeperState.SyncConnected){
            if (event.getType()== Event.EventType.None && null==event.getPath()){
                doSomething(zooKeeper);
            }else{
                try {
                    if (event.getType()== Event.EventType.NodeCreated){
                        System.out.println(event.getPath()+" created");
                        System.out.println(zooKeeper.exists(event.getPath(), true));
                        System.out.println(zooKeeper.getData(event.getPath(), false, stat));
                    }
                    else if (event.getType()== Event.EventType.NodeDataChanged){
                        System.out.println(event.getPath()+" updated");
                        System.out.println(zooKeeper.exists(event.getPath(), true));
                        System.out.println(zooKeeper.getData(event.getPath(), false, stat));
                    }
                    else if (event.getType()== Event.EventType.NodeDeleted){
                        System.out.println(event.getPath()+" deleted");
                        System.out.println(zooKeeper.exists(event.getPath(), true));
                    }

                } catch (Exception e) {
                }

            }

        }
    }
}
