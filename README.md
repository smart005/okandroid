Android开发框架
============

<div align=center>

*该框架主要分为三个基础包 RxCore、RxResource、RxBasicFun*
![images](/rxcrb.png)

</div>

### 版本引用
<a href="https://github.com/smart005/RxCore">

![images](https://img.shields.io/badge/Rxcore-1.1.0-brightgreen.svg)
</a><a href="https://github.com/smart005/RxResource">
![images](https://img.shields.io/badge/RxResource-1.1.0-brightgreen.svg)
</a><a href="https://github.com/smart005/RxBasicFun">
![images](https://img.shields.io/badge/RxBasicFun-1.0.46-brightgreen.svg)
</a>
###### 1.在project的build.gradle中分别引用下面库:
```gradle
//网络请求、数据缓存、资源 SP 验证等工具包
compile 'com.cloud:rxcore:1.1.0@aar'
//与rxcore类似,部分带资源的组件在该库中封装
compile 'com.cloud:rxresource:1.1.0@aar'
//项目开发工具包：版本更新、基类、图片压缩、dialogs、阿里云图片上传组件(二次封装调用简单)等
compile 'com.cloud:basicfun:1.0.46@aar'
```
<font face="#FF7F50">æ库引用的其它关联引用请看具体版本引用里配置</font>
### 联系方式
* email:smartljh@aliyun.com
* QQ群:122607882
* 如果遇到问题欢迎在群里提问,尽快回应解决;同时也欢迎大家一起来提建议;

### 文档
###### 1.项目初始配置
*注:代码文档中如果有继承相关基类的必须继承!!!*
* [AndroidManifest.xml配置](/docs/android_manifest_config.md)
* [Application配置](/docs/application_config.md)
* [其他配置](/docs/app_other_config.md)

### 涉及相关库的混淆
```text+
#*****************fastjson start*************
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.**{*;}
#*****************fastjson end***************
#*****************glide start****************
-keep public class * implements com.thirdparty.bumptech.glide.module.GlideModule
-keep public enum com.thirdparty.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}
-keep class com.thirdparty.bumptech.** {
    *;
}
-keep class com.bumptech.glide.integration.okhttp.OkHttpGlideModule
#*****************glide end******************
#***************butterknife start************
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
#*************butterknife end****************
#*****************okhttp start***************
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *;}
-dontwarn com.zhy.http.**
-keep class com.zhy.http.**{*;}
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
-dontwarn okio.**
-keep class okio.**{*;}
#*****************okhttp end*****************
#*****************rxjava start***************
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
 long producerIndex;
 long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
#*****************rxjava end*****************
#*****************banner start**************
-keep class com.youth.banner.** {*;}
#*****************banner end****************
#*****************eventbus start************
-keep class org.simple.** { *; }
-keep interface org.simple.** { *; }
-keepclassmembers class * {
    @org.simple.eventbus.Subscriber <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode {*;}
-keep class org.greenrobot.eventbus.** {*;}
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    public <init>(java.lang.Throwable);
}
-dontwarn org.greenrobot.eventbus.util.*$Support
-dontwarn org.greenrobot.eventbus.util.*$SupportManagerFragment
#*****************eventbus end**************
#****************aliyun oss start***************
-keep class com.alibaba.sdk.android.oss.** { *; }
-dontwarn org.apache.commons.codec.binary.**
#****************aliyun oss end***************
#****************greenDao start***********
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties
# If you do not use SQLCipher:
-dontwarn org.greenrobot.greendao.database.**
# If you do not use Rx:
-dontwarn rx.**
#****************greenDao end*************
#****************x5 start*****************
-dontwarn dalvik.**
-dontwarn com.tencent.smtt.**
-keep class com.tencent.smtt.export.external.**{
    *;
}
-keep class com.tencent.tbs.video.interfaces.IUserStateChangedListener {
	*;
}
-keep class com.tencent.smtt.sdk.CacheManager {
	public *;
}
-keep class com.tencent.smtt.sdk.CookieManager {
	public *;
}
-keep class com.tencent.smtt.sdk.WebHistoryItem {
	public *;
}
-keep class com.tencent.smtt.sdk.WebViewDatabase {
	public *;
}
-keep class com.tencent.smtt.sdk.WebBackForwardList {
	public *;
}
-keep public class com.tencent.smtt.sdk.WebView {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.WebView$HitTestResult {
	public static final <fields>;
	public java.lang.String getExtra();
	public int getType();
}
-keep public class com.tencent.smtt.sdk.WebView$WebViewTransport {
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.WebView$PictureListener {
	public <fields>;
	public <methods>;
}
-keepattributes InnerClasses
-keep public enum com.tencent.smtt.sdk.WebSettings$** {
    *;
}
-keep public enum com.tencent.smtt.sdk.QbSdk$** {
    *;
}
-keep public class com.tencent.smtt.sdk.WebSettings {
    public *;
}
-keepattributes Signature
-keep public class com.tencent.smtt.sdk.ValueCallback {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.WebViewClient {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.DownloadListener {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.WebChromeClient {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.WebChromeClient$FileChooserParams {
	public <fields>;
	public <methods>;
}
-keep class com.tencent.smtt.sdk.SystemWebChromeClient{
	public *;
}
# 1. extension interfaces should be apparent
-keep public class com.tencent.smtt.export.external.extension.interfaces.* {
	public protected *;
}

# 2. interfaces should be apparent
-keep public class com.tencent.smtt.export.external.interfaces.* {
	public protected *;
}
-keep public class com.tencent.smtt.sdk.WebViewCallbackClient {
	public protected *;
}
-keep public class com.tencent.smtt.sdk.WebStorage$QuotaUpdater {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.WebIconDatabase {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.WebStorage {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.DownloadListener {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.QbSdk {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.QbSdk$PreInitCallback {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.CookieSyncManager {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.Tbs* {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.utils.LogFileUtils {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.utils.TbsLog {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.utils.TbsLogClient {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.CookieSyncManager {
	public <fields>;
	public <methods>;
}
# Added for game demos
-keep public class com.tencent.smtt.sdk.TBSGamePlayer {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.TBSGamePlayerClient* {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.TBSGamePlayerClientExtension {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.TBSGamePlayerService* {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.utils.Apn {
	public <fields>;
	public <methods>;
}
-keep class com.tencent.smtt.** {
	*;
}
# end
-keep public class com.tencent.smtt.export.external.extension.proxy.ProxyWebViewClientExtension {
	public <fields>;
	public <methods>;
}
-keep class MTT.ThirdAppInfoNew {
	*;
}
-keep class com.tencent.mtt.MttTraceEvent {
	*;
}
# Game related
-keep public class com.tencent.smtt.gamesdk.* {
	public protected *;
}
-keep public class com.tencent.smtt.sdk.TBSGameBooter {
        public <fields>;
        public <methods>;
}
-keep public class com.tencent.smtt.sdk.TBSGameBaseActivity {
	public protected *;
}
-keep public class com.tencent.smtt.sdk.TBSGameBaseActivityProxy {
	public protected *;
}
-keep public class com.tencent.smtt.gamesdk.internal.TBSGameServiceClient {
	public *;
}
#****************x5 end*******************
```
[想看完整版混淆,请点击这里](/docs/confounding.md)

### Licenses
```text
Copyright 2017 chester(李敬欢)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```