package com.cloud.basicfun.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.cloud.core.logger.Logger;
import com.cloud.core.utils.JsonUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/5/6
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class BaseContentProviderUtils {

    /**
     * 查询条件数据key
     */
    public static String QUERY_CONDITION_DATA_KEY = "WHERE_DATA";

    /**
     * @param context
     * @param action
     * @param content
     */
    public static void submit(Context context, String hostPackageName, String action, String content) {
        try {
            if (context == null || TextUtils.isEmpty(action) || TextUtils.isEmpty(content)) {
                return;
            }
            ContentResolver contentResolver = context.getContentResolver();
            if (contentResolver == null) {
                return;
            }
            Uri uri = Uri.parse(MessageFormat.format("content://{0}/{1}", hostPackageName, action));
            contentResolver.update(uri, new ContentValues(), content, new String[]{});
        } catch (Exception e) {
            Logger.L.error("inser or update error:", e);
        }
    }

    public static void submit(Context context, String hostPackageName, String action) {
        try {
            if (context == null || TextUtils.isEmpty(action)) {
                return;
            }
            ContentResolver contentResolver = context.getContentResolver();
            if (contentResolver == null) {
                return;
            }
            Uri uri = Uri.parse(MessageFormat.format("content://{0}/{1}",
                    hostPackageName, action));
            contentResolver.delete(uri, "", new String[]{});
        } catch (Exception e) {
            Logger.L.error("submit error:", e);
        }
    }

    public static String query(Context context,
                               String hostPackageName,
                               String action,
                               List<ContentProviderQueryWhereItem> whereItems) {
        try {
            if (context == null || TextUtils.isEmpty(action)) {
                return "";
            }
            ContentResolver contentResolver = context.getContentResolver();
            if (contentResolver == null) {
                return "";
            }
            Uri uri = Uri.parse(MessageFormat.format("content://{0}/{1}", hostPackageName, action));
            ContentValues values = new ContentValues();
            values.put(QUERY_CONDITION_DATA_KEY, JsonUtils.toStr(whereItems));
            Uri data = contentResolver.insert(uri, values);
            String result = String.valueOf(data);
            return result;
        } catch (Exception e) {
            Logger.L.error("query error:", e);
        }
        return "";
    }

    public static String queryByExtras(Context context,
                                       String hostPackageName,
                                       String action,
                                       String extras) {
        List<ContentProviderQueryWhereItem> whereItems = new ArrayList<ContentProviderQueryWhereItem>();
        whereItems.add(new ContentProviderQueryWhereItem(extras, true));
        return query(context, hostPackageName, action, whereItems);
    }

    public static String query(Context context, String hostPackageName, String action) {
        try {
            if (context == null || TextUtils.isEmpty(action)) {
                return "";
            }
            ContentResolver contentResolver = context.getContentResolver();
            if (contentResolver == null) {
                return "";
            }
            Uri uri = Uri.parse(MessageFormat.format("content://{0}/{1}", hostPackageName, action));
            return contentResolver.getType(uri);
        } catch (Exception e) {
            Logger.L.error("query error:", e);
        }
        return "";
    }
}
