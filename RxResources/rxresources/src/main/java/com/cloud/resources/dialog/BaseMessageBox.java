package com.cloud.resources.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.cloud.core.beans.BaseDialogRes;
import com.cloud.core.beans.CmdItem;
import com.cloud.core.beans.MapEntry;
import com.cloud.core.enums.DialogButtonEnum;
import com.cloud.core.enums.DialogButtonsEnum;
import com.cloud.core.logger.Logger;
import com.cloud.resources.R;
import com.cloud.resources.enums.MsgBoxClickButtonEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-6-14 下午9:54:47
 * @Description: MessageBox基类
 * @Modifier:
 * @ModifyContent:
 */
public class BaseMessageBox {

    private String title = "";
    private String content = "";
    private View contentView = null;
    private boolean isShowTitle = false;
    private boolean isShowButtons = true;
    private boolean isShowClose = true;
    private BaseDialogRes mbdres = new BaseDialogRes();
    private String target = "";
    private Object extraData = null;
    private int mcontentgravity = Gravity.CENTER_HORIZONTAL;
    private int mtitlegravity = Gravity.LEFT;
    private CmdItem[] cmds = null;
    private List<MapEntry<String, Object>> datalist = new ArrayList<MapEntry<String, Object>>();
    private boolean isCancelable = true;
    private BaseDialogPlus baseDialogPlus = null;
    /**
     * dialog显示类型
     */
    private int dialogShowType = WindowManager.LayoutParams.TYPE_TOAST;

    public void setCancelable(boolean isCancelable) {
        this.isCancelable = isCancelable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public View getContentView() {
        return contentView;
    }

    public void setContentView(View contentView) {
        this.contentView = contentView;
    }

    public void setShowTitle(boolean isShowTitle) {
        this.isShowTitle = isShowTitle;
    }

    /**
     * 标题对齐
     *
     * @param gravity
     */
    public void setTitleGravity(int gravity) {
        mtitlegravity = gravity;
    }

    /**
     * 设置内容对齐
     *
     * @param gravity 默认Gravity.CENTER_HORIZONTAL
     */
    public void setContentGravity(int gravity) {
        mcontentgravity = gravity;
    }

    /**
     * @param 设置isShowButtons
     */
    public void setShowButtons(boolean isShowButtons) {
        this.isShowButtons = isShowButtons;
    }

    /**
     * @param 设置isShowClose
     */
    public void setShowClose(boolean isShowClose) {
        this.isShowClose = isShowClose;
    }

    /**
     * dialog显示类型
     *
     * @param dialogShowType
     */
    public void setDialogShowType(int dialogShowType) {
        this.dialogShowType = dialogShowType;
    }

    protected void setOnBaseDialogResChanged(BaseDialogRes mbdres) {

    }

    public void onItemClickListener(View v, MsgBoxClickButtonEnum mcbenum,
                                    String target, Object extraData) {

    }

    public void onItemClickListener(View v, String cmdid, String target,
                                    Object extraData) {

    }

    public void onCloseListener(String target, Object extraData) {

    }

    public void onFinally() {

    }

    public BaseMessageBox() {
        mbdres.dialogbackground = R.drawable.dialog_background;
        mbdres.buttonbackground = R.drawable.dialog_button_bg;
        mbdres.splitlinebackground = R.color.dialog_split_line_color;
        mbdres.closebuttonbackground = R.drawable.dialog_close_bg;
        mbdres.buttonTextColor = Color.WHITE;
        setOnBaseDialogResChanged(mbdres);
    }

    private void onConfirmProcess(View v) {
        int index = datalist.size() - 1;
        MapEntry<String, Object> mditem = index >= 0 ? datalist.get(index) : new MapEntry<String, Object>();
        onItemClickListener(v, MsgBoxClickButtonEnum.Confirm,
                index >= 0 ? mditem.getKey() : target,
                index >= 0 ? mditem.getValue() : extraData);
        onFinally();
        if (index >= 0) {
            datalist.remove(index);
        }
    }

    private void onCancelProcess(View v) {
        int index = datalist.size() - 1;
        MapEntry<String, Object> mditem = index >= 0 ? datalist.get(index) : new MapEntry<String, Object>();
        onItemClickListener(v, MsgBoxClickButtonEnum.Cancel,
                index >= 0 ? mditem.getKey() : target,
                index >= 0 ? mditem.getValue() : extraData);
        onFinally();
        if (index >= 0) {
            datalist.remove(index);
        }
    }

    private void onCustomProcess(View v) {
        if (v.getTag() == null) {
            return;
        }
        int index = datalist.size() - 1;
        MapEntry<String, Object> mditem = index >= 0 ? datalist.get(index) : new MapEntry<String, Object>();
        onItemClickListener(v, String.valueOf(v.getTag()),
                index >= 0 ? mditem.getKey() : target,
                index >= 0 ? mditem.getValue() : extraData);
        onFinally();
        if (index >= 0) {
            datalist.remove(index);
        }
    }

    private void onCloseProcess(View v) {
        int index = datalist.size() - 1;
        MapEntry<String, Object> mditem = index >= 0 ? datalist.get(index) : new MapEntry<String, Object>();
        onCloseListener(index >= 0 ? mditem.getKey() : target,
                index >= 0 ? mditem.getValue() : extraData);
        onFinally();
        if (index >= 0) {
            datalist.remove(index);
        }
    }

    private void onCancelLoginProcess(View v) {
        int index = datalist.size() - 1;
        MapEntry<String, Object> mditem = index >= 0 ? datalist.get(index) : new MapEntry<String, Object>();
        onItemClickListener(v, MsgBoxClickButtonEnum.CancelLogin,
                index >= 0 ? mditem.getKey() : target,
                index >= 0 ? mditem.getValue() : extraData);
        onFinally();
        if (index >= 0) {
            datalist.remove(index);
        }
    }

    private void onReloginProcess(View v) {
        int index = datalist.size() - 1;
        MapEntry<String, Object> mditem = index >= 0 ? datalist.get(index) : new MapEntry<String, Object>();
        onItemClickListener(v, MsgBoxClickButtonEnum.ReLogin,
                index >= 0 ? mditem.getKey() : target,
                index >= 0 ? mditem.getValue() : extraData);
        onFinally();
        if (index >= 0) {
            datalist.remove(index);
        }
    }

    private void onYessProcess(View v) {
        int index = datalist.size() - 1;
        MapEntry<String, Object> mditem = index >= 0 ? datalist.get(index) : new MapEntry<String, Object>();
        onItemClickListener(v, MsgBoxClickButtonEnum.Yes,
                index >= 0 ? mditem.getKey() : target,
                index >= 0 ? mditem.getValue() : extraData);
        onFinally();
        if (index >= 0) {
            datalist.remove(index);
        }
    }

    private void onNoProcess(View v) {
        int index = datalist.size() - 1;
        MapEntry<String, Object> mditem = index >= 0 ? datalist.get(index) : new MapEntry<String, Object>();
        onItemClickListener(v, MsgBoxClickButtonEnum.No,
                index >= 0 ? mditem.getKey() : target,
                index >= 0 ? mditem.getValue() : extraData);
        onFinally();
        if (index >= 0) {
            datalist.remove(index);
        }
    }

    public void setTarget(String target, Object extraData) {
        this.target = target;
        this.extraData = extraData;
    }

    public void setTarget(String target) {
        setTarget(target, null);
    }


    public void show(Context context, DialogButtonsEnum btnsenum) {
        try {
            baseDialogPlus = new BaseDialogPlus() {
                @Override
                protected void onDialogClickListener(DialogButtonEnum dialogButtonEnum, View view) {
                    try {
                        if (dialogButtonEnum == DialogButtonEnum.Confirm) {
                            onConfirmProcess(view);
                        } else if (dialogButtonEnum == DialogButtonEnum.Cancel) {
                            onCancelProcess(view);
                        } else if (dialogButtonEnum == DialogButtonEnum.CancelLogin) {
                            onCancelLoginProcess(view);
                        } else if (dialogButtonEnum == DialogButtonEnum.ReLogin) {
                            onReloginProcess(view);
                        } else if (dialogButtonEnum == DialogButtonEnum.Yes) {
                            onYessProcess(view);
                        } else if (dialogButtonEnum == DialogButtonEnum.No) {
                            onNoProcess(view);
                        }
                    } catch (Exception e) {
                        Logger.L.error("dialog click error:", e);
                    }
                }

                @Override
                protected void onDialogClickListener(String cmdId, View view) {
                    onCustomProcess(view);
                }

                @Override
                protected void onDialogCloseListener(View view) {
                    onCloseProcess(view);
                }
            };
            baseDialogPlus.setBtnsenum(btnsenum);
            baseDialogPlus.setButtons(cmds);
            baseDialogPlus.setContent(content);
            baseDialogPlus.setContentGravity(mcontentgravity);
            baseDialogPlus.setContentView(contentView);
            baseDialogPlus.setIsvisiblebuttons(isShowButtons);
            baseDialogPlus.setShowClose(isShowClose);
            baseDialogPlus.setShowTitle(isShowTitle);
            baseDialogPlus.setTitle(title);
            baseDialogPlus.setCancelable(isCancelable);
            baseDialogPlus.show(context);
        } catch (Exception e) {
            Logger.L.error("show dialog error:", e);
        }
    }

    public void setButtons(CmdItem[] cmds) {
        this.cmds = cmds;
    }

    public void dismiss() {
        if (baseDialogPlus != null && baseDialogPlus.isShowing()) {
            baseDialogPlus.dismiss();
        }
    }
}
