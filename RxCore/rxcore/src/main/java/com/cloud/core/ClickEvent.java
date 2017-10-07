package com.cloud.core;


import com.cloud.core.logger.Logger;

public class ClickEvent {

    private static int intervaltime = 500;
    private static long lastClickTime = 0;
    private static int viewid = 0;

    public static boolean isFastDoubleClick(int resid) {
        try {
            if (resid != 0) {
                if (viewid != resid) {
                    viewid = resid;
                    lastClickTime = 0;
                }
            }
            long time = System.currentTimeMillis();
            long timeD = time - lastClickTime;
            if (0 < timeD && timeD < intervaltime) {
                return true;
            }
            lastClickTime = time;
        } catch (Exception e) {
            Logger.L.error("click event error:", e.getMessage());
        }
        return false;
    }

    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(0);
    }
}
