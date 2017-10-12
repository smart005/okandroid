package com.cloud.basicfun.utils;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import com.cloud.basicfun.enums.AnalyticsType;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.AppInfoUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.social.UMPlatformData;

import java.util.HashMap;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/8/17
 * @Description:统计
 * @Modifier:
 * @ModifyContent:
 */
public class AnalyticsProcess {

    /**
     * 在Application onCreate中初始
     *
     * @param context      application 上下文
     * @param appkey       umeng统计appkey
     * @param channelId    渠道id
     * @param scenarioType 场景类型
     *                     EScenarioType. E_UM_NORMAL　　普通统计场景类型
     *                     EScenarioType. E_UM_GAME     　　游戏场景类型
     *                     EScenarioType. E_UM_ANALYTICS_OEM  统计盒子场景类型
     *                     EScenarioType. E_UM_GAME_OEM      　 游戏盒子场景类型
     * @param isDebug      是否启用调式模式(集成测试查看需要设为true)
     */
    public void instance(Context context, String appkey, String channelId, MobclickAgent.EScenarioType scenarioType, boolean isDebug) {
        MobclickAgent.UMAnalyticsConfig config = new MobclickAgent.UMAnalyticsConfig(context, appkey, channelId, scenarioType, true);
        MobclickAgent.startWithConfigure(config);
        /**
         * 退到后台或进入其它应用10分钟后重新记为session
         */
        MobclickAgent.setSessionContinueMillis(600000);
        /**
         * 禁用Activity自动统计
         */
        MobclickAgent.openActivityDurationTrack(false);
        /**
         * 设置成非加密模式
         */
        MobclickAgent.enableEncrypt(false);
        /**
         * 错误统计
         */
        MobclickAgent.setCatchUncaughtExceptions(true);
        /**
         * 集成测试查看
         */
        if (isDebug) {
            MobclickAgent.setDebugMode(isDebug);
        }
    }

    /**
     * 在Application onCreate中初始
     * 默认为{@link com.umeng.analytics.MobclickAgent.EScenarioType.E_UM_ANALYTICS_OEM}
     *
     * @param context   application 上下文
     * @param appkey    umeng统计appkey
     * @param channelId 渠道id
     * @param isDebug   是否启用调式模式(集成测试查看需要设为true)
     */
    public void instance(Context context, String appkey, String channelId, boolean isDebug) {
        instance(context, appkey, channelId, MobclickAgent.EScenarioType.E_UM_ANALYTICS_OEM, isDebug);
    }

    /**
     * 在Application onCreate中初始
     * 默认为{@link com.umeng.analytics.MobclickAgent.EScenarioType.E_UM_ANALYTICS_OEM}
     *
     * @param isDebug 是否启用调式模式(集成测试查看需要设为true)
     */
    public void instance(Application application, boolean isDebug) {
        try {
            String appkey = "";
            String channelId = "";
            Bundle mbundle = AppInfoUtils.getApplicationMetaData(application);
            if (mbundle.containsKey("UMENG_APPKEY")) {
                appkey = mbundle.getString("UMENG_APPKEY");
                if (TextUtils.isEmpty(appkey)) {
                    return;
                }
            } else {
                return;
            }
            if (mbundle.containsKey("UMENG_CHANNEL")) {
                channelId = mbundle.getString("UMENG_CHANNEL");
                if (TextUtils.isEmpty(channelId)) {
                    return;
                }
            } else {
                return;
            }
            instance(application.getApplicationContext(), appkey, channelId, isDebug);
        } catch (Exception e) {
            Logger.L.error("instance umeng analytics error:", e);
        }
    }

    /**
     * 实际项目无需调用，已在基类中调用
     *
     * @param context
     * @param analyticsType 统计类型
     */
    public void onResume(Context context, AnalyticsType analyticsType) {
        if (analyticsType == AnalyticsType.StatisticalPage || analyticsType == AnalyticsType.All) {
            Class<?> cls = context.getClass();
            if (cls != null) {
                String className = cls.getName();
                if (!TextUtils.isEmpty(className)) {
                    MobclickAgent.onPageStart(className);
                }
            }
        }
        if (analyticsType == AnalyticsType.StatisticalTime || analyticsType == AnalyticsType.All) {
            MobclickAgent.onResume(context);
        }
    }

    /**
     * 实际项目无需调用，已在基类中调用
     *
     * @param context
     */
    public void onResume(Context context) {
        onResume(context, AnalyticsType.All);
    }

    /**
     * 实际项目无需调用，已在基类中调用
     *
     * @param context
     * @param analyticsType
     */
    public void onPause(Context context, AnalyticsType analyticsType) {
        if (analyticsType == AnalyticsType.StatisticalPage || analyticsType == AnalyticsType.All) {
            Class<?> cls = context.getClass();
            if (cls != null) {
                String className = cls.getName();
                if (!TextUtils.isEmpty(className)) {
                    MobclickAgent.onPageEnd(className);
                }
            }
        }
        if (analyticsType == AnalyticsType.StatisticalTime || analyticsType == AnalyticsType.All) {
            MobclickAgent.onPause(context);
        }
    }

    /**
     * 实际项目无需调用，已在基类中调用
     *
     * @param context
     * @param analyticsType
     */
    public void onPause(Context context) {
        onPause(context, AnalyticsType.All);
    }

    /**
     * Process.kill或者System.exit之前调用
     */
    public void onKillProcess(Context context) {
        //MobclickAgent.onKillProcess(context);
    }

    /**
     * 帐号登录统计
     *
     * @param accountProvider 账号来源。如果用户通过第三方账号登陆，可以调用此接口进行统计。支持自定义，不能以下划线"_"开头，使用大写字母和数字标识，长度小于32 字节; 如果是上市公司，建议使用股票代码
     * @param accountID       用户账号ID，长度小于64字节
     */
    public void onProfileSignIn(String accountProvider, String accountID) {
        MobclickAgent.onProfileSignIn(accountProvider, accountID);
    }

    /**
     * 帐号登录统计
     *
     * @param accountID 用户账号ID，长度小于64字节
     */
    public void onProfileSignIn(String accountID) {
        MobclickAgent.onProfileSignIn(accountID);
    }

    /**
     * 账号登出时需调用此接口，调用之后不再发送账号相关内容
     */
    public void onProfileSignOff() {
        MobclickAgent.onProfileSignOff();
    }

    /**
     * 事件统计
     *
     * @param context 当前activity或fragment
     * @param eventId 事件id
     */
    public void onEvent(Context context, String eventId) {
        MobclickAgent.onEvent(context, eventId);
    }

    /**
     * 统计点击行为各属性被触发的次数
     *
     * @param context 当前activity或fragment
     * @param eventId 事件id
     * @param map     为当前事件的属性和取值（Key-Value键值对）
     */
    public void onEvent(Context context, String eventId, HashMap map) {
        MobclickAgent.onEvent(context, eventId, map);
    }

    /**
     * 统计数值型变量的值的分布
     *
     * @param context
     * @param eventId 事件id
     * @param map     为当前事件的属性和取值
     * @param du      为当前事件的数值为当前事件的数值，取值范围是-2,147,483,648 到 +2,147,483,647 之间的有符号整数，即int 32类型，如果数据超出了该范围，会造成数据丢包，影响数据统计的准确性
     */
    public void onEventValue(Context context, String eventId, HashMap<String, String> map, int du) {
        MobclickAgent.onEventValue(context, eventId, map, du);
    }

    /**
     * 提交错误报告
     *
     * @param context
     * @param error
     */
    public void reportError(Context context, String error) {
        MobclickAgent.reportError(context, error);
    }

    /**
     * 提交错误报告
     *
     * @param context
     * @param error
     */
    public void reportError(Context context, Throwable error) {
        MobclickAgent.reportError(context, error);
    }

    /**
     * 社交统计
     *
     * @param context
     * @param platform
     */
    public void onSocialEvent(Context context, UMPlatformData platform) {
        MobclickAgent.onSocialEvent(context, platform);
    }
}
