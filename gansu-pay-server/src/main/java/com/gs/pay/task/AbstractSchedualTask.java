package com.gs.pay.task;

import com.gs.pay.annotation.MDCTrace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author ：xiaopingzhang8@creditease.cn
 * @Description ：定时任务抽象类，用来控制整个定时任务
 * @ClassName ：AbstractSchedualTask
 * @Company ：普信恒业科技发展（北京）有限公司
 * @date ：2017/10/13 10:23
 */
public abstract class AbstractSchedualTask {
    private static final Logger log = LoggerFactory.getLogger(AbstractSchedualTask.class);
    /**
     * <p>
     * 定义一个线程安全的原子变量，防止定时任务在执行的过程中再次开启定时任务
     * </p>
     */
    private AtomicBoolean flag = new AtomicBoolean(true);

    /**
     * 需要实现的业务方法
     */
    abstract void execute();

    @MDCTrace
    public void run() {
        if (flag.compareAndSet(true, false)) {
            log.info("==开始执行任务==");
            execute();
            log.info("==任务执行完成==");
            flag.compareAndSet(false, true);
        } else {
            log.info("==该任务正在执行中……==");
        }
    }
}
