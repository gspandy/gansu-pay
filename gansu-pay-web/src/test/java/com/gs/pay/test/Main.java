package com.gs.pay.test;

import com.alibaba.fastjson.JSON;
import com.gs.pay.model.OrderPay;
import com.gs.pay.util.UUIDSnowFlake;

import java.math.BigDecimal;

/**
 * @author ：xiaopingzhang8@creditease.cn
 * @Description ：
 * @ClassName ：Main
 * @Company ：普信恒业科技发展（北京）有限公司
 * @date ：2017/10/12 15:37
 */
public class Main {

    public static void main(String[] args) {
       /* OrderPay pay = new OrderPay();
        pay.setTradeId(UUIDSnowFlake.getUUId());
        pay.setCustomerId("9527");
        pay.setOutAccount(UUIDSnowFlake.getUUId());
        pay.setInAccount(UUIDSnowFlake.getUUId());
        pay.setPayAmount(new BigDecimal("25.36"));
        System.out.println(JSON.toJSONString(pay));*/

//       String url = "http://10.100.142.18:8092/yao-web/activity/customerOffline/localeSignUp.do?inviteCode=1600006&activitySerial=20";
       String url = "http://10.100.142.18:8092/yao-web/activity/customerOffline/localeSignUp.do?activitySerial=20&inviteCode=1600006&activitySel=20";

        System.out.println(url.indexOf("activitySerial=20")!=-1);

    }
}
