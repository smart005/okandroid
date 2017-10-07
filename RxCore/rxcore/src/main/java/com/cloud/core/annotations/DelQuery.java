package com.cloud.core.annotations;

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
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface DelQuery {
    String value() default "";

    /**
     * 若为true,则默认不添加此请求下的所有字段
     *
     * @return
     */
    boolean isRemoveEmptyValueField() default false;
}
