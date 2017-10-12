package com.cloud.basicfun;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.cloud.basicfun.beans.BaseFunType;
import com.cloud.basicfun.enums.AnalyticsType;
import com.cloud.basicfun.update.UpdateBLL;
import com.cloud.basicfun.update.VersionUpdateProperties;
import com.cloud.basicfun.utils.AnalyticsProcess;
import com.cloud.basicfun.utils.WinObjectUtils;
import com.cloud.core.cache.RxModuleCache;
import com.cloud.core.logger.Logger;
import com.cloud.core.ret.BaseSysCode;
import com.cloud.core.utils.SharedPrefUtils;
import com.cloud.resources.widgets.DepthContentVPFragment;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/3/10
 * @Description:支持viewpager自定义动画
 * @Modifier:
 * @ModifyContent:
 */
public class BaseDepthFragment extends DepthContentVPFragment {
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        analyticsFlag = RxModuleCache.getCacheModuleFlag(BaseFunType.getInstance().getAnalytics());
        if (analyticsFlag) {
            analyticsProcess = new AnalyticsProcess();
        }
        mwoutils.onCreate(getContext(), savedInstanceState);
    }

    public View findViewById(int id) {
        return getActivity().findViewById(id);
    }

    protected int getCurrPageIndex() {
        return currPageIndex = 1;
    }

    public static <T extends BaseDepthFragment> T newInstance(T t, Bundle args) {
        if (args != null) {
            t.setArguments(args);
        }
        return t;
    }

    public static <T extends BaseDepthFragment> T newInstance(T t, String param) {
        Bundle args = new Bundle();
        args.putString(BaseSysCode.ARG_PARAM, param);
        t.setArguments(args);
        return t;
    }

    public static <T extends BaseDepthFragment> T newInstance(T t) {
        return newInstance(t, (Bundle) null);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (analyticsFlag) {
            analyticsProcess.onResume(getContext(), AnalyticsType.StatisticalPage);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (analyticsFlag) {
            analyticsProcess.onPause(getContext(), AnalyticsType.StatisticalPage);
        }
    }

    @Override
    public void onStart() {
        mwoutils.onStart(getActivity());
        super.onStart();
    }

    @Override
    public void onDestroy() {
        mwoutils.onDestroy(getActivity());
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private WinObjectUtils mwoutils = new WinObjectUtils() {
        @Override
        protected void receiveRSCResult(Intent intent) {
            BaseDepthFragment.this.receiveRSCResult(intent);
        }

        @Override
        protected void receiveRSCResult(Intent intent, String action) {
            BaseDepthFragment.this.receiveRSCResult(intent, action);
        }

        @Override
        protected void onCheckVersionUpdateListener() {
            boolean moduleFlag = RxModuleCache.getCacheModuleFlag(BaseFunType.getInstance().getUpdateApp());
            if (moduleFlag) {
                long updateTime = SharedPrefUtils.getPrefLong(getActivity(), "UPDATE_VERSION_KEY", 0);
                long currTime = System.currentTimeMillis();
                long time = currTime - updateTime;
                if (updateTime == 0 || ubll.isCheckComplete()) {
                    if (time > 0 && (time / 3600) <= 6) {
                        return;
                    }
                    SharedPrefUtils.setPrefLong(getActivity(), "UPDATE_VERSION_KEY", System.currentTimeMillis());
                    VersionUpdateProperties properties = new VersionUpdateProperties();
                    properties.setActivity(getActivity());
                    properties.setAppIcon(BaseApplication.getInstance().getAppIcon());
                    properties.setIsAutoUpdate(true);
                    properties.setIsCheckUpdatePrompt(false);
                    properties.setCheckUpdateUrl(BaseApplication.getInstance().getCheckUpdateUrl());
                    ubll.checkVersionUpdate(properties);
                }
            }
        }
    };

    public UpdateBLL ubll = new UpdateBLL() {
        @Override
        protected void onCheckCompleted() {

        }
    };

    public Bundle getBundle() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            return new Bundle();
        } else {
            return bundle;
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
}
