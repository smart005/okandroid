应用程序进入后台或前台监听
-----
```java
我们都知道判断应用程序进入后台或前台是非常简单，但监听应用程序是否进入后台或前台并没有现成的
方法和事件来处理；
那么在该框架中只要对继承了BaseApplication的项目Application重载以下方法即可；

@Override
protected void onAppSiwtchToBack() {
    //应用程序进入后台
}

@Override
protected void onAppSiwtchToFront() {
    //应用程序进入前台
}
```