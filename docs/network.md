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
//检查网络是否成功连接
boolean NetworkUtils.isConnected(Context context);
//飞行模式下返回值为-1， 否则返回值为ConnectivityManager.TYPE_MOBILE或ConnectivityManager.TYPE_WIFI
int NetworkUtils.getConnectedType(Context context);
//获取网络连接类型:-1未连接;1:wifi;2,3:移动网络;
List<Integer> NetworkUtils.getAPNType(Context context);
```