package com.cloud.basicfun.utils;

import android.content.Context;
import android.text.TextUtils;

import com.cloud.basicfun.beans.APIReturnResult;
import com.cloud.basicfun.beans.ArgFieldItem;
import com.cloud.basicfun.beans.H5GetAPIMethodArgsBean;
import com.cloud.basicfun.enums.APIRequestState;
import com.cloud.core.ObjectJudge;
import com.cloud.core.enums.RuleParams;
import com.cloud.core.logger.Logger;
import com.cloud.core.okgo.OkgoUtils;
import com.cloud.core.okgo.callBack.JrJsonCallback;
import com.cloud.core.utils.JsonUtils;
import com.cloud.core.utils.PathsUtils;
import com.cloud.core.utils.ValidUtils;

import java.util.HashMap;

import okhttp3.Response;
import rx.functions.Action2;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/1/4
 * @Description:H5交互API工具类
 * @Modifier:
 * @ModifyContent:
 */
public class H5InteractionAPIUtils {

    /**
     * H5请求api接口方法
     *
     * @param context
     * @param baseUrl 请求api的基地址
     * @param extras  {"apiName":"login",
     *                "args":[{"fieldName":"userName","fieldValue":"test","fieldType":"string"},
     *                {"fieldName":"password","fieldValue":"123456","fieldType":"string"}]}
     */
    public static void getAPIMethod(Context context, String baseUrl, String extras, final Action2<APIRequestState, APIReturnResult> callback) {
        try {
            if (context == null || !ValidUtils.valid(RuleParams.Url.getValue(), baseUrl) || TextUtils.isEmpty(extras)) {
                return;
            }
            H5GetAPIMethodArgsBean h5GetAPIMethodArgsBean = JsonUtils.parseT(extras, H5GetAPIMethodArgsBean.class);
            if (h5GetAPIMethodArgsBean == null || TextUtils.isEmpty(h5GetAPIMethodArgsBean.getApiName())) {
                return;
            }
            String url = PathsUtils.combine(baseUrl, h5GetAPIMethodArgsBean.getApiName());
            HashMap<String, Object> params = new HashMap<String, Object>();
            if (!ObjectJudge.isNullOrEmpty(h5GetAPIMethodArgsBean.getArgs())) {
                for (ArgFieldItem argFieldItem : h5GetAPIMethodArgsBean.getArgs()) {
                    if (!params.containsKey(argFieldItem.getFieldName())) {
                        params.put(argFieldItem.getFieldName(), argFieldItem.getFieldValue());
                    }
                }
            }

            class H5PostCallback extends JrJsonCallback<String, Object> {

                private Action2<APIRequestState, APIReturnResult> mCallback = null;
                private H5GetAPIMethodArgsBean h5GetAPIMethodArgsBean = null;

                public H5PostCallback(Action2<APIRequestState, APIReturnResult> callback, H5GetAPIMethodArgsBean h5GetAPIMethodArgsBean) {
                    this.mCallback = callback;
                    this.h5GetAPIMethodArgsBean = h5GetAPIMethodArgsBean;
                }

                @Override
                public void onSuccess(String result, Response response, Object extras) {
                    if (mCallback != null) {
                        APIReturnResult apiReturnResult = new APIReturnResult();
                        apiReturnResult.setResponse(result);
                        apiReturnResult.setTarget(h5GetAPIMethodArgsBean == null ? "" : h5GetAPIMethodArgsBean.getTarget());
                        mCallback.call(APIRequestState.Success, apiReturnResult);
                    }
                }

                @Override
                public void onError(Response response, Exception e, Object extras) {
                    if (callback != null) {
                        APIReturnResult result = new APIReturnResult();
                        H5GetAPIMethodArgsBean h5GetAPIMethodArgsBean = (H5GetAPIMethodArgsBean) extras;
                        result.setTarget(h5GetAPIMethodArgsBean == null ? "" : h5GetAPIMethodArgsBean.getTarget());
                        callback.call(APIRequestState.Error, result);
                    }
                }

                @Override
                public void onAfter(Object extras) {
                    if (callback != null) {
                        callback.call(APIRequestState.Complate, null);
                    }
                }
            }
            OkgoUtils.getInstance().getHttpByJson(context,
                    url,
                    params,
                    String.class,
                    new H5PostCallback(callback, h5GetAPIMethodArgsBean),
                    h5GetAPIMethodArgsBean);
        } catch (Exception e) {
            Logger.L.error("h5 get api method error:", e);
        }
    }
}
