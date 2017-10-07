package com.cloud.core.ret;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/8/7
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class BaseSysCode {
    /**
     * API返回码(正确回调)
     */
    public static List<String> API_RET = Arrays.asList("R0001", "200", "success");
    /**
     * 在接口请求时对包含的特定的接口名称不做rcd=API_RET验证
     */
    public static List<String> API_SPECIFIC_NAME_FILTER = Arrays.asList("authentication", "area");
    /**
     * API返回码(未登录)
     */
    public static List<String> API_UNLOGIN = Arrays.asList("U0000", "U0001");
    /**
     * 重新授权flag键
     */
    public static String RE_AUTH_FLAG_KEY = "523669987";
    /**
     * API消息提示过滤返回码
     */
    public static List<String> API_MESSAGE_PROMPT_FILTER = Arrays.asList(
            "401",
            "500",
            "400",
            "301",
            "302",
            "S0404",
            "E0002",
            "S0001"
    );
    /**
     * 插件页面跳转
     */
    public static String PLUG_GO_PAGE = "9c93d818bab349ce906081cc523de1c2";
    /**
     * 插件停止
     */
    public static String PLUG_STOP = "c92c1fa4c5a942b9865bd2b3f652378e";
    /**
     * 参数KEY
     */
    public static final String ARG_PARAM = "802826781";
    /**
     * 渠道名
     */
    public static final String CHANNEL_NAME = "UMENG_CHANNEL";
    /**
     * 打开登录页flag键
     */
    public static final String OPEN_LOGIN_FLAG_KEY = "1293317866";
    /**
     * 启动登录页面flag键
     */
    public static final String START_LOGIN_FLAG_KEY = "411237091";
    /**
     * api名称
     */
    public static final String API_NAME = "2f9c0897aea7489f86a047345fb4a527";
    /**
     * api返回结果json
     */
    public static final String API_RESULT_JSON = "8e077672c03c42728af0351bd817efb1";
}
