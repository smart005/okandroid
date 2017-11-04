网络请求使用配置
-----
```java
在实际开发中，我们知道接口的请求与定义往往比较散乱很难统一到一起；直接混杂在业务逻辑当中；
现在几个比较流行的网络框架比如Rxjava、okhttp、Okgo等相应的提供比较便历的链式写法，
但这种写法如果直接在方法或业务中写的话发现重用性并不高，而且代码比较乱相同接口可能会在多个地方
copy一堆的代码；效率不是很高；

根据不同情况和场景提供OkRx网络请求框架，主要有以下几个特点：
1.接口的地址及参数配置继承了OkHttp的优点兼可在interface文件类中定义；
2.同一项目中支持多微服务即多个基地址同时共存；
3.同一接口可配置多个api请求url;[解决请求参数类似且返回对象类型相同的接口]
4.网络验证、token验证、接口数据缓存等且可用注解的方式来配置；使用方便减少了大量的业务代码；
5.api数据返回码(code=200,500...)可指定接口配置；

除了以上几点外在使用时还有其它方便之处，只有在使用了才知道；下面就列出每种情况
在不同场景下的使用方式；(支付GET POST DELETE PATCH PUT)
```
###### 1.在配置我们先来介绍一下如何创建inteface类和请求服务类
```java
inteface类我们需要根据业务模块进行分，因为对应该接口类上要加基础url和参数提交类型等配置；
至于请求服务类最好也跟inteface类对应关联即可；(下面我们就举个例子来说明吧)
//inteface类创建
@BaseUrlTypeName(value = ApiCodes.API_URL_TYPE_NAME, tokenName = "token", contentType = RequestContentType.Json)
public interface ITestAPI {
	//这里定义你自己的接口
}
BaseUrlTypeName注解中参数的含义：
value:		baseUrl地址标识;
tokenName:	在同一项目中可能会存在不同服务请求的情况，当然可能每个服务token名称不相同，
			那么可以在这里指定；因此就有了些参数；
contentType:该类下所有定义接口的参数都按照此类来提交(form或json)

//服务类创建,这比接口定义简单多了，只要继承OkgoService即可；
public class TestService extends OkgoService {
	//这里写具体的接口对象
}
```
###### 2.基本请求配置
```java
@BaseUrlTypeName(value = ApiCodes.API_URL_TYPE_NAME, tokenName = "token", contentType = RequestContentType.Json)
public interface ITestAPI {
	@GET(value = "/rest/version")//url地址配置
    @DataParam(value = VersionBean.class)//接口数据返回类型
    RetrofitParams requestOutsideUrl(
    	//参数定义
    	@Param("versionName") String versionName,
        @Param("deviceNumber") String deviceNumber
    );
    //其中RetrofitParams为固定返回值每个接口都一样,内部使用；
}
```
```java
public class TestService extends OkgoService {
	@ApiCheckAnnotation(
            IsNetworkValid = true,//是否开启验证，即网络不正常情况下会进行toast提示
            IsTokenValid = false,//是否进行token验证,或true时token会自动以
            					//请求头参数的提交至后台
            IsCache = true,//是否启用缓存
            CacheTime = 1,//缓存时间
            CacheTimeUnit = TimeUnit.MINUTES,//缓存时间单位
            CacheKey = "xxx_version_info"//缓存key
    )
    public void passwordLogin(Context context,
                              final String versionName,
                              final String deviceNumber) {
        //RxJava订阅器，每个请求定义都一样比较简单这里就不多讲了；
        BaseSubscriber baseSubscriber = new BaseSubscriber<VersionBean, TestService>(context, this) {
            @Override
            protected void onSuccessful(VersionBean versionBean, String reqKey) {
                //接口返回
            }
        };
        //请求，如果注解中相关的配置验证失败，则不会请求网络；
        requestObject(context, ITestAPI.class, this, baseSubscriber, new Func1<ITestAPI, RetrofitParams>() {
            @Override
            public RetrofitParams call(ITestAPI testAPI) {
            	//请求api中的接口
                return testAPI.requestOutsideUrl(versionName, deviceNumber);
            }
        });
    }
}
//好了请求对象基本上就这样，每个请求几乎都一样除了方法名不同返回的数据不同外；
//下面来重点介绍定义在ITestAPI中的使用；
```
###### 3.可对某接口指定它的请求基地址以及数据提交类型
```java
@GET(value = "/rest/version")

@BaseUrlTypeName(value = "base url",tokenName = "token",contentType = RequestContentType.Form)

@DataParam(value = VersionBean.class)
RetrofitParams requestOutsideUrl(
        @Param("versionName") String versionName,
        @Param("deviceNumber") String deviceNumber
);
```
###### 4.可对某接口指定url为完整地址
```java
//如果是全路径配置，那么在验证配置当中将不拼接baseUrl;
@GET(value = "http://www.xxx.com/rest/version", isFullUrl = true)
@DataParam(value = VersionBean.class)
RetrofitParams requestOutsideUrl(
        @Param("versionName") String versionName,
        @Param("deviceNumber") String deviceNumber
);
```
###### 5.对请求参数类似且数据返回类型相同的接口，可采用地址库形式；
```java
@GET(values = {
        @UrlItem(value = "/rest/order/paid", key = "key1"),
        @UrlItem(value = "/rest/order/canceled", key = "key2"),
        @UrlItem(value = "/rest/order/completed", key = "key3")
})
@DataParam(value = OrderDetailBean.class)
RetrofitParams requestOrderList(
        @Param("urlKey") String urlKey
);
//只要输入urlKey的值是对应key1 key2 key3中的一个那么请求将以相应的url地址来请求返回数据；
```