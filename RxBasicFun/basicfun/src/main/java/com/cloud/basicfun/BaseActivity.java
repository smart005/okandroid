package com.cloud.basicfun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.cloud.core.cache.RxModuleCache;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.SharedPrefUtils;
import com.cloud.basicfun.beans.BaseFunType;
import com.cloud.basicfun.update.UpdateBLL;
import com.cloud.basicfun.update.VersionUpdateProperties;
import com.cloud.basicfun.utils.AnalyticsProcess;
import com.cloud.basicfun.utils.WinObjectUtils;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/6/4
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class BaseActivity extends Activity {

    /**
     * 当前分页索引
     */
    protected int currPageIndex = 1;
    private boolean analyticsFlag = false;
    private AnalyticsProcess analyticsProcess = null;

    protected void receiveRSCResult(Intent intent) {

    }

    protected void receiveRSCResult(Intent intent, String action) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        analyticsFlag = RxModuleCache.getCacheModuleFlag(BaseFunType.getInstance().getAnalytics());
        if (analyticsFlag) {
            analyticsProcess = new AnalyticsProcess();
        }
        mwoutils.onCreate(this, savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    protected int getCurrPageIndex() {
        return currPageIndex = 1;
    }

    private WinObjectUtils mwoutils = new WinObjectUtils() {
        @Override
        protected void receiveRSCResult(Intent intent) {
            BaseActivity.this.receiveRSCResult(intent);
        }

        @Override
        protected void receiveRSCResult(Intent intent, String action) {
            BaseActivity.this.receiveRSCResult(intent, action);
        }

        @Override
        protected void onCheckVersionUpdateListener() {
            boolean moduleFlag = RxModuleCache.getCacheModuleFlag(BaseFunType.getInstance().getUpdateApp());
            if (moduleFlag) {
                long updateTime = SharedPrefUtils.getPrefLong(getActivity(), "UPDATE_VERSION_KEY", 0);
                long currTime = System.currentTimeMillis();
                long time = currTime - updateTime;
                if (updateTime == 0 || mUBll.isCheckComplete()) {
                    if (time > 0 && (time / 3600) <= 6) {
                        return;
                    }
                    SharedPrefUtils.setPrefLong(getActivity(), "UPDATE_VERSION_KEY", System.currentTimeMillis());
                    VersionUpdateProperties properties = new VersionUpdateProperties();
                    properties.setActivity(BaseActivity.this);
                    properties.setAppIcon(BaseApplication.getInstance().getAppIcon());
                    properties.setIsAutoUpdate(true);
                    properties.setIsCheckUpdatePrompt(false);
                    properties.setCheckUpdateUrl(BaseApplication.getInstance().getCheckUpdateUrl());
                    mUBll.checkVersionUpdate(properties);
                }
            }
        }
    };

    public UpdateBLL mUBll = new UpdateBLL() {
        @Override
        protected void onCheckCompleted() {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mwoutils.onResume(this);
        if (analyticsFlag) {
            analyticsProcess.onResume(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mwoutils.onPause(this);
        if (analyticsFlag) {
            analyticsProcess.onPause(this);
        }
    }

    @Override
    protected void onStart() {
        mwoutils.onStart(this);
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        mwoutils.onDestroy(this);
        super.onDestroy();
    }

    public Bundle getBundle() {
        Intent intent = getIntent();
        if (intent == null) {
            return new Bundle();
        } else {
            Bundle bundle = intent.getExtras();
            return bundle == null ? new Bundle() : bundle;
        }
    }

    public String getStringBundle(String key, String defaultValue) {
        Bundle bundle = getBundle();
        if (bundle.containsKey(key)) {
            return bundle.getString(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    public String getStringBundle(String key) {
        return getStringBundle(key, "");
    }

    public int getIntBundle(String key, int defaultValue) {
        Bundle bundle = getBundle();
        if (bundle.containsKey(key)) {
            return bundle.getInt(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    public int getIntBundle(String key) {
        return getIntBundle(key, 0);
    }

    public boolean getBooleanBundle(String key, boolean defaultValue) {
        Bundle bundle = getBundle();
        if (bundle.containsKey(key)) {
            return bundle.getBoolean(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    public boolean getBooleanBundle(String key) {
        return getBooleanBundle(key, false);
    }

    public Object getObjectBundle(String key, Object defaultValue) {
        Bundle bundle = getBundle();
        if (bundle.containsKey(key)) {
            return bundle.get(key);
        } else {
            return defaultValue;
        }
    }

    public Object getObjectBundle(String key) {
        return getObjectBundle(key, null);
    }

    public float getFloatBundle(String key, float defaultValue) {
        Bundle bundle = getBundle();
        if (bundle.containsKey(key)) {
            return bundle.getFloat(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    public float getFloatBundle(String key) {
        return getFloatBundle(key, 0);
    }

    public double getDoubleBundle(String key, double defaultValue) {
        Bundle bundle = getBundle();
        if (bundle.containsKey(key)) {
            return bundle.getDouble(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    public double getDoubleBundle(String key) {
        return getDoubleBundle(key, 0);
    }

    public long getLongBundle(String key, long defaultValue) {
        Bundle bundle = getBundle();
        if (bundle.containsKey(key)) {
            return bundle.getLong(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    public long getLongBundle(String key) {
        return getLongBundle(key, 0);
    }

    /**
     * 重新统计列表高度
     *
     * @param listView
     */
    protected void reMeasureListHeight(ListView listView) {
        try {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                return;
            }
            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        } catch (Exception e) {
            Logger.L.error("re measure list height error:", e);
        }
    }

    public Activity getActivity() {
        return BaseActivity.this;
    }
}
