package com.cloud.core.utils;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/11/2
 * @Description:停留时间
 * @Modifier:
 * @ModifyContent:
 */

public class ResidenceTimeUtils {
    /**
     * 总的停留时间
     */
    private long TOTAL_RESIDENCE_TIME = 3000;
    /**
     * 开始时间
     */
    private long START_TIME = 0;

    public interface OnResidenceTimeCallback {
        /**
         * @param timeRemaining 剩余时间
         */
        public void onTimeRemainingCallback(int timeRemaining);
    }

    private OnResidenceTimeCallback onResidenceTimeCallback = null;

    public void setStartTime(long startTime) {
        this.START_TIME = startTime > 0 ? startTime : 0;
    }

    /**
     * 设置停留时间监听
     *
     * @param totalResidenceTime 总的停留时间
     * @param callback           回调处理
     */
    public void setOnResidenceTimeListener(long totalResidenceTime, OnResidenceTimeCallback callback) {
        if (totalResidenceTime > 0) {
            this.TOTAL_RESIDENCE_TIME = totalResidenceTime;
        }
        this.onResidenceTimeCallback = callback;
        long difftime = System.currentTimeMillis() - START_TIME;
        if (difftime >= TOTAL_RESIDENCE_TIME) {
            if (onResidenceTimeCallback != null) {
                onResidenceTimeCallback.onTimeRemainingCallback(0);
            }
        } else {
            if (onResidenceTimeCallback != null) {
                int time = (int) (TOTAL_RESIDENCE_TIME - difftime);
                onResidenceTimeCallback.onTimeRemainingCallback(time);
            }
        }
    }

    /**
     * 设置停留时间监听
     *
     * @param callback 回调处理
     */
    public void setOnResidenceTimeListener(OnResidenceTimeCallback callback) {
        setOnResidenceTimeListener(0, callback);
    }
}
