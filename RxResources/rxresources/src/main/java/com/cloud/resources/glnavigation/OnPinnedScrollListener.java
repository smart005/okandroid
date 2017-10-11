package com.cloud.resources.glnavigation;

import android.widget.AbsListView;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/6/18
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public interface OnPinnedScrollListener {
    public void onPinnedScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
}
