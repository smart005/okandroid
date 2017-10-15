android开发框架
============
# 该框架主要分为三个基础包 RxCore、RxResource、RxBasicFun [需在Android Studio使用]

## .项目开发中首先在工程的build.gradle中做以下配置:
	buildscript {
	    repositories {
	        jcenter()
	    }
	    dependencies {
	        classpath 'com.android.tools.build:gradle:2.3.3'
	    }
	}

	allprojects {
	    repositories {
	        maven { url 'http://www.slcore.com:8081/nexus/content/groups/mibao-group/' }
	    }
	}

	task clean(type: Delete) {
	    delete rootProject.buildDir
	}

	然后在项目工程中添加以下三个主要引用
	compile 'com.cloud:rxcore-release:1.0.36@aar'
	compile 'com.cloud:rxresources-release:1.0.22@aar'
	compile 'com.cloud:basicfun-release:1.0.13@aar'

	框架中所用到的其它第三方引用：
	compile 'com.android.support:support-v4:23.4.0'
	compile 'com.alibaba:fastjson:1.2.30'
	compile 'com.github.bumptech.glide:glide:3.7.0'
	compile 'com.jakewharton:butterknife:7.0.0'
	compile 'com.github.lzyzsd:circleprogress:1.2.1'
	compile 'com.lzy.net:okgo:2.1.4'
	compile 'com.lzy.net:okrx:0.1.2'
	compile 'com.umeng.analytics:analytics:latest.integration'
	compile 'com.github.lovetuzitong:MultiImageSelector:1.2'
	compile 'com.youth.banner:banner:1.4.9'
	compile 'org.greenrobot:eventbus:3.0.0'
	compile 'com.makeramen:roundedimageview:2.3.0'
	compile 'com.android.support:recyclerview-v7:22.2.1'

## .组件模块

[视差列表控件——头部自定义布局,中间有tab分页,tab分页里有滚动列表如ListView ScrollView RecyclerView等](/docs/parallax_list.md)

[XRefreshRecyclerView[带刷新和加载]](/docs/xrecyclerview.md)

[FlowEditItemsLayout[流式]标签列表](/docs/tag_list.md)

[View任何视图下拉刷新与上拉加载控件](/docs/view_refresh_load.md)


## .项目混淆配置

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
	#***************butterknife start************
	-keep class butterknife.** { *; }
	-dontwarn butterknife.internal.**
	-keepclasseswithmembernames class * {
	    @butterknife.* <fields>;
	}
	-keepclasseswithmembernames class * {
	    @butterknife.* <methods>;
	}
	#*************butterknife end****************
	#***************umeng start******************
	-dontwarn com.umeng.**
	-dontwarn com.tencent.weibo.sdk.**
	-dontwarn com.facebook.**
	-keep enum com.facebook.**
	-keep public interface com.facebook.**
	-keep public interface com.tencent.**
	-keep public interface com.umeng.socialize.**
	-keep public interface com.umeng.socialize.sensor.**
	-keep public interface com.umeng.scrshot.**
	-keep public class com.umeng.socialize.* {*;}
	-keep class com.facebook.**
	-keep class com.facebook.** { *; }
	-keep class com.umeng.scrshot.**
	-keep public class com.tencent.** {*;}
	-keep class com.umeng.socialize.sensor.**
	-keep class com.umeng.socialize.handler.**
	-keep class com.umeng.socialize.handler.*
	-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
	-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
	-keep class com.tencent.** {*;}
	-dontwarn com.tencent.**
	-keep public class com.umeng.soexample.R$*{
	    public static final int *;
	}
	-keep public class com.umeng.soexample.R$*{
	    public static final int *;
	}
	-keep class com.tencent.open.TDialog$*
	-keep class com.tencent.open.TDialog$* {*;}
	-keep class com.tencent.open.PKDialog
	-keep class com.tencent.open.PKDialog {*;}
	-keep class com.tencent.open.PKDialog$*
	-keep class com.tencent.open.PKDialog$* {*;}
	-keep class com.umeng.weixin.handler.**
	-keep class com.umeng.weixin.handler.*
	-keep class com.umeng.qq.handler.**
	-keep class com.umeng.qq.handler.*
	-keep class com.sina.** {*;}
	-dontwarn com.sina.**
	-keep class UMMoreHandler{*;}
	-keep class  com.alipay.share.sdk.** {
	   *;
	}
	-keep class com.tencent.mm.sdk.** {*;}
	-keep class com.tencent.mm.opensdk.** {*;}
	-keep class com.tencent.wxop.** {*;}
	-keep public class com.umeng.com.umeng.soexample.R$*{
	    public static final int *;
	}
	-keep public class com.linkedin.android.mobilesdk.R$*{
	    public static final int *;
	}
	-keep class com.umeng.socialize.impl.ImageImpl {*;}
	#******************umeng end****************
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