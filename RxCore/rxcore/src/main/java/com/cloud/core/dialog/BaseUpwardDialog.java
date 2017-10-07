package com.cloud.core.dialog;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloud.core.ObjectJudge;
import com.cloud.core.beans.BaseUpwardDialogParams;
import com.cloud.core.beans.CmdItem;
import com.cloud.core.beans.MenuDialogItem;
import com.cloud.core.enums.UpwardDialogContentType;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.PixelUtils;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-6-19 上午10:54:50
 * @Description: 菜单式dialog工具类
 * @Modifier:
 * @ModifyContent:
 */
public class BaseUpwardDialog extends PopupWindow {

    private final int pscreenll = 8886541;
    private final int panelcontainer_ll = 6473888;

    private String CANCEL_CMD_ID = "fdf5747832db48868f4476586abad6e4";
    private BaseUpwardDialogParams muparams = new BaseUpwardDialogParams();

    protected void onItemClickListener(View v, CmdItem citem) {

    }

    protected void onBuildContentView(Context context, View contentView) {

    }

    public void setBaseUpwardDialogParams(BaseUpwardDialogParams params) {
        this.muparams = params;
    }

    public void show(View parent) {
        try {
            View container = new MenuPanelLayout(muparams.getContext());
            final LinearLayout mll = (LinearLayout) container
                    .findViewById(panelcontainer_ll);
            if (muparams.getContenttype() == UpwardDialogContentType.Menus
                    .ordinal()) {
                buildMenuItems(mll);
            } else if (muparams.getContenttype() == UpwardDialogContentType.Panel
                    .ordinal()) {
                View contentview = muparams.getContentView();
                if (muparams.getLayoutResid() != 0) {
                    contentview = View.inflate(muparams.getContext(),
                            muparams.getLayoutResid(), null);
                }
                onBuildContentView(muparams.getContext(), contentview);
                contentview.setBackgroundColor(muparams
                        .getPanelBackgroundColor());
                if (mll.indexOfChild(contentview) < 0) {
                    mll.addView(contentview);
                }
            }
            this.setContentView(container);
            this.setWidth(LayoutParams.MATCH_PARENT);
            this.setHeight(LayoutParams.WRAP_CONTENT);
            this.setFocusable(true);
            this.setAnimationStyle(muparams.getAnimBottom());
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            this.setBackgroundDrawable(dw);
            container.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    int height = mll.getTop();
                    int y = (int) event.getY();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (y < height) {
                            if (muparams.isCancelOutLayout()) {
                                dismiss();
                            }
                        }
                    }
                    return true;
                }
            });
            this.showAtLocation(parent, Gravity.BOTTOM
                    | Gravity.CENTER_HORIZONTAL, 0, muparams.getOffsetY());
        } catch (Exception e) {
            Logger.L.error("show upward dialog error:", e);
        }
    }

    private void buildMenuItems(LinearLayout container) {
        try {
            if (ObjectJudge.isNullOrEmpty(muparams.getMenuItems())) {
                return;
            }
            ItemCLayout mchildl = new ItemCLayout(muparams.getContext());
            if (!TextUtils.isEmpty(muparams.getTitle())) {
                CmdItem cancelcmd = new CmdItem("", muparams.getTitle());
                MenuDialogItem titleitement = new MenuDialogItem();
                titleitement.setCmdItem(cancelcmd);
                titleitement.setTextName(cancelcmd.getCommandName());
                if (muparams.getTitleColor() != 0) {
                    titleitement.setTextColorRGB(muparams.getTitleColor());
                } else {
                    titleitement.setTextColorRGB(Color.rgb(31, 31, 31));
                }
                TextView tv = new ViewItemLayout(muparams.getContext(),
                        titleitement);
                tv.setBackgroundResource(muparams.getFirstItemBackgroundResid());
                mchildl.addView(tv);
                mchildl.addView(createSplitLine(muparams.getContext()));
            }
            for (int i = 0; i < muparams.getMenuItems().size(); i++) {
                MenuDialogItem item = muparams.getMenuItems().get(i);
                TextView tv = new ViewItemLayout(muparams.getContext(), item);
                if (i == 0) {
                    if (TextUtils.isEmpty(muparams.getTitle())) {
                        tv.setBackgroundResource(muparams
                                .getFirstItemBackgroundResid());
                    } else {
                        tv.setBackgroundResource(muparams
                                .getItemBackgroundrResid());
                    }
                } else if ((i + 1) == muparams.getMenuItems().size()) {
                    tv.setBackgroundResource(muparams
                            .getEndItemBackgroundResid());
                } else {
                    tv.setBackgroundResource(muparams.getItemBackgroundrResid());
                }
                tv.setPadding(0, PixelUtils.dip2px(muparams.getContext(), 10),
                        0, PixelUtils.dip2px(muparams.getContext(), 10));
                mchildl.addView(tv);
                if ((i + 1) < muparams.getMenuItems().size()) {
                    mchildl.addView(createSplitLine(muparams.getContext()));
                }
            }
            if (container.indexOfChild(mchildl) < 0) {
                container.addView(mchildl);
            }
            CmdItem cancelcmd = new CmdItem(CANCEL_CMD_ID, "\u53d6\u6d88");
            MenuDialogItem cancelitement = new MenuDialogItem();
            cancelitement.setCmdItem(cancelcmd);
            cancelitement.setTextName(cancelcmd.getCommandName());
            if (muparams.getCancelTextColor() != 0) {
                cancelitement.setTextColorRGB(muparams.getCancelTextColor());
            } else {
                cancelitement.setTextColorRGB(Color.parseColor("#0087E2"));
            }
            TextView canceltv = new ViewItemLayout(muparams.getContext(),
                    cancelitement);
            LinearLayout.LayoutParams tvparam = (LinearLayout.LayoutParams) canceltv
                    .getLayoutParams();
            tvparam.setMargins(0, PixelUtils.dip2px(muparams.getContext(), 12),
                    0, 0);
            canceltv.setBackgroundResource(muparams.getMenuBackgroundResid());
            canceltv.setPadding(0,
                    PixelUtils.dip2px(muparams.getContext(), 10), 0,
                    PixelUtils.dip2px(muparams.getContext(), 10));
            if (container.indexOfChild(canceltv) < 0) {
                container.addView(canceltv);
            }
        } catch (Exception e) {
            Logger.L.error("base menu build item error:", e);
        }
    }

    private class ItemCLayout extends LinearLayout {
        public ItemCLayout(Context context) {
            super(context);
            LayoutParams vparam = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            this.setLayoutParams(vparam);
            this.setBackgroundResource(muparams.getMenuBackgroundResid());
            this.setPadding(1, 1, 1, 1);
            this.setOrientation(VERTICAL);
        }
    }

    private View createSplitLine(Context context) {
        LinearLayout.LayoutParams vparam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, PixelUtils.dip2px(
                context, 1));
        View v = new View(context);
        v.setLayoutParams(vparam);
        v.setBackgroundResource(muparams.getSplitlinecolor());
        return v;
    }

    private class MenuPanelLayout extends RelativeLayout {
        public MenuPanelLayout(Context context) {
            super(context);
            ViewGroup.LayoutParams vgparam = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            this.setLayoutParams(vgparam);
            this.setBackgroundResource(android.R.color.transparent);
            this.addView(new PanelScreenLL(context));
        }

        private class PanelScreenLL extends LinearLayout {
            public PanelScreenLL(Context context) {
                super(context);
                ViewGroup.LayoutParams vgparam = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                this.setLayoutParams(vgparam);
                this.setBackgroundResource(android.R.color.transparent);
                this.setOrientation(LinearLayout.HORIZONTAL);
                this.setGravity(Gravity.CENTER_HORIZONTAL);
                this.setId(pscreenll);
                this.setOnTouchListener(new OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        return muparams.isCancelOutLayout();
                    }
                });
                this.addView(new PanelContainer(context));
            }

            private class PanelContainer extends LinearLayout {
                public PanelContainer(Context context) {
                    super(context);
                    LayoutParams vgparam = new LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.WRAP_CONTENT);
                    vgparam.gravity = Gravity.BOTTOM;
                    this.setLayoutParams(vgparam);
                    this.setBackgroundResource(android.R.color.transparent);
                    this.setOrientation(LinearLayout.VERTICAL);
                    if (muparams.getContenttype() == UpwardDialogContentType.Menus
                            .ordinal()) {
                        this.setPadding(PixelUtils.dip2px(context, 6),
                                PixelUtils.dip2px(context, 6),
                                PixelUtils.dip2px(context, 6),
                                PixelUtils.dip2px(context, 12));
                    }
                    this.setId(panelcontainer_ll);
                }
            }
        }
    }

    private class ViewItemLayout extends TextView {
        public ViewItemLayout(Context context, MenuDialogItem itemobj) {
            super(context);
            LinearLayout.LayoutParams llparam = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            this.setLayoutParams(llparam);
            this.setPadding(PixelUtils.dip2px(context, 4),
                    PixelUtils.dip2px(context, 6),
                    PixelUtils.dip2px(context, 4),
                    PixelUtils.dip2px(context, 6));
            this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            this.setGravity(Gravity.CENTER);
            if (itemobj != null) {
                this.setText(itemobj.getTextName());
                if (itemobj.getTextColorResid() != 0) {
                    ColorStateList csl = this.getResources().getColorStateList(
                            itemobj.getTextColorResid());
                    this.setTextColor(csl);
                } else if (itemobj.getTextColorRGB() != 0) {
                    this.setTextColor(itemobj.getTextColorRGB());
                }
                this.setTag(itemobj.getCmdItem());
            }
            if (!TextUtils.isEmpty(itemobj.getCmdItem().getCommandId())) {
                this.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Object mditem = v.getTag();
                            if (mditem == null) {
                                return;
                            }
                            CmdItem citem = (CmdItem) mditem;
                            if (!TextUtils.equals(citem.getCommandId(),
                                    CANCEL_CMD_ID)) {
                                onItemClickListener(v, citem);
                            }
                            dismiss();
                        } catch (Exception e) {
                            Logger.L.error("base menu click item error:", e);
                        }
                    }
                });
            }
        }
    }

}
