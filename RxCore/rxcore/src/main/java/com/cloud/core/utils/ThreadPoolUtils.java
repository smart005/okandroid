package com.cloud.core.utils;

import android.os.Handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/4/21
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class ThreadPoolUtils {

    //在创建线程执行
    private static Handler mhandler = new Handler();
    private static ExecutorService executorService;

    public static ExecutorService fixThread(int poolSize) {
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(poolSize, poolSize, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        }
        return executorService;
    }

    public static ExecutorService fixThread() {
        return fixThread(5);
    }

    public static void fixThread(final Runnable runnable, long delayMillis) {
        mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fixThread().execute(runnable);
            }
        }, delayMillis);
    }
}
