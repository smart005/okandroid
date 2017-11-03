项目初始配置
-----
###### 1.在代码配置之前先看一下AndroidManifest.xml相关配置
```java
//包配置最好在manifest节点属性中加上
android:sharedUserId="应用程序包名"
```

```xml
//示例
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:tools="http://schemas.android.com/tools"
package="应用程序包名"
android:sharedUserId="应用程序包名">
```

```xml
//当然在使用的过程中还可以使用框架提供的默认主题
<application
android:name="这里是你自己的Application"
...
android:theme="@style/defaultTheme">
```

```xml
//添加glide缓存配置
<meta-data
android:name="com.cloud.core.glides.GlideConfiguration"
android:value="GlideModule" />
```

```xml
//另外我们项目中Activity跳转的时候都是需要动画的，我们也可以用框架提供的默认动画
<activity
	...
	android:theme="@style/activity_anim" />
```

*好了到这里AndroidManifest.xml框架中涉及的相关配置基本完成了，其它配置可自行添加.*