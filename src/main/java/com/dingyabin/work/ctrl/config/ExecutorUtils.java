package com.dingyabin.work.ctrl.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 丁亚宾
 * Date: 2021/8/11.
 * Time:22:48
 */
public class ExecutorUtils {

    private static ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("another-cat-worker-%d").build();


    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            10, 15,
            1, TimeUnit.MINUTES,
            new LinkedBlockingQueue<Runnable>(100),
            threadFactory,
            new ThreadPoolExecutor.CallerRunsPolicy()
    );


    /**
     * 提交任务
     * @param runnable 线程
     */
    public static void execute(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }



}
