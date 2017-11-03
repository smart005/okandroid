Application配置
------
###### 1.继承BaseApplication
###### 2.onCreate配置
```java
@Override
public void onCreate() {
    super.onCreate();
    //注册action回调参数,框架中用到
    registerAction();
    //添加so静态包,框架中用到
    System.loadLibrary("rxcloud");
}

private void registerAction() {
    setTokenAction(new Func0<String>() {
        @Override
        public String call() {
        	//返回token或session值
            return "";
        }
    });
}
```