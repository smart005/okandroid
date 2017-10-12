package com.cloud.basicfun.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.cloud.core.ObjectJudge;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.JsonUtils;

import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/7/16
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public abstract class BaseContentProvider extends ContentProvider {

    protected UriMatcher uriMatcher = new UriMatcher();

    @Override
    public boolean onCreate() {
        return true;
    }

    protected String onGetContent(Uri uri, int code) {
        return "";
    }

    protected String onGetContent(Uri uri, int code, List<ContentProviderQueryWhereItem> whereItems) {
        return "";
    }

    protected String onGetContent(Uri uri, int code, ContentProviderQueryWhereItem whereItem) {
        return "";
    }

    protected int onGetContent(Uri uri, int code, String content) {
        return 0;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        try {
            int code = uriMatcher.match(uri);
            return onGetContent(uri, code);
        } catch (Exception e) {
            Logger.L.error("get provider data error:", e);
        }
        return "";
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        try {
            int code = uriMatcher.match(uri);
            List<ContentProviderQueryWhereItem> whereItems = null;
            if (values != null &&
                    values.containsKey(BaseContentProviderUtils.QUERY_CONDITION_DATA_KEY)) {
                String where = values.getAsString(BaseContentProviderUtils.QUERY_CONDITION_DATA_KEY);
                whereItems = JsonUtils.parseArray(where, ContentProviderQueryWhereItem.class);
            }
            if (ObjectJudge.isNullOrEmpty(whereItems)) {
                return Uri.EMPTY;
            }
            if (whereItems.size() > 1) {
                return Uri.parse(onGetContent(uri, code, whereItems));
            } else {
                ContentProviderQueryWhereItem queryWhereItem = whereItems.get(0);
                if (queryWhereItem.isExtrasQuery()) {
                    return Uri.parse(onGetContent(uri, code, queryWhereItem));
                } else {
                    return Uri.parse(onGetContent(uri, code, whereItems));
                }
            }
        } catch (Exception e) {
            Logger.L.error("query data error:", e);
        }
        return Uri.EMPTY;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String content, String[] selectionArgs) {
        try {
            int code = uriMatcher.match(uri);
            return onGetContent(uri, code, content);
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return 0;
    }
}
