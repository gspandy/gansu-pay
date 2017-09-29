package com.gs.pay.test;

import com.alibaba.fastjson.JSON;
import com.gs.pay.model.OrderPay;
import com.gs.pay.util.UUIDSnowFlake;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author ：xiaopingzhang8@creditease.cn
 * @Description ：
 * @ClassName ：OrderPayJunit
 * @Company ：普信恒业科技发展（北京）有限公司
 * @date ：2017/9/28 10:24
 */
public class OrderPayJunit {
    public static void main(String[] args) {
        OrderPay pay = new OrderPay();
        pay.setTradeId(UUIDSnowFlake.getUUId());
        pay.setCustomerId("9527");
        pay.setOutAccount(UUIDSnowFlake.getUUId());
        pay.setInAccount(UUIDSnowFlake.getUUId());
        pay.setPayAmount(new BigDecimal("25.36"));
        System.out.println(JSON.toJSONString(pay));
    }
}
