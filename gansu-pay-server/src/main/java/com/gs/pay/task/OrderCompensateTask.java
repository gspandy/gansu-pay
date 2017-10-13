package com.gs.pay.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ：xiaopingzhang8@creditease.cn
 * @Description ：订单补偿定时任务
 * @ClassName ：OrderCompensateTask
 * @Company ：普信恒业科技发展（北京）有限公司
 * @date ：2017/10/13 10:19
 */
public class OrderCompensateTask extends AbstractSchedualTask {
    private static final Logger log = LoggerFactory.getLogger(OrderCompensateTask.class);

    @Override
    void execute() {
        try {
            log.info("==启动订单补偿任务==");
            Thread.sleep(10000);
            log.info("==订单补偿任务执行中……==");
            Thread.sleep(30000);
            log.info("==订单任务补偿结束==");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
