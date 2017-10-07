package com.cloud.core;

import android.os.Handler;
import android.os.Message;

import com.cloud.core.logger.Logger;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-7-23 下午10:50:56
 * @Description: 倒计时处理类
 * @Modifier:
 * @ModifyContent:
 */
public class CountdownExecutor {

    private ScheduledThreadPoolExecutor sc = null;
    private long period = 1;
    private int time = 0;
    /**
     * 倒计时总时间(单位是秒)
     */
    private int countdownTotalTime = 0;

    /**
     * @param 设置每次执行时间间隔 (以秒为单位)
     */
    public void setPeriod(long period) {
        this.period = period;
    }

    /**
     * @param 设置倒计时总时间 (单位是秒)
     */
    public void setCountdownTotalTime(int countdownTotalTime) {
        this.countdownTotalTime = countdownTotalTime;
    }

    /**
     * @return 获取倒计时总时间
     * (单位是秒)
     */
    public int getCountdownTotalTime() {
        return time;
    }

    /**
     * 在倒计时启动之前执行
     *
     * @param seconds 当前计时秒数
     */
    protected void onPerExecutor(int seconds) {

    }

    /**
     * 在计时中执行
     *
     * @param seconds 当前计时秒数
     */
    protected void onDoingExecutor(int seconds) {

    }

    /**
     * 结束之后执行
     */
    protected void onPostExecutor() {

    }

    public void start() {
        if (countdownTotalTime <= 0) {
            return;
        }
        sc = new ScheduledThreadPoolExecutor(1);
        time = countdownTotalTime;
        onPerExecutor(time);
        sc.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    time--;
                    mhandler.obtainMessage(time).sendToTarget();
                    if (time <= 0) {
                        stop();
                        mhandler.obtainMessage(-2000).sendToTarget();
                    }
                } catch (Exception e) {
                    Logger.L.error("countdown deal with error:", e);
                }
            }
        }, 0, period, TimeUnit.SECONDS);
    }

    public void stop() {
        if (sc != null && !sc.isShutdown()) {
            sc.shutdown();
        }
        mhandler.obtainMessage(-2000).sendToTarget();
    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                if (msg.what == -2000) {
                    onPostExecutor();
                } else {
                    int seconds = msg.what;
                    onDoingExecutor(seconds);
                }
            } catch (Exception e) {
                Logger.L.error("countdown deal-with-ing error:", e);
            }
        }
    };
}
