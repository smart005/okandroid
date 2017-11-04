网络请求初始化配置
-------
###### 1.在Application中初始化okgo网络基础包
```java
public class xxxxxxx extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //okgo网络框架
        OkgoBase.getInstance().init(this, new OkgoConfigBean());
    }
}
```
###### 2.网络检查配置类
```java
public class OkgoValidParsing {
    public static <T extends BaseService> OkgoValidParam check(final Context context, T t) {
        return BaseOkgoValidParsing.getInstance().check(context, t, new Action<T>() {
            @Override
            public void execute(T t) {
            	//设置用户token,当网络请求需要授权的接口使用
                //t.setToken();
            }
        });
    }
}
```
###### 3.配置请求服务类(必须继承BaseService)
```java
public class OkgoService extends BaseService {
    /**
     * 网络请求配置
     *
     * @param context        上下文
     * @param apiClass       接口定义类Class
     * @param server         请求接口服务类，一般传this
     * @param baseSubscriber 数据返回订阅器
     * @param decApiAction   定义接口回调方法，用来调用apiClass中的接口
     * @param <I>            接口泛型类
     * @param <S>            接口服务泛型类
     */
    public <I, S extends BaseService> void requestObject(Context context,
                                                         Class<I> apiClass,
                                                         S server,
                                                         BaseSubscriber baseSubscriber,
                                                         Func1<I, RetrofitParams> decApiAction) {
        //网络请求前检查，网络、token以及是否需要缓存
        OkgoValidParam validParam = OkgoValidParsing.check(context, server);
        //请求接口
        requestObject(context, apiClass, server, baseSubscriber, validParam, new Func1<String, String>() {
            @Override
            public String call(String apiUrlTypeName) {
                //根据apiClass中定义的标识来返回对应的基础地址
                return "";
            }
        }, decApiAction);
    }
}
```

*至此网络请求的配置基本完成了；那么如何使用呢请点击以下连接*
*[网络请求使用方式](/docs/okrx_use.md)*