package com.cloud.basicfun.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/2/10
 * @Description: 通知事件action
 * com.rongxun.action.notification.event
 * @Modifier:
 * @ModifyContent:
 */
public abstract class OnNotificationBroadcast extends BroadcastReceiver {

    protected void onClickEvent(Context context, Intent intent) {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TextUtils.equals(intent.getAction(), "com.rongxun.action.notification.event")) {
            onClickEvent(context, intent);
        }
    }
}
