package com.gs.pay.aop;

import com.gs.pay.annotation.RedisCacheAspect;
import com.gs.pay.util.RedisUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author ：xiaopingzhang8@creditease.cn
 * @Description ：redis缓存切入
 * @ClassName ：RedisCacheAop
 * @Company ：普信恒业科技发展（北京）有限公司
 * @date ：2017/10/12 11:40
 */
@Aspect
@Component
public class RedisCacheAop {
    private static final Logger log = LoggerFactory.getLogger(RedisCacheAop.class);
    @Resource
    private RedisUtils redisUtils;

//    @Before("@annotation(com.gs.pay.annotation.RedisCacheAspect)")
    @Pointcut("@annotation(com.gs.pay.annotation.RedisCacheAspect)")
    public void myPointCut() {

    }

    @Around("myPointCut()")
    public Object beforeAspect(ProceedingJoinPoint joinPoint) {
        //首先获取注解中的key
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method method = ms.getMethod();
        Class cls = method.getReturnType();//方法返回类型
        String key = method.getAnnotation(RedisCacheAspect.class).key();
        int index = method.getAnnotation(RedisCacheAspect.class).index();
        if (StringUtils.isNotBlank(key)) {
            //如果key不为空，首先从redis中获取数据
            Object value = redisUtils.getValue(key, index);
            if (value == null) {
                //从数据中获取
                try {
                    value = joinPoint.proceed();
                    if (value != null) {
                        redisUtils.setValue(key, value, index);
                    }
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
            return value;
        } else {
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return null;

    }
}
