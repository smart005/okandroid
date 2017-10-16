项目混淆配置
-------

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