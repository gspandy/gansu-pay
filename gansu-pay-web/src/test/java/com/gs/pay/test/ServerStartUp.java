package com.gs.pay.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author ：xiaopingzhang8@creditease.cn
 * @Description ：启动服务
 * @ClassName ：ServerStartUp
 * @Company ：普信恒业科技发展（北京）有限公司
 * @date ：2017/10/13 10:41
 */
public class ServerStartUp {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:test-spring-config.xml");
        context.start();
        System.in.read();


    }

}
