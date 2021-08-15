package com.dingyabin.work.ctrl.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author 丁亚宾
 * Date: 2021/8/11.
 * Time:22:48
 */
public class ExecutorUtils {

    private static ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("another-cat-worker-%d").build();

    private static ExecutorService threadPoolExecutor = Executors.newCachedThreadPool(threadFactory);


    /**
     * 提交任务
     * @param runnable 线程
     */
    public static void execute(Runnable runnable) {
        threadPoolExecutor.execute(runnable);

    }



}
