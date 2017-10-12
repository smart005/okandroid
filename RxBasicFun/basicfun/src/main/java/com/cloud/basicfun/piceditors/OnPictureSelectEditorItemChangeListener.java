package com.cloud.basicfun.piceditors;

import android.net.Uri;

import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/7/21
 * @Description:当数据项改变时触发
 * @Modifier:
 * @ModifyContent:
 */
public interface OnPictureSelectEditorItemChangeListener {
    public void onPictureSelectEditorItemChange(PictureSelectEditorView pictureSelectEditorView, List<Uri> images);
}
