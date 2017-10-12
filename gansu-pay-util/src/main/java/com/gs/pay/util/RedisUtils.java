package com.gs.pay.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * @author ：xiaopingzhang8@creditease.cn
 * @Description ：
 * @ClassName ：RedisUtils
 * @Company ：普信恒业科技发展（北京）有限公司
 * @date ：2017/10/10 18:51
 */

public class RedisUtils {
    private static final Logger log = LoggerFactory.getLogger(RedisUtils.class);
    @Resource
    private JedisPool jedisPool;

    /**
     * redis设置值
     *
     * @param key   唯一标识Key
     * @param value
     * @param index 存在那个数据库
     * @param <V>
     */
    public <V> void setValue(String key, V value, int index) {
        log.info("==redis设置缓存");
        String json = JSON.toJSONString(value);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(index);
            jedis.set(key, json);
        } catch (Exception e) {
            log.error("==setValue设置数据异常", e);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * redis设置值
     *
     * @param key
     * @param value
     * @param second 过期时间
     * @param index
     * @param <V>
     */
    public <V> void setValueExpire(String key, V value, int second, int index) {
        log.info("==redis设置缓存过期时间");
        String json = JSON.toJSONString(value);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(index);
            jedis.set(key, json);
            jedis.expire(key, second);
        } catch (Exception e) {
            log.error("==setValue设置数据异常", e);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 获取缓存数据
     *
     * @param key
     * @param index
     * @param <V>
     * @return
     */
    public <V> V getValue(String key, int index) {
        String value = "";
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(index);
            value = jedis.get(key);
        } catch (Exception e) {
            log.error("==getValue获取数据异常", e);
        } finally {
            closeJedis(jedis);
        }
        return (V) JSONObject.parse(value);
    }

    public String getValueStr(String key, int index) {
        String value = "";
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(index);
            value = jedis.get(key);
        } catch (Exception e) {
            log.error("==getValue获取数据异常", e);
        } finally {
            closeJedis(jedis);
        }
        return value;
    }

    /**
     * 使用redis充当分布式锁
     *
     * @param key
     * @return
     */
    public boolean tryLock(String key, long expireTime) {
        Jedis jedis = null;
        try {
            String expire = String.valueOf(System.currentTimeMillis() + expireTime + 1);
            jedis = jedisPool.getResource();
            Long result = jedis.setnx(key, expire);
            if (result == 1L) {
                jedis.expire(key, Integer.valueOf(expireTime + ""));
                return true;
            }
            //超过了过期时间，但是key没有删除
            if (Long.parseLong(jedis.get(key)) < System.currentTimeMillis()) {
                jedis.set(key, expire);
                jedis.expire(key, Integer.valueOf(expireTime + ""));
                return true;
            }
        } catch (Exception e) {
            closeJedis(jedis);
        } finally {
            closeJedis(jedis);
        }
        return false;
    }

    /**
     * 释放锁
     *
     * @param key
     */
    public void unLock(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            closeJedis(jedis);
        } finally {
            closeJedis(jedis);
        }
    }


    /**
     * 关闭redis连接，释放资源
     *
     * @param jedis
     */
    public void closeJedis(Jedis jedis) {
        try {
            if (jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
        } catch (Exception e) {
            log.error("==jedis释放资源异常", e);
        }
    }


}
