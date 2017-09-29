-- 订单表
CREATE TABLE t_orderpay (
  id bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  tradeId varchar(32) NOT NULL COMMENT '交易流水号',
  customerId varchar(32) NOT NULL COMMENT '客户Id',
  outAccount varchar(32) NOT NULL COMMENT '出金账户',
  inAccount varchar(32) NOT NULL COMMENT '入金账户',
  payAmount decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '支付金额',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
