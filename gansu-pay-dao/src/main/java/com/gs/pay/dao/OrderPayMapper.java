package com.gs.pay.dao;

import com.gs.pay.model.OrderPay;

/**
 * @author ：xiaopingzhang8@creditease.cn
 * @Description ：订单支付DB操作
 * @ClassName ：com.gs.pay.dao.OrderPayMapper
 * @Company ：普信恒业科技发展（北京）有限公司
 * @date ：2017/9/27 17:56
 */
public interface OrderPayMapper {

    /**
     * 插入支付订单
     * @param orderPay
     * @return
     */
    int insertOrderPay(OrderPay orderPay);
}
