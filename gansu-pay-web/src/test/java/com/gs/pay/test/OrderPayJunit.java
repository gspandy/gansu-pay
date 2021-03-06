package com.gs.pay.test;

import com.alibaba.fastjson.JSON;
import com.gs.pay.model.OrderPay;
import com.gs.pay.service.api.WeChatPayApi;
import com.gs.pay.util.RedisUtils;
import com.gs.pay.util.UUIDSnowFlake;
import com.gs.pay.util.ZookeeperUtils;
import com.gs.pay.zkwatch.ZookeeperWatch;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @author ：xiaopingzhang8@creditease.cn
 * @Description ：
 * @ClassName ：OrderPayJunit
 * @Company ：普信恒业科技发展（北京）有限公司
 * @date ：2017/9/28 10:24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-config.xml"})
public class OrderPayJunit {
    private static final Logger log = LoggerFactory.getLogger(OrderPayJunit.class);
    @Resource
    private WeChatPayApi weChatPayApi;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ZookeeperUtils zookeeperUtils;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private ZookeeperWatch zookeeperWatch;

    @Test
    public void testRedis() {
        List<OrderPay> orderPayList = weChatPayApi.getOrderPay();
        System.out.println(JSON.toJSONString(orderPayList));
    }

    @Test
    public void addRedis() {
        for (int i = 1; i < 5; i++) {
            OrderPay pay = new OrderPay();
            pay.setTradeId(UUIDSnowFlake.getUUId());
            pay.setCustomerId("9527");
            pay.setOutAccount(UUIDSnowFlake.getUUId());
            pay.setInAccount(UUIDSnowFlake.getUUId());
            pay.setPayAmount(new BigDecimal("25.36").multiply(new BigDecimal(i + "")));
            weChatPayApi.commitPay(JSON.toJSONString(pay));
        }
    }

    @Test
    public void testLock() {
        final Object object = new Object();
        final String key = "pay";
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!redisUtils.tryLock(key, 5000)) {
                    command(object, key);
                } else {
                    try {
                        log.info(Thread.currentThread().getName() + "==未获得锁，现场进入等待状态，等待被唤醒");
                        synchronized (object) {
                            object.wait();
                            log.info(Thread.currentThread().getName() + "==线程被唤醒");
                            command(object, key);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "线程1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (redisUtils.tryLock(key, 5000)) {
                    command(object, key);
                } else {
                    try {
                        log.info(Thread.currentThread().getName() + "==未获得锁，现场进入等待状态，等待被唤醒");
                        synchronized (object) {
                            object.wait();
                            log.info(Thread.currentThread().getName() + "==线程被唤醒");
                            command(object, key);

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "线程2").start();


        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void command(Object object, final String key) {
        synchronized (object) {
            try {
                log.info(Thread.currentThread().getName() + "==获取到锁");
                Thread.sleep(1000);
                log.info(Thread.currentThread().getName() + "==正在执行……");
                Thread.sleep(1000);
                log.info(Thread.currentThread().getName() + "==执行完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                log.info(Thread.currentThread().getName() + "==释放锁");
                redisUtils.unLock(key);
                object.notifyAll();
            }
        }
    }


    @Test
    public void testAop() {
        List<OrderPay> orderPayList = weChatPayApi.getOrderPayList();
        System.out.println(JSON.toJSONString(orderPayList));
    }

    @Test
    public void testzk1() {
        System.out.println(zookeeperUtils.createPath("/conf/zk2", "xiaoxin", CreateMode.EPHEMERAL));

    }

    @Test
    public void testzk2() {
        System.out.println(zookeeperUtils.getChildren("/conf"));

    }

    @Test
    public void testzk3() {
        System.out.println(zookeeperUtils.getData("/conf/zk"));
    }


    @Test
    public void testzkLock() {
        final Object object = new Object();
        final String key = "/conf/syncLock";
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (zookeeperUtils.tryLock(key)) {
                    command2(object, key);
                } else {
                    try {
                        log.info(Thread.currentThread().getName() + "==未获得锁，现场进入等待状态，等待被唤醒");
                        synchronized (object) {
                            object.wait();
                            log.info(Thread.currentThread().getName() + "==线程被唤醒");
                            if (zookeeperUtils.tryLock(key)) {
                                command2(object, key);
                            }

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "线程1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (zookeeperUtils.tryLock(key)) {
                    command2(object, key);
                } else {
                    try {
                        log.info(Thread.currentThread().getName() + "==未获得锁，现场进入等待状态，等待被唤醒");
                        synchronized (object) {
                            object.wait();
                            log.info(Thread.currentThread().getName() + "==线程被唤醒");
                            if (zookeeperUtils.tryLock(key)) {
                                command2(object, key);
                            }

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "线程2").start();


        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void command2(Object object, final String key) {
        synchronized (object) {
            try {
                log.info(Thread.currentThread().getName() + "==获取到锁");
                Thread.sleep(1000);
                log.info(Thread.currentThread().getName() + "==正在执行……");
                Thread.sleep(1000);
                log.info(Thread.currentThread().getName() + "==执行完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                log.info(Thread.currentThread().getName() + "==释放锁");
                zookeeperUtils.unLock(key);
                object.notifyAll();
            }
        }
    }

    @Test
    public void testZKlOCK() throws Exception {
//        System.out.println(zookeeperUtils.tryLock("/conf/lock"));
        zookeeperUtils.getData("/conf/watch", true);
        System.in.read();
//        zookeeperUtils.setData("/conf/watch");
    }

    @Test
    public void testRedisPublish() {
        redisTemplate.convertAndSend("gansu","我正在发布消息");
    }

    @Test
    public void testwatch() throws Exception {
//        zookeeperWatch.watchNode("/lock/watch");
        System.in.read();
    }


}
