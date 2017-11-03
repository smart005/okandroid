项目初始配置
-----
###### 1.在代码配置之前先看一下AndroidManifest.xml相关配置
	* 包配置
	//最好在manifest节点属性中加上
```java
android:sharedUserId="应用程序包名"
```

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:tools="http://schemas.android.com/tools"
package="应用程序包名"
android:sharedUserId="应用程序包名">
```