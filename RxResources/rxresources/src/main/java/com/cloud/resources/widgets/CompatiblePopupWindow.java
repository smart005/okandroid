package com.cloud.resources.widgets;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.widget.PopupWindow;

/**
 * @Author Gs
 * @Email:gs_12@foxmail.com
 * @CreateTime:2017/5/2
 * @Description: 解决7.0以上弹出位置置顶问题
 * @Modifier:
 * @ModifyContent:
 */

public class CompatiblePopupWindow extends PopupWindow {

    public CompatiblePopupWindow(View contentView, int width, int height) {
        this(contentView, width, height, false);
    }

    public CompatiblePopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor, xoff, yoff);
    }

    @Override
    public void showAsDropDown(View anchor) {
        showAsDropDown(anchor, 0, 0);
    }

}
