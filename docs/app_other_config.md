项目初始化其他配置
------
###### 1.在应用程序启动页面初始化框架相关配置(最好不要写在Application中)
```java
//获取配置对象
RxConfig config = RxCoreUtils.getInstance().getConfig(getActivity());
//设置应用程序广播接收key
config.setRscReceiveAction("广播唯一标识码");
//设置app icon图标
config.setAppIcon(R.drawable.appicon);
//设置项目主题颜色
config.setThemeColorResId(R.color.colorPrimary);
//设置需要apk提示更新的Activity名称,具体配置请看下面
config.setUpdateActivityNamesResId(R.array.updateActivityNames);
//配置断网状态下不提示的activity名称,具体配置请看下面
config.setNetStateActivityNamesResId(R.array.netStateActivityNames);
//设置【图片】使用的第三方平台类型
config.setPlatformType(PlatformType.Alibaba);
//设置项目中使用的token名称
config.setTokenName("token");
//设置apk更新信息请求url地址
config.setRequestUpdateInfoUrl("");
//api配置
ApiConfig apiConfig = config.getApiConfig();
//对未登录状态下，需要授权的接口；添加接口返回码过滤；也就是说此返回码不做校验；
apiConfig.getApiUnlogin().add("api返回码");
//对接口非200的状态下，添加返回码过滤；也就是接口返回码是500时，message消息字段在app端不做ToastUtils处理；
apiConfig.getApiMessagePromptFilter().add("500");
//资源配置
ResConfig resConfig = config.getResConfig();
//设置默认背影
resConfig.setDefBg(R.drawable.def_bg);
//保存配置信息
RxCoreUtils.getInstance().init(getActivity(), rxConfig);
```

```xml
//R.array.updateActivityNames
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string-array name="updateActivityNames">
        <item>activity名称</item>
    </string-array>
</resources>
```

```xml
//R.array.netStateActivityNames
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string-array name="netStateActivityNames">
        <item>LoginActivity</item>
        <item>RegisterActivity</item>
        <item>SplashActivity</item>
        <item>GuidePageActivity</item>
        <item>WirelessPromptActivity</item>
    </string-array>
</resources>
```
###### 2.在应用程序主界面Main创建广播接收，对未登录状态下所调用的接口自动跳转至登录页面
```java
@Override
protected void receiveRSCResult(Intent intent) {
    RxConfig config = RxCoreUtils.getInstance().getConfig(getActivity());
    if (intent.getBooleanExtra(config.getStartLoginFlagKey(), false)) {
        //打开登录页面
        RedirectUtils.startActivity(getActivity(), LoginActivity.class);
    }
}
```