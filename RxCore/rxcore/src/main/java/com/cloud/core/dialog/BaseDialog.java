package com.cloud.core.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cloud.core.beans.BaseDialogRes;
import com.cloud.core.beans.CmdItem;
import com.cloud.core.enums.DialogButtonEnum;
import com.cloud.core.enums.DialogButtonsEnum;
import com.cloud.core.utils.PixelUtils;

public class BaseDialog extends Dialog {

    /**
     * 触摸对话框区域外是否消失
     */
    private boolean iscancelbynotarea = true;

    private String title = "";
    private String msg = "";

    private DialogButtonsEnum btnsenum;

    private BaseDialogRes dgres;

    private Context context;

    /**
     * 0:文本消息;1:自定义视图;
     */
    private int dialogtype = 0;

    private View contentview = null;

    private CmdItem[] cmds;

    private boolean isvisiblebuttons = true;

    /**
     * 是否显示标题栏
     */
    private boolean isShowTitle = false;

    /**
     * 是否显示关闭按钮
     */
    private boolean isShowClose = true;

    private View.OnClickListener yeslistener;

    private View.OnClickListener nolistener;

    private View.OnClickListener confirmlistener;

    private View.OnClickListener customlistener;

    private View.OnClickListener cancellistener;

    private View.OnClickListener closelistener;

    private View.OnClickListener cancelloginlistener;

    private View.OnClickListener reloginlistener;

    private int mcontentgravity = Gravity.CENTER;

    private int mtitlegravity = Gravity.LEFT;

    public BaseDialog(Context context, boolean iscancelbynotarea,
                      DialogButtonsEnum btnsenum, int theme) {
        super(context, theme);
        this.iscancelbynotarea = iscancelbynotarea;
        this.btnsenum = btnsenum;
        this.context = context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.iscancelbynotarea) {
            if (isShowing()) {
                dismiss();
            }
            return super.onTouchEvent(event);
        } else {
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (iscancelbynotarea) {
                if (isShowing()) {
                    dismiss();
                }
                return super.onKeyDown(keyCode, event);
            } else {
                return true;
            }
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * 窗口之外单击
     *
     * @param iscancelbynotarea true:隐藏窗口;false:不作处理;
     */
    public void isClickOutSide(boolean iscancelbynotarea) {
        this.iscancelbynotarea = iscancelbynotarea;
        if (iscancelbynotarea) {
            this.getWindow().setType(
                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
        }
    }

    public void setResource(BaseDialogRes dgres) {
        this.dgres = dgres;
        if (dgres.animation != 0) {
            getWindow().setWindowAnimations(dgres.animation);
        } else {
            getWindow().setWindowAnimations(0);
        }
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
     * 设置标题对齐方式
     *
     * @param gravity
     */
    public void setTitleGravity(int gravity) {
        this.mtitlegravity = gravity;
    }

    public void setShowTitle(boolean isShowTitle) {
        this.isShowTitle = isShowTitle;
    }

    /**
     * @param isShowClose 设置是否显示关闭按钮
     */
    public void setShowClose(boolean isShowClose) {
        this.isShowClose = isShowClose;
    }

    public void show(String title, String msg) {
        this.title = title;
        this.msg = msg;
        this.dialogtype = 0;
        setContentView(new BaseDialogLayout(context));
        super.show();
    }

    public void show(String title, View contentview) {
        this.title = title;
        this.dialogtype = 1;
        this.contentview = contentview;
        setContentView(new BaseDialogLayout(context));

        super.show();
    }

    private int getDialogWidth() {
        int minw = PixelUtils.dip2px(context, 200), maxw = PixelUtils.dip2px(
                context, 600);
        DisplayMetrics dm = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int dw = dm.widthPixels * 4 / 5;
        if (dw <= minw) {
            return minw;
        } else if (dw >= maxw) {
            return maxw;
        } else {
            return dw;
        }
    }

    private int getDialogMaxHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int dh = dm.heightPixels * 2 / 3;
        return dh;
    }

    private class BaseDialogLayout extends LinearLayout {

        public BaseDialogLayout(Context context) {
            super(context);
            LayoutParams mainparam = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            this.setGravity(Gravity.CENTER);
            this.setOrientation(HORIZONTAL);
            this.setLayoutParams(mainparam);
            int dgwidth = getDialogWidth();
            this.addView(createDialogContainer(context, dgwidth));
        }

        private LinearLayout createDialogContainer(Context context, int dgwidth) {
            LayoutParams containerparam = new LayoutParams(
                    dgwidth, LayoutParams.WRAP_CONTENT);
            LinearLayout ll = new LinearLayout(context);
            ll.setLayoutParams(containerparam);
            ll.setOrientation(VERTICAL);
            ll.setBackgroundResource(dgres.dialogbackground);
            ll.addView(createTitle(context, dgwidth));
            ll.addView(createContentView(context, dgwidth));
            return ll;
        }

        private LinearLayout createTitle(Context context, int dgwidth) {
            LayoutParams titlellparam = new LayoutParams(
                    dgwidth, LayoutParams.WRAP_CONTENT);
            LinearLayout titlell = new LinearLayout(context);
            titlell.setOrientation(HORIZONTAL);
            titlell.setLayoutParams(titlellparam);
            titlell.setGravity(Gravity.RIGHT);
            LayoutParams tvparam = new LayoutParams(
                    0, LayoutParams.WRAP_CONTENT);
            tvparam.weight = 1;
            TextView tv = new TextView(context);
            tv.setGravity(Gravity.LEFT);
            if (isShowTitle) {
                tv.setPadding(PixelUtils.dip2px(context, 6),
                        PixelUtils.dip2px(context, 8),
                        PixelUtils.dip2px(context, 6),
                        PixelUtils.dip2px(context, 8));
                tv.setText(title);
            } else {
                tv.setPadding(0, 0, 0, 0);
                tv.setText("");
            }
            tv.setGravity(mtitlegravity);
            tv.setVisibility(isShowTitle ? View.VISIBLE : View.GONE);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            tv.setSingleLine(true);
            tv.setTextColor(Color.rgb(50, 50, 50));
            titlell.addView(tv, tvparam);
            LayoutParams closeparam = new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            closeparam.gravity = Gravity.CENTER_VERTICAL;
            closeparam.setMargins(PixelUtils.dip2px(context, 5),
                    PixelUtils.dip2px(context, 8),
                    PixelUtils.dip2px(context, 12),
                    PixelUtils.dip2px(context, 8));
            ImageView closeiv = new ImageView(context);
            closeiv.setLayoutParams(closeparam);
            closeiv.setScaleType(ScaleType.MATRIX);
            if (dgres.closebuttonbackground != 0) {
                closeiv.setBackgroundResource(dgres.closebuttonbackground);
            }
            closeiv.setOnClickListener(closelistener);
            closeiv.setVisibility(isShowClose ? View.VISIBLE : View.GONE);
            titlell.addView(closeiv);
            return titlell;
        }

        /**
         * 水平分隔线
         *
         * @param context
         * @param top
         * @param bottom
         * @param orientation
         * @return
         */
        private View createSplitLine(Context context, int top, int bottom,
                                     int orientation, boolean ismargin) {
            View v = new View(context);
            LayoutParams vparam = null;
            if (LinearLayout.HORIZONTAL == orientation) {
                vparam = new LayoutParams(
                        LayoutParams.MATCH_PARENT, 1);
                if (ismargin) {
                    vparam.setMargins(PixelUtils.dip2px(context, 8), top,
                            PixelUtils.dip2px(context, 8), bottom);
                } else {
                    vparam.setMargins(0, top, 0, bottom);
                }
                if (dgres != null && dgres.splitlinebackground > 0) {
                    v.setBackgroundResource(dgres.splitlinebackground);
                } else {
                    v.setBackgroundColor(Color.rgb(229, 229, 229));
                }
            } else {
                vparam = new LayoutParams(1,
                        LayoutParams.MATCH_PARENT);
                vparam.setMargins(0, top, 0, bottom);
            }
            v.setLayoutParams(vparam);
            return v;
        }

        /**
         * 垂直分隔线
         *
         * @param context
         * @param top
         * @param bottom
         * @return
         */
        private View createVerticalSplitLine(Context context, int top,
                                             int bottom) {
            return createSplitLine(context, top, bottom, LinearLayout.VERTICAL,
                    false);
        }

        private LinearLayout createContentView(Context context, int dgwidth) {
            LayoutParams cvllparam = new LayoutParams(
                    dgwidth, LayoutParams.WRAP_CONTENT);
            LinearLayout cvll = new LinearLayout(context);
            cvll.setLayoutParams(cvllparam);
            cvll.setOrientation(VERTICAL);
            if (isShowTitle) {
                cvll.addView(createSplitLine(context, 0,
                        PixelUtils.dip2px(context, 8), LinearLayout.HORIZONTAL,
                        true));
            }
            cvll.addView(createCViewContainer(context));
            if (isvisiblebuttons) {
                cvll.addView(createSplitLine(context,
                        PixelUtils.dip2px(context, 8), 0,
                        LinearLayout.HORIZONTAL, false));
                cvll.addView(createButtons(context));
            } else {
                cvll.setPadding(0, 0, 0, PixelUtils.dip2px(context, 8));
            }
            return cvll;
        }

        private ScrollView createCViewContainer(Context context) {
            ScrollView sv = new ScrollView(context);
            LayoutParams cllparam = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            LinearLayout cll = new LinearLayout(context);
            cll.setPadding(PixelUtils.dip2px(context, 2), 0,
                    PixelUtils.dip2px(context, 2), 0);
            cll.setLayoutParams(cllparam);
            cll.setOrientation(LinearLayout.HORIZONTAL);
            switch (dialogtype) {
                case 0:
                    cll.addView(createMessageView(context, msg));
                    break;
                case 1:
                    LayoutParams contentViewParmas = new LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.WRAP_CONTENT);
                    cll.removeAllViews();
                    cll.setPadding(PixelUtils.dip2px(context, 0.5f), PixelUtils.dip2px(context, 0.5f),
                            PixelUtils.dip2px(context, 0.5f), 0);
                    cll.addView(contentview, contentViewParmas);
                    break;
            }
            cll.measure(MeasureSpec.UNSPECIFIED,
                    MeasureSpec.UNSPECIFIED);
            int cheight = cll.getMeasuredHeight();
            int cmaxheight = getDialogMaxHeight();
            if (cheight <= cmaxheight) {
                LayoutParams svparam = new LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT);
                sv.setLayoutParams(svparam);
            } else {
                LayoutParams svparam = new LayoutParams(
                        LayoutParams.MATCH_PARENT, cmaxheight);
                sv.setLayoutParams(svparam);
            }
            sv.addView(cll);
            return sv;
        }

        private TextView createMessageView(Context context, String msg) {
            LayoutParams cvllparam = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
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

        private LinearLayout createButtons(Context context) {
            LayoutParams cvllparam = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            cvllparam.leftMargin = PixelUtils.dip2px(context, 8);
            cvllparam.rightMargin = PixelUtils.dip2px(context, 8);
            cvllparam.topMargin = PixelUtils.dip2px(context, 8);
            cvllparam.bottomMargin = PixelUtils.dip2px(context, 8);
            LinearLayout cvll = new LinearLayout(context);
            cvll.setLayoutParams(cvllparam);
            cvll.setOrientation(HORIZONTAL);
            cvll.setGravity(Gravity.CENTER);
            if (btnsenum == DialogButtonsEnum.YesNo) {
                Button nobtn = new DialogButton(context,
                        DialogButtonEnum.No.getDes(), true);
                nobtn.setTag(DialogButtonEnum.No.getValue());
                nobtn.setOnClickListener(nolistener);
                cvll.addView(nobtn);

                cvll.addView(createVerticalSplitLine(context, 0, 0));

                Button yesbtn = new DialogButton(context,
                        DialogButtonEnum.Yes.getDes(), false);
                yesbtn.setTag(DialogButtonEnum.Yes.getValue());
                yesbtn.setOnClickListener(yeslistener);
                cvll.addView(yesbtn);
            } else if (btnsenum == DialogButtonsEnum.ConfirmCancel) {
                Button cancelbtn = new DialogButton(context,
                        DialogButtonEnum.Cancel.getDes(), true);
                cancelbtn.setTag(DialogButtonEnum.Cancel.getValue());
                cancelbtn.setOnClickListener(cancellistener);
                cvll.addView(cancelbtn);

                cvll.addView(createVerticalSplitLine(context, 0, 0));

                Button confirmbtn = new DialogButton(context,
                        DialogButtonEnum.Confirm.getDes(), false);
                confirmbtn.setTag(DialogButtonEnum.Confirm.getValue());
                confirmbtn.setOnClickListener(confirmlistener);
                cvll.addView(confirmbtn);
            } else if (btnsenum == DialogButtonsEnum.Confirm) {
                Button confrimbtn = new DialogButton(context,
                        DialogButtonEnum.Confirm.getDes(), false);
                confrimbtn.setTag(DialogButtonEnum.Confirm.getValue());
                confrimbtn.setOnClickListener(confirmlistener);
                cvll.addView(confrimbtn);
            } else if (btnsenum == DialogButtonsEnum.CancelLoginReLogin) {
                Button cancelloginbtn = new DialogButton(context,
                        DialogButtonEnum.CancelLogin.getDes(), true);
                cancelloginbtn.setTag(DialogButtonEnum.CancelLogin.getValue());
                cancelloginbtn.setOnClickListener(cancelloginlistener);
                cvll.addView(cancelloginbtn);

                cvll.addView(createVerticalSplitLine(context, 0, 0));

                Button reloginbtn = new DialogButton(context,
                        DialogButtonEnum.ReLogin.getDes(), false);
                reloginbtn.setTag(DialogButtonEnum.ReLogin.getValue());
                reloginbtn.setOnClickListener(reloginlistener);
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
                    cmdbtn.setOnClickListener(customlistener);
                    cmdbtn.setTag(cmd.getCommandId());
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
                LayoutParams param = new LayoutParams(0, PixelUtils.dip2px(context, 32));
                param.weight = 1;
                if (splitflag) {
                    param.setMargins(0, 0, PixelUtils.dip2px(context, 10), 0);
                }
                this.setGravity(Gravity.CENTER);
                this.setBackgroundResource(dgres.buttonbackground);
                this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                this.setTextColor(dgres.buttonTextColor);
                this.setLayoutParams(param);
                this.setText(text);
                this.setPadding(0, PixelUtils.dip2px(context, 5), 0,
                        PixelUtils.dip2px(context, 5));
            }
        }
    }

    public void setButtons(CmdItem[] cmds) {
        this.cmds = cmds;
    }

    /**
     * true:显示buttons;false:隐藏
     *
     * @param visible
     */
    public void setVisibleButtons(boolean visible) {
        this.isvisiblebuttons = visible;
    }

    /**
     * yes click listener 需在show之前注册
     *
     * @param listener
     */
    public void setOnYesClickListener(View.OnClickListener listener) {
        yeslistener = listener;
    }

    /**
     * no click listener 需在show之前注册
     *
     * @param listener
     */
    public void setOnNoClickListener(View.OnClickListener listener) {
        nolistener = listener;
    }

    /**
     * confirm click listener 需在show之前注册
     *
     * @param listener
     */
    public void setOnConfirmClickListener(View.OnClickListener listener) {
        confirmlistener = listener;
    }

    /**
     * DialogButtonsEnum.Custom类型 click listener 需在show之前注册
     * CmdItem实体中CommandId将通过Button的tag属性传递
     */
    public void setOnCustomClickListener(View.OnClickListener listener) {
        customlistener = listener;
    }

    /**
     * cancel click listener 需在show之前注册
     *
     * @param listener
     */
    public void setOnCancelClickListener(View.OnClickListener listener) {
        cancellistener = listener;
    }

    /**
     * close click listener 关闭窗口
     *
     * @param listener
     */
    public void setOnCloseClickListener(View.OnClickListener listener) {
        closelistener = listener;
    }

    /**
     * close click listener 取消登录
     *
     * @param listener
     */
    public void setOnCancelLoginClickListener(View.OnClickListener listener) {
        cancelloginlistener = listener;
    }

    /**
     * relogin click listener 重新登录
     *
     * @param listener
     */
    public void setOnReLoginClickListener(View.OnClickListener listener) {
        reloginlistener = listener;
    }
}
