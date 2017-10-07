package com.cloud.core.annotations;

import com.cloud.core.okgo.RequestContentType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/6/6
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PATCH {
    String value() default "";

    /**
     * 若为true,则默认不添加此请求下的所有字段
     *
     * @return
     */
    boolean isRemoveEmptyValueField() default false;

    /**
     * 是否完整url
     *
     * @return
     */
    boolean isFullUrl() default false;

    /**
     * 是否配置基础url
     * 1.只有isFullUrl为false是才生效;
     * 2.若为true且当前配置了BaseUrlTypeName则从该接口的BaseUrlTypeName中取；
     * 否则从全局类定义的基础url中取；
     * 3.若isFullUrl为false且isConfigBaseUrl为true;但实际url为全路径，则安实际
     * 值为准;
     *
     * @return
     */
    boolean isConfigBaseUrl() default false;

    /**
     * 是否获取基础url
     * true:对于注解配置的url均无效;
     *
     * @return
     */
    boolean isGetBaseUrl() default false;

    /**
     * 数据提交方式
     *
     * @return
     */
    RequestContentType contentType() default RequestContentType.None;
}
