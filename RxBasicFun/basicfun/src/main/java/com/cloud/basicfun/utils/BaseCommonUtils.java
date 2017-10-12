package com.cloud.basicfun.utils;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloud.basicfun.BaseApplication;
import com.cloud.basicfun.R;
import com.cloud.basicfun.beans.ClassInfoItem;
import com.cloud.basicfun.enums.RxReceiverActions;
import com.cloud.basicfun.ui.WirelessPromptActivity;
import com.cloud.core.cache.RxCache;
import com.cloud.core.db.DbUtils;
import com.cloud.core.db.Selector;
import com.cloud.core.enums.RuleParams;
import com.cloud.core.logger.Logger;
import com.cloud.core.ret.BaseSysCode;
import com.cloud.core.utils.AppInfoUtils;
import com.cloud.core.utils.GlobalUtils;
import com.cloud.core.utils.HandlerUtils;
import com.cloud.core.utils.PathsUtils;
import com.cloud.core.utils.StorageUtils;
import com.cloud.core.utils.ToastUtils;
import com.cloud.core.utils.ValidUtils;
import com.cloud.resources.RedirectUtils;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/6/20
 * @Description: 公用配置类
 * @Modifier:
 * @ModifyContent:
 */
public class BaseCommonUtils {

    private static HandlerUtils handlerUtils = new HandlerUtils();

    /**
     * 获取图片url格式(中间imgurl用%s代替)
     *
     * @param baseUrl
     * @param imgUrl
     * @param suffix  图片后缀
     * @return 如:baseurl/imgurl+规则
     */
    public static String getImgUrlFormat(String baseUrl, String imgUrl, String suffix) {
        if (TextUtils.isEmpty(imgUrl)) {
            return "";
        }
        if (ValidUtils.valid(RuleParams.Url.getValue(), imgUrl)) {
            return String.format("%s%s", imgUrl, suffix);
        } else {
            return String.format("%s%s", PathsUtils.combine(baseUrl, imgUrl), suffix);
        }
    }

    public static ViewGroup getRootView(Window win) {
        View rootView = win.getDecorView().findViewById(android.R.id.content);
        ViewGroup vg = (ViewGroup) rootView;
        return vg;
    }

    public static void sendNetworkStateBroadcast(Context context) {
        Bundle mbundle = new Bundle();
        mbundle.putBoolean(RxReceiverActions.NETWORK_CHANGE.getValue(), true);
        RedirectUtils.sendBroadcast(context, mbundle);
    }

    public static void hideNetworkStateView(Window win) {
        ViewGroup rootview = getRootView(win);
        if (rootview != null) {
            View networkstateview = rootview
                    .findViewById(R.id.network_state_rl);
            if (networkstateview != null) {
                networkstateview.setVisibility(View.GONE);
            }
        }
    }

    public static void showNetworkStateView(final Activity activity) {
        if (activity == null) {
            return;
        }
        ViewGroup rootview = getRootView(activity.getWindow());
        if (rootview != null) {
            View networkstateview = rootview
                    .findViewById(R.id.network_state_rl);
            if (networkstateview == null) {
                RelativeLayout.LayoutParams rlparam = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                rlparam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                View nsview = View.inflate(activity,
                        R.layout.network_state_view, null);
                networkstateview = nsview.findViewById(R.id.network_state_rl);
                networkstateview.setVisibility(View.VISIBLE);
                networkstateview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RedirectUtils.startActivity(activity, WirelessPromptActivity.class);
                    }
                });
                rootview.addView(nsview, rlparam);
            } else {
                networkstateview.setVisibility(View.VISIBLE);
            }
        }
    }

    public static View getMapPopouView(Context context, Bundle extraInfo) {
        TextView tv = (TextView) View.inflate(context,
                R.layout.location_pop_view, null);
        tv.setBackgroundResource(extraInfo.getInt("BACKGROUND_KEY"));
        tv.setText(extraInfo.getString("CONTENT_KEY"));
        return tv;
    }

    public static void bindFrameLayout(FragmentActivity fragmentActivity, int containerViewId, Fragment fragment) {
        if (!fragment.isAdded()) {
            FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(containerViewId, fragment);
            fragmentTransaction.commit();
        }
    }

    public static void downApp(Context context, String url, String title) {
        String suffix = GlobalUtils.getSuffixName(url);
        if (ValidUtils.valid(RuleParams.Url.getValue(), url) && TextUtils.equals(suffix, "apk")) {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setVisibleInDownloadsUi(true);
            request.setDestinationUri(Uri.fromFile(StorageUtils.getApksDir()));
            request.setTitle(String.format("下载 %s apk", title));
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setAllowedOverRoaming(false);
            request.setVisibleInDownloadsUi(true);
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            request.setMimeType(mimeTypeMap.getMimeTypeFromExtension(url));
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);
            ToastUtils.showLong(context, R.string.downloading_just);
        } else {
            ToastUtils.showLong(context, R.string.down_address_novalid);
        }
    }

    /**
     * 获取渠道名
     *
     * @param channelName 如果channelName不为空则返回channelName渠道
     * @return
     */
    public static String getChannelName(String channelName) {
        try {
            if (!TextUtils.isEmpty(channelName)) {
                return channelName;
            }
            BaseApplication baseApplication = BaseApplication.getInstance();
            Bundle mbundle = AppInfoUtils.getApplicationMetaData(baseApplication);
            if (mbundle != null) {
                if (mbundle.containsKey(BaseSysCode.CHANNEL_NAME)) {
                    channelName = mbundle.getString(BaseSysCode.CHANNEL_NAME);
                }
            }
            return channelName;
        } catch (Exception e) {
            Logger.L.error("get channel name error:", e);
        }
        return "";
    }

    /**
     * 加载静态库文件是否成功
     *
     * @param libName 静态库名称
     * @return 是否加载成功
     */
    public static boolean loadLibrary(String libName) {
        try {
            try {
                System.loadLibrary(libName);
                return true;
            } catch (Exception e) {
                System.loadLibrary(libName);
            }
            return true;
        } catch (Exception e) {
            Logger.L.error("load so library error:", e);
        }
        return false;
    }

    public static void post(Runnable runnable) {
        if (handlerUtils != null) {
            handlerUtils.post(runnable);
        }
    }

    public static void postDelayed(Runnable runnable, long delayMillis) {
        if (handlerUtils != null) {
            handlerUtils.postDelayed(runnable, delayMillis);
        }
    }

    public static void setIsOfficial(boolean isOfficial) {
        RxCache.setCacheFlag("IS_OFFICIAL_FLAG", isOfficial);
    }

    public static boolean getIsOfficial() {
        return RxCache.getCacheFlag("IS_OFFICIAL_FLAG");
    }

    public static void setIsPlugOfficial(boolean isOfficial) {
        RxCache.setCacheFlag("PLUG_IS_OFFICIAL_FLAG", isOfficial);
    }

    public static boolean getIsPlugOfficial() {
        return RxCache.getCacheFlag("PLUG_IS_OFFICIAL_FLAG");
    }

    /**
     * 获取类路径
     *
     * @param className 类名
     * @return 完整路径
     */
    public static String getClassPath(Context context, final String className) {
        try {
            if (context == null || TextUtils.isEmpty(className)) {
                return "";
            }
            Selector<ClassInfoItem> selector = DbUtils.getInstance().getDbManager().selector(ClassInfoItem.class);
            if (selector == null) {
                return "";
            }
            ClassInfoItem entity = selector.getEntity();
            ClassInfoItem first = selector.where(entity.getForKey(entity.getClassName()), "=", className).findFirst();
            if (first == null) {
                return "";
            }
            return first.getClassFullName() == null ? "" : first.getClassFullName();
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return "";
    }
}
