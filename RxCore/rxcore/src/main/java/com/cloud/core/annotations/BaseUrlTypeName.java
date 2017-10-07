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
 * @CreateTime:2017/6/9
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BaseUrlTypeName {

    String value() default "ApiUrl";

    String tokenName() default "token";

    RequestContentType contentType() default RequestContentType.None;
}
