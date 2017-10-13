package com.gs.pay.task;

import com.gs.pay.annotation.MDCTrace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ：xiaopingzhang8@creditease.cn
 * @Description ：文件对账冲正定时任务
 * @ClassName ：AccountReverseTask
 * @Company ：普信恒业科技发展（北京）有限公司
 * @date ：2017/10/13 12:01
 */
public class AccountReverseTask {
    private static final Logger log = LoggerFactory.getLogger(AccountReverseTask.class);

    @MDCTrace
    public void execute(){
        try {
            log.info("==启动文件对账冲正任务==");
            Thread.sleep(10000);
            log.info("==文件对账冲正任务执行中……==");
            Thread.sleep(30000);
            log.info("==文件对账冲正任务结束==");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
