package com.cloud.basicfun.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.cloud.core.logger.Logger;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/9/26
 * @Description:通知管理
 * @Modifier:
 * @ModifyContent:
 */

public class RxNotification {

    private Handler mhandler = new Handler();

    /**
     * 通知
     *
     * @param context
     * @param notificationProperties
     * @param pendingIntent          PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, ResultActivity.class), 0);
     */
    public static void notification(Context context, RxNotificationProperties notificationProperties, PendingIntent pendingIntent) {
        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setTicker(TextUtils.isEmpty(notificationProperties.getTickerText()) ? notificationProperties.getTitle() : notificationProperties.getTickerText());
            builder.setContentTitle(TextUtils.isEmpty(notificationProperties.getTitle()) ? "今日金融" : notificationProperties.getTitle());
            builder.setContentText(notificationProperties.getText());
            builder.setSmallIcon(notificationProperties.getIcon());
            if (notificationProperties.getLargeIcon() != null && notificationProperties.getLargeIcon().asBitmap() != null) {
                builder.setLargeIcon(notificationProperties.getLargeIcon().asBitmap());
            }
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingIntent);
            builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
            Notification notification = builder.build();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            notification.vibrate = new long[]{150, 300, 150, 600};
            notification.defaults = Notification.DEFAULT_ALL;
            notificationManager.notify(notificationProperties.getNotificationId(), notification);
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "TAG");
            wakeLock.acquire();
        } catch (Exception e) {
            Logger.L.error("notification process error:", e);
        }
    }

    public static void cancel(Context context, int notificationId) {
        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.cancel(notificationId);
        } catch (Exception e) {
            Logger.L.error("cancel notification process error:", e);
        }
    }

    public static PendingIntent getPendingIntent(Context context, Intent intent) {
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static PendingIntent getBroadcastPendingIntent(Context context, Intent intent) {
        intent.setAction("com.rongxun.action.notification.event");
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
