package com.gs.pay.annotation;

import java.lang.annotation.*;

/**
 * @author ：xiaopingzhang8@creditease.cn
 * @Description ：使用注解的方式调用redis缓存
 * @ClassName ：RedisCacheAspect
 * @Company ：普信恒业科技发展（北京）有限公司
 * @date ：2017/10/12 10:31
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisCacheAspect {
    /**
     * redis Key值，不能为空
     *
     * @return
     */
    String key();

    /**
     * redis所在数据库，默认为0
     * @return
     */
    int index() default 0;
}
