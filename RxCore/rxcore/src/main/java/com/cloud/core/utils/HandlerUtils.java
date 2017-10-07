package com.cloud.core.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/3/7
 * @Description:主线程的handler对象
 * @Modifier:
 * @ModifyContent:
 */
public class HandlerUtils {

    private Handler mhandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            onHandleMessage(msg);
        }
    };

    protected void onHandleMessage(Message msg) {

    }

    public void post(Runnable runnable) {
        mhandler.post(runnable);
    }

    public void postDelayed(Runnable runnable, long delayMillis) {
        mhandler.postDelayed(runnable, delayMillis);
    }

    public void removeMessages(int what, Runnable runnable) {
        if (what != 0) {
            mhandler.removeMessages(what);
        }
        if (runnable != null) {
            mhandler.removeCallbacks(runnable);
        }
    }

    public void removeMessages(int what) {
        removeMessages(what, null);
    }

    public void removeMessages(Runnable runnable) {
        removeMessages(0, runnable);
    }
}
