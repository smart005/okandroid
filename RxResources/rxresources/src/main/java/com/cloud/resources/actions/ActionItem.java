package com.cloud.resources.actions;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class ActionItem {

    private Drawable mIcon;
    private String mTitle;
    private String mActionId = "";
    private int position = 0;

    public ActionItem(Context context, String title, String actionId,
                      int resourceId, int position) {
        if (resourceId != 0) {
            mIcon = context.getResources().getDrawable(resourceId);
        }
        mTitle = title;
        mActionId = actionId;
        this.position = position;
    }

    public ActionItem(Context context, String title, String actionId, int position) {
        this(context, title, actionId, 0, position);
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public void setIcon(Drawable icon) {
        this.mIcon = icon;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getActionId() {
        return mActionId;
    }

    public void setActionId(String actionId) {
        this.mActionId = actionId;
    }

    /**
     * @return 获取position
     */
    public int getPosition() {
        return position;
    }

    /**
     * @param 设置position
     */
    public void setPosition(int position) {
        this.position = position;
    }
}
