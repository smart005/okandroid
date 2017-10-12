package com.cloud.basicfun.beans;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/7/10
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class BaseFunType {

    private static BaseFunType baseFunType = null;

    public static BaseFunType getInstance() {
        return baseFunType == null ? baseFunType = new BaseFunType() : baseFunType;
    }

    /**
     * 友盟统计
     */
    private final String Analytics = "19d36084f4a54a19b64e6d5a353c22d6";
    /**
     * 更新app
     */
    private final String UpdateApp = "c6aa10e09e004c83983549605d6e54b8";
    /**
     * 停止插件更新
     */
    private final String StopPlugUpdate = "df5faf4ddcd34a728c1cc33ff4a7629e";

    public String getAnalytics() {
        return Analytics;
    }

    public String getUpdateApp() {
        return UpdateApp;
    }

    public String getStopPlugUpdate() {
        return StopPlugUpdate;
    }
}
