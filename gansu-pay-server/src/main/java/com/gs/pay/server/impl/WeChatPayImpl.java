package com.gs.pay.server.impl;

import com.alibaba.fastjson.JSONObject;
import com.gs.pay.annotation.MDCTrace;
import com.gs.pay.server.api.WeChatPayApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author ：xiaopingzhang8@creditease.cn
 * @Description ：
 * @ClassName ：WeChatPayImpl
 * @Company ：普信恒业科技发展（北京）有限公司
 * @date ：2017/9/25 11:31
 */
@Service("weChatPayApi")
public class WeChatPayImpl implements WeChatPayApi {
    private static final Logger log = LoggerFactory.getLogger(WeChatPayImpl.class);

    /**
     * 确认支付
     *
     * @param reqJson
     * @return
     */
    @MDCTrace
    @Override
    public String commitPay(String reqJson) {
        log.info("==确认支付请求报文->{}", reqJson);
        log.error("==支付成功");
        JSONObject result = new JSONObject(2);
        result.put("code", "2000");
        result.put("msg", "支付成功");
        return result.toJSONString();
    }
}
