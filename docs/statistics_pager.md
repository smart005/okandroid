页面统计——变得简单
-----
```java
我们知道集成第三方统计时相对比较麻烦，如友盟、腾讯云、baidu云等；那有没有第三方平台一键集成的？答案是肯定的——GrowingIO,但是该平台是收费的且相对有点贵；为此才有了在该框架中的统计处理方案；

具体操作步骤如下（前提项目Application必须继承框架中的BaseApplication）：
```
*[下面以友盟统计为例]*
###### 1.注册行为统计事件
```java
@Override
public void onCreate() {
    super.onCreate();
    //行为统计
    behaviorStatistics();
}
private void behaviorStatistics() {
    super.setOnBehaviorStatistics(new OnBehaviorStatistics() {
        @Override
        public <T> void onPageStatistics(T t, LifeCycleStatus lifeCycleStatus) {
            //提供友盟统计工具类，如果其它统计平台请自行添加
            UmengAnalytics.getInstance().statistics(t, lifeCycleStatus);
        }
    });
}
```
###### 2.UmengAnalytics统计工具类(其它统计平台请自行添加)
```java
public class UmengAnalytics {

    private static UmengAnalytics umengAnalytics = null;

    public static UmengAnalytics getInstance() {
        return umengAnalytics == null ? umengAnalytics = new UmengAnalytics() : umengAnalytics;
    }

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
    private void instance(Context context,
                          String appkey,
                          String channelId,
                          MobclickAgent.EScenarioType scenarioType,
                          boolean isDebug) {
        MobclickAgent.UMAnalyticsConfig config = new MobclickAgent.UMAnalyticsConfig(
                context, appkey, channelId, scenarioType, true);
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
        MobclickAgent.enableEncrypt(true);
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

    private void instance(Context context, String appkey, String channelId, boolean isDebug) {
        instance(context, appkey, channelId, MobclickAgent.EScenarioType.E_UM_ANALYTICS_OEM, isDebug);
    }

    public void instance() {
        try {
            MibaoApplication application = MibaoApplication.getInstance();
            boolean release = CommonUtils.isRelease();
            String appkey = AppInfoUtils.getMetaString(application,
                    release ? "UMENG_APPKEY" : "UMENG_TESTING_APPKEY");
            //该渠道可以从AndroidManifest.xml中获取
            String channelId = CommonUtils.getChannelName("");
            instance(application.getApplicationContext(), appkey, channelId, !release);
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    /**
     * 实际项目无需调用，已在基类中调用
     *
     * @param context
     * @param analyticsType 统计类型
     */
    private void onResume(Context context, AnalyticsType analyticsType) {
        if (analyticsType == AnalyticsType.StatisticalPage || analyticsType == AnalyticsType.All) {
            Class<?> cls = context.getClass();
            if (cls != null) {
                String className = cls.getName();
                if (!TextUtils.isEmpty(className)) {
                    MobclickAgent.onPageStart(className);
                }
            }
            MobclickAgent.onResume(context);
        }
    }

    /**
     * 实际项目无需调用，已在基类中调用
     *
     * @param context
     * @param analyticsType
     */
    private void onPause(Context context, AnalyticsType analyticsType) {
        if (analyticsType == AnalyticsType.StatisticalPage || analyticsType == AnalyticsType.All) {
            Class<?> cls = context.getClass();
            if (cls != null) {
                String className = cls.getName();
                if (!TextUtils.isEmpty(className)) {
                    MobclickAgent.onPageEnd(className);
                }
            }
            MobclickAgent.onPause(context);
        }
    }

    /**
     * Process.kill或者System.exit之前调用
     */
    public void onKillProcess(Context context) {
        MobclickAgent.onKillProcess(context);
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

    public <T> void statistics(T t, LifeCycleStatus lifeCycleStatus) {
        try {
            if (t instanceof BaseActivity || t instanceof BaseAppCompatActivity) {
                Activity activity = (Activity) t;
                if (lifeCycleStatus == LifeCycleStatus.Resume) {
                    onResume(activity, AnalyticsType.All);
                } else if (lifeCycleStatus == LifeCycleStatus.Pause) {
                    onPause(activity, AnalyticsType.All);
                }
            } else if (t instanceof BaseFragmentActivity) {
                Context context = (Context) t;
                if (lifeCycleStatus == LifeCycleStatus.Resume) {
                    MobclickAgent.onResume(context);
                } else if (lifeCycleStatus == LifeCycleStatus.Pause) {
                    MobclickAgent.onPause(context);
                }
            } else if (t instanceof BaseFragment) {
                BaseFragment fragment = (BaseFragment) t;
                fragmentAnalytics(fragment, lifeCycleStatus);
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    private void fragmentAnalytics(Fragment fragment, LifeCycleStatus lifeCycleStatus) {
        if (fragment == null) {
            return;
        }
        Context context = fragment.getContext();
        if (context == null) {
            return;
        }
        if (lifeCycleStatus == LifeCycleStatus.Resume) {
            Class<?> cls = context.getClass();
            if (cls != null) {
                String className = cls.getName();
                if (!TextUtils.isEmpty(className)) {
                    MobclickAgent.onPageStart(className);
                }
            }
        } else if (lifeCycleStatus == LifeCycleStatus.Pause) {
            Class<?> cls = context.getClass();
            if (cls != null) {
                String className = cls.getName();
                if (!TextUtils.isEmpty(className)) {
                    MobclickAgent.onPageEnd(className);
                }
            }
        }
    }
}
```
###### 3.在应用程序启动或Application中初始化友盟统计
```java
UmengAnalytics.getInstance().instance();
```

*[好了到此基本统计就算完成了，致于事件统计需要根据第三方平台规则自行添加.]*