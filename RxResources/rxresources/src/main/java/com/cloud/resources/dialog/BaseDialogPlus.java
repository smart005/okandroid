package com.cloud.resources.dialog;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloud.core.beans.CmdItem;
import com.cloud.core.enums.DialogButtonEnum;
import com.cloud.core.enums.DialogButtonsEnum;
import com.cloud.core.utils.GlobalUtils;
import com.cloud.core.utils.PixelUtils;
import com.cloud.resources.R;
import com.cloud.resources.dialog.plugs.DialogPlus;
import com.cloud.resources.dialog.plugs.DialogPlusBuilder;
import com.cloud.resources.dialog.plugs.ViewHolder;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/4/27
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class BaseDialogPlus {

    private String title = "";
    private boolean isShowClose = false;
    private boolean isShowTitle = true;
    private String content = "";
    private View contentView = null;
    private int mcontentgravity = Gravity.CENTER;
    private DialogButtonsEnum btnsenum = DialogButtonsEnum.None;
    private boolean isvisiblebuttons = true;
    private CmdItem[] cmds;
    private int CUSTOM_BTN_TAG = 1927133320;
    private DialogPlus dialog = null;
    private boolean isCancelable = true;
    private String dialogId = "851539783";

    protected void onDialogClickListener(DialogButtonEnum dialogButtonEnum, View view) {

    }

    protected void onDialogClickListener(String cmdId, View view) {

    }

    protected void onDialogCloseListener(View view) {

    }

    public void setCancelable(boolean isCancelable) {
        this.isCancelable = isCancelable;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setShowClose(boolean isShowClose) {
        this.isShowClose = isShowClose;
    }

    public void setShowTitle(boolean isShowTitle) {
        this.isShowTitle = isShowTitle;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContentView(View contentView) {
        this.contentView = contentView;
    }

    public void setContentGravity(int contentGravity) {
        this.mcontentgravity = contentGravity;
    }

    public void setBtnsenum(DialogButtonsEnum buttonsEnum) {
        this.btnsenum = buttonsEnum;
    }

    public void setIsvisiblebuttons(boolean isvisiblebuttons) {
        this.isvisiblebuttons = isvisiblebuttons;
    }

    public void setButtons(CmdItem[] cmds) {
        this.cmds = cmds;
    }

    public void show(Context context) {
        int contentWidth = GlobalUtils.getScreenWidth(context) * 4 / 5;
        ViewHolder holder = new ViewHolder(R.layout.dialog_plugs_content_view);
        DialogPlusBuilder builder = DialogPlus.newDialog(context)
                //内容布局
                .setHeader(R.layout.dialog_plugs_head_view)
                .setContentHolder(holder)
                .setFooter(R.layout.dialog_plugs_footer_view)
                //点击dialog之外是否消失
                .setCancelable(isCancelable)
                .setGravity(Gravity.CENTER)
                //设置单击dialog监听
                .setOnClickListener(new com.cloud.resources.dialog.plugs.OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        onDailogClick(dialog, view);
                    }
                })
                //是否根据内容大小自动展开直到充满全屏
                .setExpanded(false)
                //内容宽度
                .setContentWidth(contentWidth)
                //内容高度
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                //弹窗区域之外的背景
                .setOverlayBackgroundResource(R.color.semi_transparent)
                //弹窗内容背景
                .setContentBackgroundResource(R.drawable.dialog_background)
                //overlay背景和屏幕的边距
                .setOutMostMargin(0, 0, 0, 0)
                .setPadding(PixelUtils.dip2px(context, 4),
                        PixelUtils.dip2px(context, 6),
                        PixelUtils.dip2px(context, 4),
                        PixelUtils.dip2px(context, 6));
        dialog = builder.create(dialogId);
        TextView titleTv = (TextView) dialog.getHeaderView().findViewById(R.id.title_tv);
        if (isShowTitle) {
            titleTv.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(title)) {
                titleTv.setText(title);
            }
        } else {
            titleTv.setVisibility(View.INVISIBLE);
        }
        if (isShowClose) {
            View dialogPlugsCloseIv = dialog.getHeaderView().findViewById(R.id.dialog_plugs_close_iv);
            dialogPlugsCloseIv.setVisibility(View.VISIBLE);
        }
        RelativeLayout container = (RelativeLayout) dialog.getHolderView().findViewById(R.id.dialog_plugs_content_rl);
        if (TextUtils.isEmpty(content)) {
            if (contentView != null) {
                RelativeLayout.LayoutParams cvparam = new RelativeLayout.LayoutParams(contentWidth, RelativeLayout.LayoutParams.WRAP_CONTENT);
                container.addView(contentView, cvparam);
            }
        } else {
            container.addView(createMessageView(context, content, contentWidth));
        }
        LinearLayout cvll = (LinearLayout) dialog.getFooterView().findViewById(R.id.dialog_plugs_footer_rl);
        if (isvisiblebuttons) {
            cvll.addView(createSplitLine(context,
                    PixelUtils.dip2px(context, 8), 0,
                    LinearLayout.HORIZONTAL, false));
            LinearLayout.LayoutParams cvllparam = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            cvllparam.leftMargin = PixelUtils.dip2px(context, 8);
            cvllparam.rightMargin = PixelUtils.dip2px(context, 8);
            cvllparam.topMargin = PixelUtils.dip2px(context, 8);
            cvllparam.bottomMargin = PixelUtils.dip2px(context, 8);
            LinearLayout buttons = createButtons(context, contentWidth - 2 * PixelUtils.dip2px(context, 4));
            cvll.addView(buttons, cvllparam);
        }
        dialog.assignClickListenerRecursively(cvll);
        dialog.show();
    }

    public boolean isShowing() {
        if (dialog != null) {
            return dialog.isShowing();
        }
        return false;
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    private void onDailogClick(DialogPlus dialog, View view) {
        int id = view.getId();
        if (id == R.id.dialog_plugs_close_iv) {
            onDialogCloseListener(view);
            dialog.dismiss();
        } else {
            String tag = String.valueOf(view.getTag());
            if (TextUtils.equals(tag, DialogButtonEnum.No.getValue())) {
                onDialogClickListener(DialogButtonEnum.No, view);
                dialog.dismiss();
            } else if (TextUtils.equals(tag, DialogButtonEnum.Yes.getValue())) {
                onDialogClickListener(DialogButtonEnum.Yes, view);
                dialog.dismiss();
            } else if (TextUtils.equals(tag, DialogButtonEnum.Cancel.getValue())) {
                onDialogClickListener(DialogButtonEnum.Cancel, view);
                dialog.dismiss();
            } else if (TextUtils.equals(tag, DialogButtonEnum.Confirm.getValue())) {
                onDialogClickListener(DialogButtonEnum.Confirm, view);
                dialog.dismiss();
            } else if (TextUtils.equals(tag, DialogButtonEnum.CancelLogin.getValue())) {
                onDialogClickListener(DialogButtonEnum.CancelLogin, view);
                dialog.dismiss();
            } else if (TextUtils.equals(tag, DialogButtonEnum.ReLogin.getValue())) {
                onDialogClickListener(DialogButtonEnum.ReLogin, view);
                dialog.dismiss();
            } else if (TextUtils.equals(String.valueOf(view.getTag(CUSTOM_BTN_TAG)), "1872645244")) {
                onDialogClickListener(tag, view);
                dialog.dismiss();
            } else {
                dialog.dismiss();
            }
        }
    }

    private TextView createMessageView(Context context, String msg, int width) {
        LinearLayout.LayoutParams cvllparam = new LinearLayout.LayoutParams(
                width,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        cvllparam.setMargins(PixelUtils.dip2px(context, 12),
                PixelUtils.dip2px(context, 8),
                PixelUtils.dip2px(context, 12),
                PixelUtils.dip2px(context, 8));
        TextView tv = new TextView(context);
        tv.setLayoutParams(cvllparam);
        tv.setTextColor(Color.rgb(31, 31, 31));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        tv.setSingleLine(false);
        tv.setText(msg);
        tv.setMinHeight(PixelUtils.dip2px(context, 28));
        tv.setGravity(mcontentgravity);
        return tv;
    }

    private View createSplitLine(Context context, int top, int bottom,
                                 int orientation, boolean ismargin) {
        View v = new View(context);
        LinearLayout.LayoutParams vparam = null;
        if (LinearLayout.HORIZONTAL == orientation) {
            vparam = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 1);
            if (ismargin) {
                vparam.setMargins(PixelUtils.dip2px(context, 8), top,
                        PixelUtils.dip2px(context, 8), bottom);
            } else {
                vparam.setMargins(1, top, 1, bottom);
            }
            v.setBackgroundResource(R.color.dialog_split_line_color);
        } else {
            vparam = new LinearLayout.LayoutParams(1,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            vparam.setMargins(0, top, 0, bottom);
        }
        v.setLayoutParams(vparam);
        return v;
    }

    private View createVerticalSplitLine(Context context, int top,
                                         int bottom) {
        return createSplitLine(context, top, bottom, LinearLayout.VERTICAL,
                false);
    }

    private LinearLayout createButtons(Context context, int contentWidth) {
        LinearLayout cvll = new LinearLayout(context);
        cvll.setOrientation(LinearLayout.HORIZONTAL);
        cvll.setGravity(Gravity.CENTER);
        if (btnsenum == DialogButtonsEnum.YesNo) {
            Button nobtn = new DialogButton(context,
                    DialogButtonEnum.No.getDes(), true);
            nobtn.setTag(DialogButtonEnum.No.getValue());
            cvll.addView(nobtn);

            cvll.addView(createVerticalSplitLine(context, 0, 0));

            Button yesbtn = new DialogButton(context,
                    DialogButtonEnum.Yes.getDes(), false);
            yesbtn.setTag(DialogButtonEnum.Yes.getValue());
            cvll.addView(yesbtn);
        } else if (btnsenum == DialogButtonsEnum.ConfirmCancel) {
            Button cancelbtn = new DialogButton(context,
                    DialogButtonEnum.Cancel.getDes(), true);
            cancelbtn.setTag(DialogButtonEnum.Cancel.getValue());
            cvll.addView(cancelbtn);

            cvll.addView(createVerticalSplitLine(context, 0, 0));

            Button confirmbtn = new DialogButton(context,
                    DialogButtonEnum.Confirm.getDes(), false);
            confirmbtn.setTag(DialogButtonEnum.Confirm.getValue());
            cvll.addView(confirmbtn);
        } else if (btnsenum == DialogButtonsEnum.Confirm) {
            Button confrimbtn = new DialogButton(context,
                    DialogButtonEnum.Confirm.getDes(), false);
            confrimbtn.setTag(DialogButtonEnum.Confirm.getValue());
            cvll.addView(confrimbtn);
        } else if (btnsenum == DialogButtonsEnum.CancelLoginReLogin) {
            Button cancelloginbtn = new DialogButton(context,
                    DialogButtonEnum.CancelLogin.getDes(), true);
            cancelloginbtn.setTag(DialogButtonEnum.CancelLogin.getValue());
            cvll.addView(cancelloginbtn);

            cvll.addView(createVerticalSplitLine(context, 0, 0));

            Button reloginbtn = new DialogButton(context,
                    DialogButtonEnum.ReLogin.getDes(), false);
            reloginbtn.setTag(DialogButtonEnum.ReLogin.getValue());
            cvll.addView(reloginbtn);
        } else if (btnsenum == DialogButtonsEnum.Custom) {
            for (int i = 0; i < cmds.length; i++) {
                CmdItem cmd = cmds[i];
                if (i > 0 && (i % 2) == 1) {
                    cvll.addView(createVerticalSplitLine(context, 0, 0));
                }
                Button cmdbtn = new DialogButton(context,
                        cmd.getCommandName(), (i + 1) < cmds.length);
                cmdbtn.setEnabled(cmd.isEnable());
                cmdbtn.setTag(cmd.getCommandId());
                cmdbtn.setTag(CUSTOM_BTN_TAG, "1872645244");
                if (cmd.getTextColor() != 0) {
                    cmdbtn.setTextColor(cmd.getTextColor());
                }
                if (cmd.getBgresid() != 0) {
                    cmdbtn.setBackgroundResource(cmd.getBgresid());
                }
                cvll.addView(cmdbtn);
            }
        }
        return cvll;
    }

    private class DialogButton extends Button {
        public DialogButton(Context context, String text, boolean splitflag) {
            super(context);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(0, PixelUtils.dip2px(context, 32));
            param.weight = 1;
            if (splitflag) {
                param.setMargins(0, 0, PixelUtils.dip2px(context, 10), 0);
            }
            this.setId(GlobalUtils.getHashCodeByUUID());
            this.setGravity(Gravity.CENTER);
            this.setBackgroundResource(R.drawable.dialog_button_bg);
            this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            this.setTextColor(Color.WHITE);
            this.setLayoutParams(param);
            this.setText(text);
            this.setPadding(0, PixelUtils.dip2px(context, 5), 0,
                    PixelUtils.dip2px(context, 5));
        }
    }
}
