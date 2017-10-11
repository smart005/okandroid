package com.cloud.resources.xrecyclerview;

import android.view.View;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/10
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public interface IFamiliarLoadMore {
    void showLoading();

    void showNormal();

    boolean isLoading();

    View getView();
}
