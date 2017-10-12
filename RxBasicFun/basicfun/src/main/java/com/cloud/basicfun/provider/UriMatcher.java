package com.cloud.basicfun.provider;

import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/7/16
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class UriMatcher {

    private HashMap<String, MatchItem> datalist = new HashMap<String, MatchItem>();

    public void addUri(String uri, String action, int code) {
        if (TextUtils.isEmpty(uri) ||
                TextUtils.isEmpty(action)) {
            return;
        }
        MatchItem item = new MatchItem();
        item.setUri(uri);
        item.setAction(action);
        item.setCode(code);
        datalist.put(action, item);
    }

    public int match(Uri uri) {
        if (uri == null) {
            return -1;
        }
        String action = uri.getLastPathSegment();
        if (TextUtils.isEmpty(action)) {
            return -1;
        }
        if (datalist.containsKey(action)) {
            MatchItem item = datalist.get(action);
            if (item == null) {
                return -1;
            } else {
                return item.getCode();
            }
        } else {
            return -1;
        }
    }
}
