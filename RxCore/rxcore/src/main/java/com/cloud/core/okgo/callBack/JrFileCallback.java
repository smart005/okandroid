package com.cloud.core.okgo.callBack;

import java.io.File;

/**
 * @Author Gs
 * @Email:gs_12@foxmail.com
 * @CreateTime:2017/6/9
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */

public abstract class JrFileCallback<E> extends JrAbsCallback<File, E> {

    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed){

    }
}
