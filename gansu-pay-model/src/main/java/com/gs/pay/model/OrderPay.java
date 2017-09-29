package com.gs.pay.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ：xiaopingzhang8@creditease.cn
 * @Description ：支付订单
 * @ClassName ：com.gs.pay.model.OrderPay
 * @Company ：普信恒业科技发展（北京）有限公司
 * @date ：2017/9/27 17:44
 */
public class OrderPay implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;//自增Id
    private String tradeId;//交易流水号
    private String customerId;//客户Id
    private String outAccount;//出金账户
    private String inAccount;//入金账户
    private BigDecimal payAmount;//支付金额

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOutAccount() {
        return outAccount;
    }

    public void setOutAccount(String outAccount) {
        this.outAccount = outAccount;
    }

    public String getInAccount() {
        return inAccount;
    }

    public void setInAccount(String inAccount) {
        this.inAccount = inAccount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }
}
