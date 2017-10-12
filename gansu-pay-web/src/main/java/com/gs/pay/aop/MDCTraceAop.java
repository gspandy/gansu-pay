package com.gs.pay.aop;

import com.alibaba.fastjson.JSON;
import com.gs.pay.annotation.MDCTrace;
import com.gs.pay.util.UUIDGenerator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author ：xiaopingzhang8@creditease.cn
 * @Description ：MDC追踪号
 * @ClassName ：MDCTraceAop
 * @Company ：普信恒业科技发展（北京）有限公司
 * @date ：2017/9/20 10:48
 */
@Aspect
@Component
public class MDCTraceAop {
    private static final Logger log = LoggerFactory.getLogger(MDCTraceAop.class);

    /**·
     * MDC切入
     *
     * @param joinPoint
     */
    @Before("@annotation(com.gs.pay.annotation.MDCTrace)")
    public void beforeAspect(JoinPoint joinPoint) {
        String uuid = UUIDGenerator.getUUID();
        //获取方法参数
        Object[] args = joinPoint.getArgs();
        //获取注解方法
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method method = ms.getMethod();
        try {
            String index = method.getAnnotation(MDCTrace.class).value();
            int num = Integer.parseInt(index);
            uuid = String.valueOf(args[num]);
        } catch (NumberFormatException e) {
        }
        //打印参数列表
        log.info("==MDC追踪号切入Trade_ID->{},param->{}", new Object[]{uuid, JSON.toJSONString(args)});
        MDC.put("TRADE_ID", uuid);
    }

    /**
     * MDC移除
     *
     * @param joinPoint
     */
    @After("@annotation(com.gs.pay.annotation.MDCTrace)")
    public void afterAspect(JoinPoint joinPoint) {
        log.info("==MDC追踪号移除->{}", MDC.get("TRADE_ID"));
        MDC.remove("TRADE_ID");
    }

}
