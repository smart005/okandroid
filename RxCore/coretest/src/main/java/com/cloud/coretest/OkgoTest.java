package com.cloud.coretest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.cloud.core.annotations.ApiCheckAnnotation;
import com.cloud.core.beans.BaseBean;
import com.cloud.core.beans.RetrofitParams;
import com.cloud.core.enums.ResponseType;
import com.cloud.core.okgo.BaseService;
import com.cloud.core.okgo.BaseSubscriber;
import com.cloud.core.okgo.OkgoUtils;
import com.cloud.core.okgo.OkgoValidParam;
import com.cloud.core.okgo.callBack.JrFileCallback;
import com.cloud.coretest.beans.VersionBean;

import java.io.File;

import okhttp3.Response;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/6/8
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class OkgoTest extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.okgo_view);
        View annotationBtn = findViewById(R.id.annotation_btn);
        annotationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestServer testServer = new TestServer();
                testServer.requestOutsideVersion(OkgoTest.this);
            }
        });

        OkgoUtils.getInstance().downloadFile(this, "", new JrFileCallback<Object>() {
            @Override
            public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {

            }

            @Override
            public void onSuccess(File file, Response response, Object extras) {

            }
        }, ResponseType.Rx);
    }

    public class TestServer extends BaseService {
        //https://mobile.findaily.cn/rest/version
        @ApiCheckAnnotation(IsNetworkValid = true)
        public void requestInfo(Context context) {
            BaseSubscriber baseSubscriber = new BaseSubscriber<BaseBean, TestServer>(context, this) {
                @Override
                protected void onSuccessful(BaseBean baseBean) {
                    baseBean.getRcd();
                }
            };
            OkgoValidParam validParam = OkgoValidParsing.check(context, this);
            requestObject(context, TestAPI.class, this, baseSubscriber, validParam, new Func1<String, String>() {
                @Override
                public String call(String apiUrlTypeName) {
                    //获取baseurl实际项目中需要再统一封装个方法
                    return "https://talk.iqiaorong.com/";
                }
            }, new Func1<TestAPI, RetrofitParams>() {
                @Override
                public RetrofitParams call(TestAPI testAPI) {
                    return testAPI.cancelConcernFriends(459338, "54d41d85db2d635900263cbce910122e");
                }
            }, new Func0<String>() {
                @Override
                public String call() {
                    return null;
                }
            });
        }

        @ApiCheckAnnotation(IsNetworkValid = true)
        public void requestOutsideVersion(Context context) {
            BaseSubscriber baseSubscriber = new BaseSubscriber<VersionBean, TestServer>(context, this) {
                @Override
                protected void onSuccessful(VersionBean versionBean) {

                }
            };
            OkgoValidParam validParam = OkgoValidParsing.check(context, this);
            requestObject(context, TestAPI.class, this, baseSubscriber, validParam, new Func1<String, String>() {
                @Override
                public String call(String apiUrlTypeName) {
                    //获取baseurl实际项目中需要再统一封装个方法
                    return "http://mobile.findaily.cn/";
                }
            }, new Func1<TestAPI, RetrofitParams>() {
                @Override
                public RetrofitParams call(TestAPI testAPI) {
                    return testAPI.requestOutsideUrl();
                }
            }, new Func0<String>() {
                @Override
                public String call() {
                    return null;
                }
            });
        }
    }

    public static final String baseUrl = "";
}
