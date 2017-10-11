package com.cloud.resources.popuppanel;

import android.content.Context;
import android.view.View;

import com.cloud.core.ObjectJudge;
import com.cloud.core.beans.BaseUpwardDialogParams;
import com.cloud.core.beans.CmdItem;
import com.cloud.core.beans.MenuDialogItem;
import com.cloud.core.dialog.BaseUpwardDialog;
import com.cloud.core.enums.UpwardDialogContentType;
import com.cloud.resources.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-6-19 下午5:52:43
 * @Description: 底部菜单工具类
 * @Modifier:
 * @ModifyContent:
 */
public class BaseUpwardMenu {

    private List<MenuDialogItem> mdilst = new ArrayList<MenuDialogItem>();
    private boolean iscanceloutlayout = false;
    private int menubgresid = 0;
    private int splitlinecolor = 0;
    private int firstitembgresid = 0;
    private int itembackgroundresid = 0;
    private int enditembackgroundresid = 0;
    private int titlecolor = 0;
    private String title = "";
    private int cancelTextColor = 0;
    /**
     * 面板背景颜色值 (兼容部分机型)默认为透明
     */
    private int panelBackgroundColor = 0;
    private BaseUpwardDialogParams muparams = new BaseUpwardDialogParams();

    private BaseUpwardDialog mudialog = new BaseUpwardDialog() {
        @Override
        protected void onItemClickListener(View v, CmdItem citem) {
            onClickItemListener(v, citem);
        }
    };

    public void show(Context context, View parent) {
        muparams.setContext(context);
        muparams.setMenuItems(mdilst);
        muparams.setCancelOutLayout(iscanceloutlayout);
        muparams.setSplitlinecolor(splitlinecolor);
        muparams.setMenuBackgroundResid(menubgresid);
        muparams.setFirstItemBackgroundResid(firstitembgresid);
        muparams.setItemBackgroundrResid(itembackgroundresid);
        muparams.setEndItemBackgroundResid(enditembackgroundresid);
        muparams.setTitle(title);
        muparams.setTitleColor(titlecolor);
        muparams.setContenttype(UpwardDialogContentType.Menus.ordinal());
        muparams.setAnimBottom(R.style.AnimBottom);
        muparams.setPanelBackgroundColor(panelBackgroundColor);
        muparams.setCancelTextColor(cancelTextColor);
        mudialog.setBaseUpwardDialogParams(muparams);
        mudialog.show(parent);
    }

    public void onClickItemListener(View v, CmdItem entity) {

    }

    /**
     * @param panelBackgroundColor 设置面板背景颜色值 (兼容部分机型)默认为透明
     */
    public void setPanelBackgroundColor(int panelBackgroundColor) {
        this.panelBackgroundColor = panelBackgroundColor;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitleColor(int titleColor) {
        this.titlecolor = titleColor;
    }

    public void setCancelTextColor(int cancelTextColor) {
        this.cancelTextColor = cancelTextColor;
    }

    public void setDataItems(List<MenuDialogItem> lst) {
        if (ObjectJudge.isNullOrEmpty(lst)) {
            return;
        }
        mdilst = lst;
    }

    public void setCancelClickOutLayout(boolean iscanceloutlayout) {
        this.iscanceloutlayout = iscanceloutlayout;
    }

    public void setFirstItemBackgroundResid(int firstitembgresid) {
        this.firstitembgresid = firstitembgresid;
    }

    public void setItemBackgroundResid(int itembackgroundresid) {
        this.itembackgroundresid = itembackgroundresid;
    }

    public void setEndItemBackgroundResid(int enditembackgroundresid) {
        this.enditembackgroundresid = enditembackgroundresid;
    }

    public void setSplitLineColor(int splitlinecolor) {
        this.splitlinecolor = splitlinecolor;
    }

    public void setMenuBackgroundResid(int menubgresid) {
        this.menubgresid = menubgresid;
    }

    public void dismiss() {
        if (mudialog != null && mudialog.isShowing()) {
            mudialog.dismiss();
        }
    }
}
