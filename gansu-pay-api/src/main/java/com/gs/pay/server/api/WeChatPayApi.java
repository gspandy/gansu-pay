package com.gs.pay.server.api;

/**
 * @author ：xiaopingzhang8@creditease.cn
 * @Description ：微信支付API
 * @ClassName ：WeChatPayApi
 * @Company ：普信恒业科技发展（北京）有限公司
 * @date ：2017/9/19 15:42
 */
public interface WeChatPayApi {
    /**
     * 确认支付
     * @param reqJson
     * @return
     */
    String commitPay(String reqJson);
}
