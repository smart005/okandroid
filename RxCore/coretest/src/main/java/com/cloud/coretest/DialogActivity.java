package com.cloud.coretest;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;

import com.cloud.core.okgo.OkgoUtils;
import com.cloud.core.okgo.callBack.JrJsonArrayCallback;

import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

public class DialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_view);

        final OkgoUtils okgoUtils = OkgoUtils.getInstance();

        final HashMap<String, Object> map = new HashMap<>();
        map.put("searchType", 3);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Response httpResponse = okGoUtils.getHttpResponse(DialogActivity.this, "https://talk.findaily.cn/kol/1.0/article", map);
//                httpResponse.body();
//            }
//        }).start();


//        okGoUtils.getHttp(this, "https://talk.findaily.cn/kol/1.0/article", map, new Action<KolListBean>() {
//            @Override
//            public void execute(KolListBean kolListBean) {
//                kolListBean.getBeanList();
//            }
//        }, KolListBean.class);

//        try {
//            String httpResponse = okGoUtils.getHttpResponseMain(this, "https://talk.findaily.cn/kol/1.0/article",map);
//            Log.i("test", httpResponse);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        long l = SystemClock.currentThreadTimeMillis();

        OkgoUtils.getInstance().getHttpByArray(DialogActivity.this, "https://static.findaily.cn/wanghong/activeRule/kol_ads.json?t=" + l, KolListItem.class, new JrJsonArrayCallback<KolListItem, Object>() {
            @Override
            public void onSuccess(List<KolListItem> kolListItems, Response response, Object extras) {
                if (kolListItems == null) {
                    
                }
            }
        });

        String httpResponse = okgoUtils.getHttpResponseMain(DialogActivity.this, "https://talk.findaily.cn/kol/1.0/article?searchType=3");
        httpResponse.length();


//        okGoUtils.getHttp(this, "https://talk.findaily.cn/kol/1.0/article?searchType=3", new Action<String>() {
//            @Override
//            public void execute(String t1) {
//                Log.i("test", t1);
//            }
//        }, ResponseType.Json);

    }

}
