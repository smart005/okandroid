网络验证工具类
---------
###### 1.在使用工具之前需添加以下权限
```xml
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
```

###### 1.方法使用
```java
//存在可用网络但未连接
boolean NetworkUtils.hasAvailable(Context context);
```