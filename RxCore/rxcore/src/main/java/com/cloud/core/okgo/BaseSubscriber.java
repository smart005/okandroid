package com.cloud.core.okgo;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.cloud.core.beans.BaseBean;
import com.cloud.core.logger.Logger;
import com.cloud.core.ret.BaseSysCode;
import com.cloud.core.utils.BaseRedirectUtils;
import com.cloud.core.utils.JsonUtils;
import com.cloud.core.utils.StorageUtils;
import com.cloud.core.utils.ToastUtils;

import java.io.File;

import rx.Subscriber;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/6/14
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public abstract class BaseSubscriber<T extends BaseBean, BaseT extends BaseService> extends Subscriber<T> {

    private Context context = null;
    private BaseT baseT = null;
    private String reqKey = "";
    private String apiName = "";
    private Object extra = null;
    /**
     * 启动登录页时间戳
     */
    public static long START_LOGIN_TIME_STMPT = 0;
    /**
     * 未登录接口对应的本地文件名
     */
    private static String API_UNLOGIN_FILE_NAME = "6cedf5f448c84b528f7cbbb72ac691d5.txt";
    private static String API_ERROR_FILE_NAME = "680b2ce7ba2e4873b9de9b7ce9d301d5";

    public void onCacheSuccessful(T t) {
        onSuccessful(t);
        onSuccessful(t, reqKey);
        onSuccessful(t, reqKey, extra);
    }

    public void setReqKey(String reqKey) {
        this.reqKey = reqKey;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }

    /**
     * API请求成功
     *
     * @param t
     * @param reqKey 请求唯一标识符
     * @param extra  额外数据
     */
    protected void onSuccessful(T t, String reqKey, Object extra) {

    }

    /**
     * API请求成功
     *
     * @param t
     * @param reqKey 请求唯一标识符
     */
    protected void onSuccessful(T t, String reqKey) {

    }

    /**
     * API请求成功
     *
     * @param t
     */
    protected void onSuccessful(T t) {

    }

    /**
     * API请求失败
     *
     * @param t
     */
    protected void onFailure(T t) {

    }

    /**
     * API请求异常
     *
     * @param throwable 异常信息
     */
    protected void onErrored(Throwable throwable) {

    }

    /**
     * API请求完成(结束)
     */
    protected void onFinished() {

    }

    /**
     * api调用失败
     *
     * @param t
     */
    protected void onApiFailure(T t) {

    }

    public <ExtraT> BaseSubscriber(Context context, BaseT cls) {
        this.context = context;
        this.baseT = cls;
        if (this.baseT != null) {
            this.baseT.setBaseSubscriber(this);
        }
    }

    @Override
    public void onCompleted() {
        onFinished();
        if (baseT != null) {
            baseT.onRequestCompleted();
        }
    }

    @Override
    public void onError(Throwable e) {
        onErrored(e);
        if (baseT != null) {
            baseT.onRequestError(e);
            baseT.onRequestCompleted();
        }
    }

    @Override
    public void onNext(T t) {
        try {
            if (t == null) {
                return;
            }
            if (TextUtils.isEmpty(apiName)) {
                apiName = baseT.getApiName();
            }
            if (TextUtils.isEmpty(t.getRcd()) ||
                    BaseSysCode.API_RET.contains(t.getRcd()) ||
                    BaseSysCode.API_SPECIFIC_NAME_FILTER.contains(apiName)) {
                onSuccessful(t);
                onSuccessful(t, reqKey);
                onSuccessful(t, reqKey, extra);
            } else {
                if (BaseSysCode.API_UNLOGIN.contains(t.getRcd())) {
                    recordAPIClassInfo(t, API_UNLOGIN_FILE_NAME);
                    long currtime = System.currentTimeMillis();
                    if (START_LOGIN_TIME_STMPT == 0 || ((currtime - START_LOGIN_TIME_STMPT) > 3000)) {
                        if (context != null) {
                            START_LOGIN_TIME_STMPT = currtime;
                            Bundle bundle = new Bundle();
                            bundle.putBoolean(BaseSysCode.START_LOGIN_FLAG_KEY, true);
                            bundle.putString(BaseSysCode.API_NAME, apiName);
                            bundle.putString(BaseSysCode.API_RESULT_JSON, JsonUtils.toStr(t));
                            BaseRedirectUtils.sendBroadcast(context, bundle);
                            if (baseT != null) {
                                //请求token api请求中的token清空
                                baseT.setToken("");
                            }
                        }
                    }
                } else {
                    if (!TextUtils.isEmpty(t.getRcd()) &&
                            !BaseSysCode.API_MESSAGE_PROMPT_FILTER.contains(t.getRcd())) {
                        if (context != null) {
                            recordAPIClassInfo(t, API_ERROR_FILE_NAME);
                            ToastUtils.showLong(context, t.getRmg(), -100);
                        }
                    } else {
                        onSuccessful(t);
                        onSuccessful(t, reqKey);
                        onSuccessful(t, reqKey, extra);
                    }
                }
            }
        } catch (Exception e) {
            Logger.L.warn("interface success ret error:", e);
        }
    }

    private void recordAPIClassInfo(T t, String fileName) {
        try {
            String className = t.getClass().getName();
            File tobeProcessedDir = StorageUtils.getTobeProcessed();
            File tobeFile = new File(tobeProcessedDir, fileName);
            StringBuffer unprocesssb = new StringBuffer();
            unprocesssb.append("\n");
            unprocesssb.append(apiName);
            unprocesssb.append("\n");
            unprocesssb.append(className);
            unprocesssb.append("\n");
            unprocesssb.append(JsonUtils.toStr(t));
            unprocesssb.append("\n");
            unprocesssb.append("--------------------------------------------------------------------------------------------------------------------");
            unprocesssb.append("\n");
            StorageUtils.appendContent(unprocesssb.toString(), tobeFile);
        } catch (Exception e) {
            Logger.L.warn("record api unlogin class info error:", e);
        }
    }
}
