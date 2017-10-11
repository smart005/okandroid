package com.cloud.resources.search;

import android.text.Editable;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-11-10 上午12:29:21
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public interface OnSearchListener {
    void onSearchTextChanged(boolean isempty);

    void onSearchDone(CharSequence text);

    void afterTextChanged(Editable editable);
}
