package com.gs.pay.server.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gs.pay.annotation.MDCTrace;
import com.gs.pay.dao.OrderPayMapper;
import com.gs.pay.model.OrderPay;
import com.gs.pay.server.api.WeChatPayApi;
import com.gs.pay.util.ReturnResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.sql.SQLException;

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
    @Resource
    private OrderPayMapper orderPayMapper;

    @Resource
    private TransactionTemplate transactionTemplate;

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
        try {
            OrderPay orderPay = JSON.parseObject(reqJson, OrderPay.class);
            int count = savePayInfo(orderPay);
            if (count > 0) {
                log.info("==支付成功");
                return ReturnResultUtils.getSuccess(null).toJSONString();
            } else {
                log.error("==支付失败");
                return ReturnResultUtils.getFail(null).toJSONString();
            }
        } catch (Exception e) {
            log.error("==commitPay确认支付异常Exception", e);
            return ReturnResultUtils.getFail(null).toJSONString();
        }
    }

    @Transactional
    int savePayInfo(OrderPay orderPay) {
        try {
            int count = orderPayMapper.insertOrderPay(orderPay);
            log.info("==插入成功一条");
            orderPayMapper.insertOrderPay(orderPay);
            return count;
        } catch (Exception e) {
            if(e instanceof SQLException){
                SQLException sqlE= (SQLException) e;
                log.error(sqlE.getErrorCode()+"\t"+sqlE.getSQLState()+"\t"+sqlE.getCause());
            }
            log.error("==保存支付信息异常", e);
            throw e;
        }
    }
   /* private int savePayInfo(final OrderPay orderPay) {
        return transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus status) {
                try {
                    int count = orderPayMapper.insertOrderPay(orderPay);
                    int a = 1/0;
                    return count;
                } catch (Exception e) {
                    log.error("==保存支付信息异常", e);
                    status.setRollbackOnly();
                    return 0;
                }
            }
        });
    }*/
}
