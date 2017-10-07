/**
 * @Title: NetworkUtils.java
 * @Description:
 * @author: lijinghuan
 * @data: 2015年4月30日 上午11:15:14
 */
package com.cloud.core.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

import java.util.ArrayList;
import java.util.List;

public class NetworkUtils {
    /**
     * 网络状态为DISCONNECTED DISCONNECTING SUSPENDED UNKNOWN均表示存在可用网络但未连接
     *
     * @param context
     * @return
     */
    public static boolean hasAvailable(Context context) {
        ConnectivityManager netconnMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (netconnMgr == null) {
            return false;
        } else {
            boolean flag = false;
            NetworkInfo[] info = netconnMgr.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    State netstate = info[i].getState();
                    if (netstate == State.DISCONNECTED
                            || netstate == State.DISCONNECTING
                            || netstate == State.SUSPENDED
                            || netstate == State.UNKNOWN) {
                        flag = true;
                        break;
                    }
                }
            }
            return flag;
        }
    }

    /**
     * function: 检查网络是否成功连接
     *
     * @return true: 网络是否已连接成功，并且已建立连接和传递数据 false: 网络未连接成功
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager netconnMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = netconnMgr.getActiveNetworkInfo();
        if (ni == null || !ni.isConnected()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 飞行模式下返回值为-1， 否则返回值为ConnectivityManager.TYPE_MOBILE
     * 或ConnectivityManager.TYPE_WIFI
     */
    public static int getConnectedType(Context context) {
        ConnectivityManager netconnMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = netconnMgr.getActiveNetworkInfo();
        if (ni == null || !ni.isAvailable()) {
            return -1;
        } else {
            return ni.getType();
        }
    }

    /**
     * 获取网络连接类型:-1未连接;1:wifi;2,3:移动网络;
     * @param context
     * @return
     */
    public static List<Integer> getAPNType(Context context) {
        List<Integer> netTypes = new ArrayList<Integer>();
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] nInfolst = connMgr.getAllNetworkInfo();
        if (nInfolst == null) {
            netTypes.add(-1);
            return netTypes;
        }
        for (NetworkInfo nk : nInfolst) {
            if (nk.isConnectedOrConnecting() || nk.isAvailable()) {
                int nType = nk.getType();
                if (nType == ConnectivityManager.TYPE_MOBILE) {
                    String exinfo = nk.getExtraInfo();
                    if (exinfo != null && exinfo.toLowerCase().equals("cmnet")) {
                        if (!netTypes.contains(3)) {
                            netTypes.add(3);
                        }
                    } else {
                        if (!netTypes.contains(2)) {
                            netTypes.add(2);
                        }
                    }
                } else if (nType == ConnectivityManager.TYPE_WIFI) {
                    if (!netTypes.contains(1)) {
                        netTypes.add(1);
                    }
                }
            }
        }
        if (netTypes.isEmpty()) {
            netTypes.add(-1);
        }
        return netTypes;
    }
}