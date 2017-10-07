package com.cloud.core;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.cloud.core.logger.Logger;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-7-23 下午10:52:25
 * @Description: 可循环文本处理类
 * @Modifier:
 * @ModifyContent:
 */
public class CycleTextExecutor {

    private ScheduledThreadPoolExecutor sc = null;
    /**
     * 每次执行时间间隔 (以秒为单位)
     */
    private long period = 1;
    private String mdeftext = "";
    private String mcycletext = "";
    private int mcount = 0;
    private int mcurrnum = 0;

    /**
     * @param 设置每次执行时间间隔 (以秒为单位)
     */
    public void setPeriod(long period) {
        this.period = period;
    }

    protected void onDoingExecutor(String deftext, String cycletext) {

    }

    public void start(String deftext, String cycletext) {
        mdeftext = deftext;
        mcycletext = cycletext;
        if (TextUtils.isEmpty(mcycletext)) {
            return;
        }
        mcount = mcycletext.length();
        mcurrnum = 0;
        sc = new ScheduledThreadPoolExecutor(1);
        sc.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                mhandler.obtainMessage().sendToTarget();
            }
        }, 0, period, TimeUnit.SECONDS);
    }

    public void stop() {
        if (sc != null && !sc.isShutdown()) {
            sc.shutdown();
        }
    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                mcurrnum++;
                if (mcurrnum > mcount) {
                    mcurrnum = mcount;
                }
                String mtext = mcycletext.substring(0, mcurrnum - 1);
                onDoingExecutor(mdeftext, mtext);
                if (mcurrnum >= mcount) {
                    mcurrnum = 0;
                }
            } catch (Exception e) {
                Logger.L.error("cycle text deal-with-ing error:", e);
            }
        }
    };
}
