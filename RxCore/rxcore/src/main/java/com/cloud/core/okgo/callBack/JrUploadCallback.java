package com.cloud.core.okgo.callBack;

/**
 * @Author Gs
 * @Email:gs_12@foxmail.com
 * @CreateTime:2017/6/9
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */

public abstract class JrUploadCallback<E> extends JrAbsCallback<String, E> {

    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {

    }
}
