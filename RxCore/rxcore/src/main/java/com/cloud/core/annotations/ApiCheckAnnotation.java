package com.cloud.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/8/19
 * @Description:接口请求验证
 * @Modifier:
 * @ModifyContent:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiCheckAnnotation {
    /**
     * 网络验证,默认为false
     *
     * @return true:已连接;false:未连接;
     */
    boolean IsNetworkValid() default false;

    /**
     * token验证,默认为false;
     *
     * @return true:自动取token值并传入;false:不传;
     */
    boolean IsTokenValid() default false;

    /**
     * 是否缓存
     *
     * @return
     */
    boolean IsCache() default false;

    /**
     * 缓存时间
     *
     * @return
     */
    long CacheTime() default 0;

    /**
     * 缓存时间单位
     *
     * @return
     */
    TimeUnit CacheTimeUnit() default TimeUnit.SECONDS;

    /**
     * 缓存key
     *
     * @return
     */
    String CacheKey() default "859800517";

    /**
     * 是否获取基础url
     * true:对于注解配置的url均无效;
     *
     * @return
     */
    boolean isGetBaseUrl() default false;
}
