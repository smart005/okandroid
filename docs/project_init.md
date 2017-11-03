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