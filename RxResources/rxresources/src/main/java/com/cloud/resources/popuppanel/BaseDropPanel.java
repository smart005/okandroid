package com.cloud.resources.popuppanel;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

import com.cloud.core.logger.Logger;
import com.cloud.resources.R;
import com.cloud.resources.widgets.CompatiblePopupWindow;


/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-12-24 上午11:29:42
 * @Description: 上拉面板组件
 * @Modifier:
 * @ModifyContent:
 */
public class BaseDropPanel {

    private int layoutResid = 0;
    private View contentView = null;
    private boolean isUpdateView = false;
    private PopupWindow mpwin = null;
    private DropPanelListener mDropPanelListener = null;
    private int width = LayoutParams.MATCH_PARENT;
    private int height = LayoutParams.WRAP_CONTENT;

    /**
     * @param 设置mDropPanelListener
     */
    public void setDropPanelListener(DropPanelListener mDropPanelListener) {
        this.mDropPanelListener = mDropPanelListener;
    }

    /**
     * @param 设置width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @param 设置height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @param 设置layoutResid
     */
    public void setContentView(int layoutResid) {
        if (mpwin != null) {
            this.isUpdateView = this.layoutResid != layoutResid;
        }
        this.layoutResid = layoutResid;
    }

    public void setUpdateView(boolean isUpdateView) {
        this.isUpdateView = isUpdateView;
    }

    /**
     * @param 设置contentView
     */
    public void setContentView(View contentView) {
        if (mpwin != null) {
            this.isUpdateView = this.contentView != contentView;
        }
        this.contentView = contentView;
    }

    public void showAsDropPanel(Context context, View anthor, int xoff, int yoff) {
        try {
            if (layoutResid != 0) {
                contentView = View.inflate(context, layoutResid, null);
            }
            if (contentView == null) {
                return;
            }
            if (mpwin == null) {
                mpwin = new CompatiblePopupWindow(contentView, width, height);
                mpwin.setBackgroundDrawable(new BitmapDrawable());
                mpwin.setFocusable(true);
                mpwin.setOutsideTouchable(true);
                mpwin.setAnimationStyle(R.style.popupwindow_top_style);
                if (mDropPanelListener != null) {
                    mDropPanelListener.onDropPanelView(contentView);
                }
            }
            if (isUpdateView) {
                mpwin.setContentView(contentView);
                if (mDropPanelListener != null) {
                    mDropPanelListener.onDropPanelView(contentView);
                }
            }
            mpwin.update();
            mpwin.showAsDropDown(anthor, xoff, yoff);
        } catch (Exception e) {
            Logger.L.error("show drop panel error:", e);
        }
    }

    public void dismiss() {
        if (mpwin != null) {
            mpwin.dismiss();
        }
    }
}
