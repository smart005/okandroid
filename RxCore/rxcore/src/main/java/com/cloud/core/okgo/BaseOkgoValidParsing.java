package com.cloud.core.okgo;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.cloud.core.Action;
import com.cloud.core.ObjectJudge;
import com.cloud.core.annotations.ApiCheckAnnotation;
import com.cloud.core.annotations.BaseUrlTypeName;
import com.cloud.core.annotations.DELETE;
import com.cloud.core.annotations.DataParam;
import com.cloud.core.annotations.DelQuery;
import com.cloud.core.annotations.GET;
import com.cloud.core.annotations.Header;
import com.cloud.core.annotations.HeaderPart;
import com.cloud.core.annotations.Headers;
import com.cloud.core.annotations.PATCH;
import com.cloud.core.annotations.POST;
import com.cloud.core.annotations.PUT;
import com.cloud.core.annotations.Param;
import com.cloud.core.annotations.Path;
import com.cloud.core.beans.RetrofitParams;
import com.cloud.core.enums.RequestType;
import com.cloud.core.enums.RuleParams;
import com.cloud.core.ret.BaseSysCode;
import com.cloud.core.utils.BaseRedirectUtils;
import com.cloud.core.utils.NetworkUtils;
import com.cloud.core.utils.PathsUtils;
import com.cloud.core.utils.ValidUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Func1;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/6/7
 * @Description:okgo请求验证
 * @Modifier:
 * @ModifyContent:
 */
public class BaseOkgoValidParsing {

    private static BaseOkgoValidParsing validParsing = null;

    public static BaseOkgoValidParsing getInstance() {
        return validParsing == null ? validParsing = new BaseOkgoValidParsing() : validParsing;
    }

    public <T extends BaseService> OkgoValidParam check(Context context, T t, Action configAction, Action<T> tokenAction) {
        OkgoValidParam validParam = new OkgoValidParam();
        try {
            if (t == null) {
                validParam.setFlag(false);
            }
            Method[] methods = t.getClass().getMethods();
            if (ObjectJudge.isNullOrEmpty(methods)) {
                validParam.setFlag(false);
            }
            String invokeMethodName = getInvokingMethodName();
            if (TextUtils.isEmpty(invokeMethodName)) {
                validParam.setFlag(false);
            }
            for (Method method : methods) {
                if (!TextUtils.equals(method.getName(), invokeMethodName)) {
                    continue;
                }
                if (method.isAnnotationPresent(ApiCheckAnnotation.class)) {
                    Annotation[] annotations = method.getDeclaredAnnotations();
                    //若未定义注解则不作任何校验
                    if (ObjectJudge.isNullOrEmpty(annotations)) {
                        validParam.setFlag(true);
                        break;
                    }
                    ApiCheckAnnotation apiCheckAnnotation = method.getAnnotation(ApiCheckAnnotation.class);
                    if (apiCheckAnnotation == null) {
                        //若注解获取为空则不作任何校验
                        validParam.setFlag(true);
                        break;
                    }
                    BaseSubscriber subscriber = t.getBaseSubscriber();
                    if (subscriber == null) {
                        //若未注册请求定阅则结束请求
                        validParam.setFlag(false);
                        break;
                    }
                    //检查网络
                    if (apiCheckAnnotation.IsNetworkValid()) {
                        if (NetworkUtils.isConnected(context)) {
                            //拼接当前调用方法参数
                            //拼接缓存key
                            validParam.setCacheKey(String.format("%s_{0}_%s_%s",
                                    t.getClass().getSimpleName(),
                                    invokeMethodName, apiCheckAnnotation.CacheKey()));
                            validParam.setApiCheckAnnotation(apiCheckAnnotation);
                            if (configAction != null) {
                                configAction.execute(null);
                            }
                            if (apiCheckAnnotation.IsTokenValid()) {
                                //若当前服务中的token不为空则重新获取
                                if (tokenAction == null) {
                                    //若需要token时tokenAction为空则结束请求
                                    validParam.setFlag(false);
                                } else {
                                    tokenAction.execute(t);
                                    if (TextUtils.isEmpty(t.getToken())) {
                                        long currtime = System.currentTimeMillis();
                                        if (t.getBaseSubscriber().START_LOGIN_TIME_STMPT == 0 || ((currtime - t.getBaseSubscriber().START_LOGIN_TIME_STMPT) > 3000)) {
                                            if (context != null) {
                                                t.getBaseSubscriber().START_LOGIN_TIME_STMPT = currtime;
                                                Bundle bundle = new Bundle();
                                                bundle.putBoolean(BaseSysCode.START_LOGIN_FLAG_KEY, true);
                                                bundle.putString(BaseSysCode.API_NAME, invokeMethodName);
                                                BaseRedirectUtils.sendBroadcast(context, bundle);
                                            }
                                        }
                                        validParam.setFlag(false);
                                    } else {
                                        validParam.setFlag(true);
                                    }
                                }
                                break;
                            } else {
                                validParam.setFlag(true);
                                if (tokenAction != null) {
                                    tokenAction.execute(t);
                                }
                                break;
                            }
                        } else {
                            //无网络结束请求
                            validParam.setFlag(false);
                            break;
                        }
                    } else {
                        //若无网络则不作请求处理
                        validParam.setFlag(false);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            validParam.setFlag(false);
        }
        return validParam;
    }

    /**
     * 获取调用方法名
     *
     * @return
     */
    public static String getInvokingMethodName() {
        Exception exception = new Exception();
        if (exception == null) {
            return "";
        }
        StackTraceElement[] stacks = exception.getStackTrace();
        if (ObjectJudge.isNullOrEmpty(stacks)) {
            return "";
        }
        String[] fms = {"getInvokingMethodName", "check", "requestObject"};
        List<String> fmslst = Arrays.asList(fms);
        String methodName = "";
        for (StackTraceElement stack : stacks) {
            if (fmslst.contains(stack.getMethodName())) {
                continue;
            } else {
                methodName = stack.getMethodName();
                break;
            }
        }
        return methodName;
    }

    public <T> T createAPI(Class<T> service, Func1<String,
            String> urlAction, boolean isGetBaseUrl) {
        if (!ValidUtils.validateServiceInterface(service)) {
            return null;
        }
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new ApiInvocationHandler(service, urlAction, isGetBaseUrl));
    }

    private class ApiInvocationHandler<T> implements InvocationHandler {

        private Class<T> apiClass = null;
        private Func1<String, String> urlAction = null;
        private boolean isGetBaseUrl = false;

        public ApiInvocationHandler(Class<T> apiClass,
                                    Func1<String, String> urlAction,
                                    boolean isGetBaseUrl) {
            this.apiClass = apiClass;
            this.urlAction = urlAction;
            this.isGetBaseUrl = isGetBaseUrl;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
            try {
                if (method.getDeclaringClass() == Object.class) {
                    return method.invoke(this, args);
                } else if (method.getReturnType() == RetrofitParams.class) {
                    RetrofitParams retrofitParams = new RetrofitParams();
                    retrofitParams.setIsGetBaseUrl(isGetBaseUrl);
                    Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
                    if (ObjectJudge.isNullOrEmpty(declaredAnnotations)) {
                        return null;
                    }
                    Annotation requestAnnotation = getRequestAnnotation(declaredAnnotations);
                    if (requestAnnotation == null) {
                        //若不符合接口请求则结束请求
                        return null;
                    }
                    //此isRemoveEmptyValueField为接口请求全局变量,默认为false
                    boolean isRemoveEmptyValueField = false;
                    for (Annotation declaredAnnotation : declaredAnnotations) {
                        if (declaredAnnotation.annotationType() == POST.class) {
                            POST annotation = method.getAnnotation(POST.class);
                            if (TextUtils.isEmpty(annotation.value())) {
                                return null;
                            }
                            isRemoveEmptyValueField = annotation.isRemoveEmptyValueField();
                            retrofitParams.setRequestType(RequestType.POST);
                            bindRequestAnnontation(apiClass,
                                    method,
                                    retrofitParams,
                                    args,
                                    annotation.value(),
                                    annotation.isFullUrl(),
                                    annotation.isConfigBaseUrl(),
                                    annotation.isGetBaseUrl(),
                                    urlAction);
                        } else if (declaredAnnotation.annotationType() == GET.class) {
                            GET annotation = method.getAnnotation(GET.class);
                            if (TextUtils.isEmpty(annotation.value())) {
                                return null;
                            }
                            isRemoveEmptyValueField = annotation.isRemoveEmptyValueField();
                            retrofitParams.setRequestType(RequestType.GET);
                            bindRequestAnnontation(apiClass,
                                    method,
                                    retrofitParams,
                                    args,
                                    annotation.value(),
                                    annotation.isFullUrl(),
                                    annotation.isConfigBaseUrl(),
                                    annotation.isGetBaseUrl(),
                                    urlAction);
                        } else if (declaredAnnotation.annotationType() == DELETE.class) {
                            DELETE annotation = method.getAnnotation(DELETE.class);
                            if (TextUtils.isEmpty(annotation.value())) {
                                return null;
                            }
                            isRemoveEmptyValueField = annotation.isRemoveEmptyValueField();
                            retrofitParams.setRequestType(RequestType.DELETE);
                            bindRequestAnnontation(apiClass,
                                    method,
                                    retrofitParams,
                                    args,
                                    annotation.value(),
                                    annotation.isFullUrl(),
                                    annotation.isConfigBaseUrl(),
                                    annotation.isGetBaseUrl(),
                                    urlAction);
                        } else if (declaredAnnotation.annotationType() == PUT.class) {
                            PUT annotation = method.getAnnotation(PUT.class);
                            if (TextUtils.isEmpty(annotation.value())) {
                                return null;
                            }
                            isRemoveEmptyValueField = annotation.isRemoveEmptyValueField();
                            retrofitParams.setRequestType(RequestType.PUT);
                            bindRequestAnnontation(apiClass,
                                    method,
                                    retrofitParams,
                                    args,
                                    annotation.value(),
                                    annotation.isFullUrl(),
                                    annotation.isConfigBaseUrl(),
                                    annotation.isGetBaseUrl(),
                                    urlAction);
                        } else if (declaredAnnotation.annotationType() == PATCH.class) {
                            PATCH annotation = method.getAnnotation(PATCH.class);
                            if (TextUtils.isEmpty(annotation.value())) {
                                return null;
                            }
                            isRemoveEmptyValueField = annotation.isRemoveEmptyValueField();
                            retrofitParams.setRequestType(RequestType.PATCH);
                            bindRequestAnnontation(apiClass,
                                    method,
                                    retrofitParams,
                                    args,
                                    annotation.value(),
                                    annotation.isFullUrl(),
                                    annotation.isConfigBaseUrl(),
                                    annotation.isGetBaseUrl(),
                                    urlAction);
                        } else if (declaredAnnotation.annotationType() == Header.class) {
                            bindHeaderAnnontation(method, retrofitParams, args, isRemoveEmptyValueField);
                        } else if (declaredAnnotation.annotationType() == Headers.class) {
                            bindHeadersAnnontation(method, retrofitParams, args, isRemoveEmptyValueField);
                        } else if (declaredAnnotation.annotationType() == DataParam.class) {
                            DataParam annotation = method.getAnnotation(DataParam.class);
                            retrofitParams.setDataClass(annotation.value());
                        }
                    }
                    //获取参数集合
                    bindParamAnnontation(method, retrofitParams,
                            args, isRemoveEmptyValueField, requestAnnotation.annotationType());
                    return retrofitParams;
                } else {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }
        }
    }

    private void bindParamAnnontation(Method method,
                                      RetrofitParams retrofitParams,
                                      Object[] args,
                                      boolean isRemoveEmptyValueField,
                                      Class<? extends Annotation> annotationType) {
        HashMap<Param, Integer> paramAnnotationObject = getParamAnnotationObject(method, Param.class);
        if (!ObjectJudge.isNullOrEmpty(paramAnnotationObject)) {
            HashMap<String, Object> params = retrofitParams.getParams();
            for (Map.Entry<Param, Integer> paramIntegerEntry : paramAnnotationObject.entrySet()) {
                Param key = paramIntegerEntry.getKey();
                if (!params.containsKey(key.value())) {
                    Object arg = args[paramIntegerEntry.getValue()];
                    if (key.isRemoveEmptyValueField()) {
                        if (!TextUtils.isEmpty(String.valueOf(arg))) {
                            params.put(key.value(), arg);
                        }
                    } else {
                        if (isRemoveEmptyValueField) {
                            if (!TextUtils.isEmpty(String.valueOf(arg))) {
                                params.put(key.value(), arg);
                            }
                        } else {
                            params.put(key.value(), arg);
                        }
                    }
                }
            }
        }
        if (annotationType == DELETE.class) {
            //delete 请求时加query注解时将参数拼接到url后面
            HashMap<DelQuery, Integer> delQueryIntegerHashMap = getParamAnnotationObject(method, DelQuery.class);
            if (!ObjectJudge.isNullOrEmpty(delQueryIntegerHashMap)) {
                HashMap<String, String> params = retrofitParams.getDelQueryParams();
                for (Map.Entry<DelQuery, Integer> delQueryIntegerEntry : delQueryIntegerHashMap.entrySet()) {
                    DelQuery key = delQueryIntegerEntry.getKey();
                    if (!params.containsKey(key.value())) {
                        Object arg = args[delQueryIntegerEntry.getValue()];
                        if (key.isRemoveEmptyValueField()) {
                            if (!TextUtils.isEmpty(String.valueOf(arg))) {
                                params.put(key.value(), String.valueOf(arg));
                            }
                        } else {
                            if (isRemoveEmptyValueField) {
                                if (!TextUtils.isEmpty(String.valueOf(arg))) {
                                    params.put(key.value(), String.valueOf(arg));
                                }
                            } else {
                                params.put(key.value(), String.valueOf(arg));
                            }
                        }
                    }
                }
            }
        }
    }

    private <T> void bindRequestAnnontation(Class<T> apiClass,
                                            Method method,
                                            RetrofitParams retrofitParams,
                                            Object[] args,
                                            String formatUrl,
                                            boolean isFullUrl,
                                            boolean isConfigBaseUrl,
                                            boolean isGetBaseUrl,
                                            Func1<String, String> urlAction) {
        String pattent = String.format(RuleParams.MatchTagBetweenContent.getValue(), "\\{", "\\}");
        List<String> matches = ValidUtils.matches(pattent, formatUrl);
        if (ObjectJudge.isNullOrEmpty(matches)) {
            retrofitParams.setFlag(true);
            matchRequestUrl(apiClass,
                    method,
                    retrofitParams,
                    formatUrl,
                    isFullUrl,
                    isConfigBaseUrl,
                    isGetBaseUrl,
                    urlAction);
        } else {
            String rativeUrl = formatUrl;
            HashMap<Path, Integer> paramAnnotationObject = getParamAnnotationObject(method, Path.class);
            if (!ObjectJudge.isNullOrEmpty(paramAnnotationObject)) {
                for (Map.Entry<Path, Integer> pathIntegerEntry : paramAnnotationObject.entrySet()) {
                    Path path = pathIntegerEntry.getKey();
                    if (matches.contains(path.value())) {
                        rativeUrl = rativeUrl.replace(String.format("{%s}", path.value()), "{0}");
                        rativeUrl = MessageFormat.format(rativeUrl, String.valueOf(args[pathIntegerEntry.getValue()]));
                        //只要有参数则判断为校验成功
                        retrofitParams.setFlag(true);
                    }
                }
                matchRequestUrl(apiClass,
                        method,
                        retrofitParams,
                        rativeUrl,
                        isFullUrl,
                        isConfigBaseUrl,
                        isGetBaseUrl,
                        urlAction);
            } else {
                retrofitParams.setFlag(false);
            }
        }
    }

    private <T> void matchRequestUrl(Class<T> apiClass,
                                     Method method,
                                     RetrofitParams retrofitParams,
                                     String url,
                                     boolean isFullUrl,
                                     boolean isConfigBaseUrl,
                                     boolean isGetBaseUrl,
                                     Func1<String, String> urlAction) {
        retrofitParams.setApiName(url);
        if (isFullUrl) {
            retrofitParams.setRequestUrl(url);
            retrofitParams.setIsJoinUrl(false);
            retrofitParams.setIsGetBaseUrl(false);
        } else {
            RequestContentType contentType = RequestContentType.None;
            BaseUrlTypeName urlTypeName = method.getAnnotation(BaseUrlTypeName.class);
            if (urlTypeName == null) {
                //全局基础地址配置
                urlTypeName = apiClass.getAnnotation(BaseUrlTypeName.class);
                if (urlTypeName != null) {
                    contentType = urlTypeName.contentType();
                }
            } else {
                //获取content-type
                if (urlTypeName.contentType() == RequestContentType.None) {
                    BaseUrlTypeName annotation = apiClass.getAnnotation(BaseUrlTypeName.class);
                    if (annotation != null) {
                        contentType = annotation.contentType();
                    }
                } else {
                    contentType = urlTypeName.contentType();
                }
            }
            if (urlTypeName == null) {
                //未配置局部和全局基础地址
                retrofitParams.setFlag(false);
            } else {
                retrofitParams.setUrlTypeName(urlTypeName);
                retrofitParams.setRequestContentType(contentType);
                if (!isGetBaseUrl && isConfigBaseUrl) {
                    String baseUrl = urlAction.call(urlTypeName.value());
                    if (TextUtils.isEmpty(baseUrl)) {
                        retrofitParams.setFlag(false);
                    } else {
                        retrofitParams.setRequestUrl(PathsUtils.combine(baseUrl, url));
                        retrofitParams.setIsJoinUrl(false);
                    }
                } else {
                    retrofitParams.setRequestUrl(url);
                    if (ValidUtils.valid(RuleParams.Url.getValue(), url)) {
                        retrofitParams.setIsJoinUrl(false);
                    } else {
                        retrofitParams.setIsJoinUrl(true);
                    }
                }
            }
        }
    }

    private void bindHeaderAnnontation(Method method, RetrofitParams retrofitParams, Object[] args, boolean isRemoveEmptyValueField) {
        Header annotation = method.getAnnotation(Header.class);
        HashMap<String, String> headParams = retrofitParams.getHeadParams();
        if (!headParams.containsKey(annotation.name()) &&
                !TextUtils.isEmpty(annotation.value())) {
            String pattent = String.format(RuleParams.MatchTagBetweenContent.getValue(), "\\{", "\\}");
            String matche = ValidUtils.matche(pattent, annotation.value());
            if (TextUtils.isEmpty(matche)) {
                if (annotation.isRemoveEmptyValueField()) {
                    if (!TextUtils.isEmpty(annotation.value())) {
                        headParams.put(annotation.name(), annotation.value());
                    }
                } else {
                    if (isRemoveEmptyValueField) {
                        if (!TextUtils.isEmpty(annotation.value())) {
                            headParams.put(annotation.name(), annotation.value());
                        }
                    } else {
                        headParams.put(annotation.name(), annotation.value());
                    }
                }
            } else {
                HashMap<HeaderPart, Integer> paramAnnotationObject = getParamAnnotationObject(method, HeaderPart.class);
                if (!ObjectJudge.isNullOrEmpty(paramAnnotationObject)) {
                    for (Map.Entry<HeaderPart, Integer> headerPartIntegerEntry : paramAnnotationObject.entrySet()) {
                        HeaderPart headerPart = headerPartIntegerEntry.getKey();
                        if (TextUtils.equals(headerPart.value(), matche)) {
                            String dataValue = String.valueOf(args[headerPartIntegerEntry.getValue()]);
                            if (headerPart.isRemoveEmptyValueField()) {
                                if (!TextUtils.isEmpty(dataValue)) {
                                    headParams.put(annotation.name(), dataValue);
                                }
                            } else {
                                if (isRemoveEmptyValueField) {
                                    if (!TextUtils.isEmpty(dataValue)) {
                                        headParams.put(annotation.name(), dataValue);
                                    }
                                } else {
                                    headParams.put(annotation.name(), dataValue);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void bindHeadersAnnontation(Method method, RetrofitParams retrofitParams, Object[] args, boolean isRemoveEmptyValueField) {
        Headers annotation = method.getAnnotation(Headers.class);
        if (annotation != null) {
            String[] values = annotation.value();
            if (!ObjectJudge.isNullOrEmpty(values)) {
                HashMap<String, String> headParams = retrofitParams.getHeadParams();
                for (int i = 0; i < values.length; i++) {
                    String value = values[i];
                    String[] lst = value.split(":");
                    if (lst.length == 2 && !TextUtils.isEmpty(lst[0])
                            && !TextUtils.isEmpty(lst[1]) && !headParams.containsKey(lst[0])) {
                        String pattent = String.format(RuleParams.MatchTagBetweenContent.getValue(), "\\{", "\\}");
                        String matche = ValidUtils.matche(pattent, lst[1]);
                        if (TextUtils.isEmpty(matche)) {
                            if (annotation.isRemoveEmptyValueField()) {
                                if (!TextUtils.isEmpty(lst[1])) {
                                    headParams.put(lst[0], lst[1]);
                                }
                            } else {
                                if (isRemoveEmptyValueField) {
                                    if (!TextUtils.isEmpty(lst[1])) {
                                        headParams.put(lst[0], lst[1]);
                                    }
                                } else {
                                    headParams.put(lst[0], lst[1]);
                                }
                            }
                        } else {
                            //取HeaderPart中的内容
                            HashMap<HeaderPart, Integer> paramAnnotationObject = getParamAnnotationObject(method, HeaderPart.class);
                            if (!ObjectJudge.isNullOrEmpty(paramAnnotationObject)) {
                                for (Map.Entry<HeaderPart, Integer> headerPartIntegerEntry : paramAnnotationObject.entrySet()) {
                                    HeaderPart headerPart = headerPartIntegerEntry.getKey();
                                    if (TextUtils.equals(headerPart.value(), matche)) {
                                        String dataValue = String.valueOf(args[headerPartIntegerEntry.getValue()]);
                                        if (annotation.isRemoveEmptyValueField()) {
                                            if (!TextUtils.isEmpty(dataValue)) {
                                                headParams.put(lst[0], dataValue);
                                            }
                                        } else {
                                            if (isRemoveEmptyValueField) {
                                                if (!TextUtils.isEmpty(dataValue)) {
                                                    headParams.put(lst[0], dataValue);
                                                }
                                            } else {
                                                headParams.put(lst[0], dataValue);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private Annotation getRequestAnnotation(Annotation[] declaredAnnotations) {
        Annotation annotation = null;
        for (Annotation declaredAnnotation : declaredAnnotations) {
            if (declaredAnnotation.annotationType() == POST.class ||
                    declaredAnnotation.annotationType() == GET.class ||
                    declaredAnnotation.annotationType() == DELETE.class ||
                    declaredAnnotation.annotationType() == PUT.class ||
                    declaredAnnotation.annotationType() == PATCH.class) {
                annotation = declaredAnnotation;
                break;
            }
        }
        return annotation;
    }

    private <T> HashMap<T, Integer> getParamAnnotationObject(Method method, Class<T> annotationClass) {
        HashMap<T, Integer> lst = new HashMap<T, Integer>();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (!ObjectJudge.isNullOrEmpty(parameterAnnotations)) {
            for (int i = 0; i < parameterAnnotations.length; i++) {
                Annotation[] parameterAnnotation = parameterAnnotations[i];
                if (ObjectJudge.isNullOrEmpty(parameterAnnotation)) {
                    continue;
                }
                if (parameterAnnotation[0].annotationType() == annotationClass) {
                    if (!lst.containsKey(parameterAnnotation[0])) {
                        lst.put((T) parameterAnnotation[0], i);
                    }
                }
            }
        }
        return lst;
    }
}
