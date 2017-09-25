package com.gs.pay.annotation;

import java.lang.annotation.*;

/**
 * @author ：xiaopingzhang8@creditease.cn
 * @Description ：MDC追踪注解类
 * @ClassName ：MDCTrace
 * @Company ：普信恒业科技发展（北京）有限公司
 * @date ：2017/9/20 10:51
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MDCTrace {
    /**
     * value 的值 为正整数，指的是需要将第几位参数作为追踪号传递
     * 如果传递的参数格式不对，或者造成数组越界，则生成的追踪号为uuid
     * @return
     */
    String value() default "";
}
