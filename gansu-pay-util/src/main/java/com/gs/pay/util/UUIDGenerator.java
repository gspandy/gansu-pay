package com.gs.pay.util;

import java.math.MathContext;
import java.util.UUID;

/**
 * @author ：xiaopingzhang8@creditease.cn
 * @Description ：UUID生成器
 * @ClassName ：UUIDGenerator
 * @Company ：普信恒业科技发展（北京）有限公司
 * @date ：2017/9/19 17:20
 */
public class UUIDGenerator {

    /**
     * 获取UUID随机数
     * @return
     */
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString().replace("-","");
        return uuid;
    }


    public static void main(String[] args) {
        System.out.println(getUUID());
    }
}
