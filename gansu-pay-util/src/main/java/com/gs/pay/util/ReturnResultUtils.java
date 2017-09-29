package com.gs.pay.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ：xiaopingzhang8@creditease.cn
 * @Description ：报文结果返回
 * @ClassName ：ReturnResultUtils
 * @Company ：普信恒业科技发展（北京）有限公司
 * @date ：2017/9/27 18:26
 */
public class ReturnResultUtils {
    private static final Logger log = LoggerFactory.getLogger(ReturnResultUtils.class);
    private static final String SUCCESS_CODE="2000";
    private static final String FAIL_CODE="4000";


    /**
     * 成功
     * @param value
     * @return
     */
    public static JSONObject getSuccess(Object value){
        JSONObject result = new JSONObject();
        result.put("code", SUCCESS_CODE);
        result.put("msg", "success");
        result.put("value",value);
        log.info("==返回结果result->{}",result.toJSONString());
        return result;
    }

    /**
     * 失败
     * @param value
     * @return
     */
    public static JSONObject getFail(Object value){
        JSONObject result = new JSONObject();
        result.put("code", FAIL_CODE);
        result.put("msg", "fail");
        result.put("value",value);
        log.info("==返回结果result->{}",result.toJSONString());
        return result;
    }
}
