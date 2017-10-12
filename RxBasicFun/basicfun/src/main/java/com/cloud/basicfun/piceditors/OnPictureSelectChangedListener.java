package com.cloud.basicfun.piceditors;

import android.net.Uri;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/7/21
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public interface OnPictureSelectChangedListener {
    public void onPictureSelectChanged(Uri imgUri, String fileName, int position);
}
