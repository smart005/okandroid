package com.cloud.resources.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.cloud.core.ObjectJudge;
import com.cloud.core.beans.CmdItem;
import com.cloud.core.enums.DialogButtonsEnum;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.JsonUtils;
import com.cloud.core.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import com.cloud.resources.R;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-7-16 上午8:55:31
 * @Description: 创建单选组件基类
 * @Modifier:
 * @ModifyContent:
 */
public class BaseRadioMessageBox extends BaseMessageBox {

    public interface OnRadioItemClickListener {
        void onRadioItemListener(View v);
    }

    private List<CmdItem> cmditems = new ArrayList<CmdItem>();
    private int RADIO_TAG_KEY = 1901620716;
    /**
     * 0 选中框在右边 1 选中框在左边
     */
    private int radiobuttontype = 0;
    /**
     * 选中框样式
     */
    private int buttondrawable = R.drawable.radio_icon_style;
    /**
     * 自定义标题示图
     */
    private View radioTiteView = null;
    /**
     * 是否默认选中第一项
     */
    private boolean isFirstChecked = false;

    private OnRadioItemClickListener onRadioItemClickListener = null;

    /**
     * @param cmditems
     */
    public void setCmditems(List<CmdItem> cmditems) {
        this.cmditems = cmditems;
    }

    /**
     * @param listener
     */
    public void setOnRadioItemClickListener(OnRadioItemClickListener listener) {
        this.onRadioItemClickListener = listener;
    }

    /**
     * 设置选中框位置
     * 0 右边 1 左边
     *
     * @param radiobuttontype
     */
    public void setRadiobuttontype(int radiobuttontype) {
        this.radiobuttontype = radiobuttontype;
    }

    /**
     * 设置选中框样式
     *
     * @param buttondrawable
     */
    public void setButtondrawable(int buttondrawable) {
        this.buttondrawable = buttondrawable;
    }

    /**
     * 设置自定义标题
     *
     * @param radioTiteView
     */
    public void setRadioTiteView(View radioTiteView) {
        this.radioTiteView = radioTiteView;
    }

    /**
     * 是否默认选择第一项
     *
     * @param firstChecked
     */
    public void setFirstChecked(boolean firstChecked) {
        isFirstChecked = firstChecked;
    }

    /**
     * 显示单选组件框(需先设置setCmditems)
     *
     * @param context
     */
    public void showRadioBox(Context context) {
        super.setContentView(new RadioViewLayout(context));
        super.setShowButtons(false);
        super.setShowClose(false);
        super.setShowTitle(false);
        super.show(context, DialogButtonsEnum.None);
    }

    private class RadioViewLayout extends LinearLayout {

        public RadioViewLayout(Context context) {
            super(context);
            init(context);
        }

        private void init(Context context) {
            try {
                LayoutParams vgparam = new LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT);
                this.setLayoutParams(vgparam);
                this.setPadding(0, 0, 0, 0);
                this.setOrientation(LinearLayout.VERTICAL);
                if (radioTiteView != null) {
                    this.addView(radioTiteView);
                }
                for (int i = 0; i < cmditems.size(); i++) {
                    CmdItem item = cmditems.get(i);
                    LinearLayout radioll = (LinearLayout) View.inflate(context,
                            R.layout.mb_radio_item_view, null);
                    RadioButton radiorb = (RadioButton) radioll
                            .findViewById(R.id.radio_item_rb);
                    if (radiobuttontype == 0) {
                        radiorb.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                        radiorb.setCompoundDrawablesWithIntrinsicBounds(
                                0, 0, buttondrawable, 0);
                    } else if (radiobuttontype == 1) {
                        radiorb.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                        radiorb.setCompoundDrawablesWithIntrinsicBounds(
                                buttondrawable, 0, 0, 0);
                    }
                    radiorb.setTag(RADIO_TAG_KEY, "radiotag");
                    radiorb.setText(item.getCommandName());
                    radiorb.setTag(JsonUtils.toStr(item));
                    radiorb.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clearCheckItems(v);
                            RadioButton rb = (RadioButton) v;
                            rb.setChecked(true);
                            if (onRadioItemClickListener != null) {
                                onRadioItemClickListener.onRadioItemListener(v);
                            }
                        }
                    });
                    if (isFirstChecked && i == 0) {
                        radiorb.setChecked(true);
                    }
                    this.addView(radioll);
                    if ((i + 1) < cmditems.size()) {
                        this.addView(createSplitLine(context));
                    }
                }
            } catch (Exception e) {
                Logger.L.error("init radio component error:", e);
            }
        }

        private View createSplitLine(Context context) {
            ViewGroup.LayoutParams vgparam = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 1);
            View v = new View(context);
            v.setLayoutParams(vgparam);
            v.setBackgroundResource(R.color.color_dcd9d5);
            return v;
        }

        private void clearCheckItems(View v) {
            List<View> lst = ViewUtils.getViewsByTag(this, RADIO_TAG_KEY,
                    "radiotag");
            if (ObjectJudge.isNullOrEmpty(lst)) {
                return;
            }
            for (View view : lst) {
                RadioButton rb = (RadioButton) view;
                rb.setChecked(false);
            }
        }
    }
}
