package com.cloud.core;

import android.os.Handler;
import android.os.Message;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-9-8 上午8:51:43
 * @Description: 循环执行器(类似于定时器)
 * @Modifier:
 * @ModifyContent:
 */
public class CycleExecutor<T> {

    private ScheduledThreadPoolExecutor sc = null;
    /**
     * 每次执行时间间隔 (以毫秒为单位)
     */
    private long period = 1000;

    private T[] parames;

    /**
     * @param 设置每次执行时间间隔 (以毫秒为单位)
     */
    public void setPeriod(long period) {
        this.period = period;
    }

    /**
     * @param 设置parames
     */
    public void setParames(T... parames) {
        this.parames = parames;
    }

    protected void onDoingExecutor(T... parames) {

    }

    public void start() {
        if (sc != null) {
            if (!sc.isShutdown()) {
                sc.shutdown();
            }
        }
        sc = new ScheduledThreadPoolExecutor(1);
        sc.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                mhandler.obtainMessage().sendToTarget();
            }
        }, 0, period, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        if (sc != null && !sc.isShutdown()) {
            sc.shutdown();
        }
    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            onDoingExecutor(parames);
        }
    };
}
