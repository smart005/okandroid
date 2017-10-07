package com.cloud.coretest;

import java.util.HashMap;
import java.util.List;

/**
 * @Author Gs
 * @Email:gs_12@foxmail.com
 * @CreateTime:2017/5/23
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */

public class ConfigsItem {

    private int deviceType;
    private String pageName;
    private String plugName;
    private List<String> version;
    private HashMap<String, Object> params;

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPlugName() {
        return plugName;
    }

    public void setPlugName(String plugName) {
        this.plugName = plugName;
    }

    public List<String> getVersion() {
        return version;
    }

    public void setVersion(List<String> version) {
        this.version = version;
    }

    public HashMap<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<>();
        }
        return params;
    }

    public void setParams(HashMap<String, Object> params) {
        this.params = params;
    }
}


