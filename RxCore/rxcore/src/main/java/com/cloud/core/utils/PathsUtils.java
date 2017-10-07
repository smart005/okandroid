package com.cloud.core.utils;

import java.io.File;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-11-23 下午3:55:20
 * @Description: 目录、路径处理类(http://stackoverflow.com/questions/412380/combine-paths
 * -in-java)
 * @Modifier:
 * @ModifyContent:
 */
public class PathsUtils {

    /**
     * 组合路径
     *
     * @param paths 要组合的地址列表
     * @return
     */
    public static String combine(String... paths) {
        File file = new File(paths[0]);
        for (int i = 1; i < paths.length; i++) {
            file = new File(file, paths[i]);
        }
        String uri = file.getPath();
        if(uri==null){
            return "";
        }
        if (uri.indexOf("://") < 0 && uri.indexOf(":/") >= 0) {
            uri = uri.replace(":/", "://");
        }
        return uri;
    }
}
