广播使用
-----
###### 1.设置广播接收key
*[在应用程序初始时已设置，具体配置请点击这里](/docs/app_other_config.md)*
###### 2.广播发送
```java
void BaseRedirectUtils.sendBroadcast(Context context, String action,String permission, Bundle bundle);
void BaseRedirectUtils.sendBroadcast(Context context, String action,Bundle bundle);
void BaseRedirectUtils.sendBroadcast(Context context, Bundle bundle);
//其中params最终以json形式传入至接收处；以RECEIVE_DATA作为键接收；
void BaseRedirectUtils.sendBroadcast(Context context, String action,T... params);
```
###### 3.广播接收
```java
//在需要接收广播的Activity或Fragment下重载receiveRSCResult方法
@Override
protected void receiveRSCResult(Intent intent) {
    ....
}

@Override
protected void receiveRSCResult(Intent intent, String action) {
    ...
}
```
*注：广播的注册与销毁在框架中已处理，无需再处理；*