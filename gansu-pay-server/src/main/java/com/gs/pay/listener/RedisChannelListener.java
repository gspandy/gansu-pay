package com.gs.pay.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ：xiaopingzhang8@creditease.cn
 * @Description ：redis 发布订阅模式监听（消费者）
 * @ClassName ：RedisChannelListener
 * @Company ：普信恒业科技发展（北京）有限公司
 * @date ：2017/10/20 18:02
 */
public class RedisChannelListener   {
    private final static Logger log = LoggerFactory.getLogger(RedisChannelListener.class);
    public void resetMemory(String message){
        log.info("message->{}",message);
        log.info("更新缓存");

    }
}
