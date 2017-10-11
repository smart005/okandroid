package com.cloud.resources.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloud.resources.R;

/**
 * @Author lijinghuan
 * @Email: ljh0576123@163.com
 * @CreateTime:2016/4/16 14:45
 * @Description: 文件分散填充（保持两端对齐）
 * <com.frong.resources.widget.TextDispersedFilling
 * android:id="@+id/text_tdf"
 * style="@style/text_def_style"
 * android:layout_width="wrap_content"
 * android:layout_height="wrap_content"
 * appext:dispersedFilling="16dp"
 * appext:suffixSymbol="："
 * appext:text="" />
 * <p/>
 * textTdf.setTextColor(getResources().getColor(R.color.text_def_color));
 * textTdf.setTextSize(14);
 */
public class TextDispersedFilling extends LinearLayout {

    /**
     * 文本
     */
    private String text = "";
    /**
     * 文本颜色
     */
    private int textColor = 0;
    /**
     * 文本大小
     */
    private float textSize = 12;
    /**
     * 分散间距
     */
    private int dispersedSpacing = 0;
    /**
     * 后缀符号
     */
    private String suffixSymbol = "";

    private boolean initFlag = false;

    public TextDispersedFilling(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(true, attrs);
    }

    public TextDispersedFilling(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(true, attrs);
    }

    public TextDispersedFilling(Context context) {
        super(context);
        init(false, null);
    }

    private void init(boolean flag, AttributeSet attrs) {
        this.initFlag = flag;
        this.setOrientation(HORIZONTAL);
        if (flag) {
            TypedArray a = getContext().obtainStyledAttributes(attrs,
                    R.styleable.TextDispersedFilling);
            dispersedSpacing = (int) a.getDimension(R.styleable.TextDispersedFilling_dispersedFilling, 0);
            text = a.getString(R.styleable.TextDispersedFilling_text);
            suffixSymbol = a.getString(R.styleable.TextDispersedFilling_suffixSymbol);
            a.recycle();
        } else {
            ViewGroup.LayoutParams vgparam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            this.setLayoutParams(vgparam);
        }
        buildItems();
    }

    /**
     * 设置分散间距
     *
     * @param dispersedSpacing
     */
    public void setDispersedSpacing(int dispersedSpacing) {
        if (dispersedSpacing <= 0) {
            return;
        }
        this.dispersedSpacing = dispersedSpacing;
        for (int i = 0; i < this.getChildCount(); i++) {
            if (this.getChildAt(i) instanceof TextView) {
                LayoutParams tvparam = (LayoutParams) this.getChildAt(i).getLayoutParams();
                if (tvparam != null) {
                    tvparam.setMargins(0, 0, dispersedSpacing, 0);
                }
            }
        }
    }

    /**
     * 设置文本
     *
     * @param text
     */
    public void setText(String text) {
        if (!TextUtils.isEmpty(text) && !TextUtils.equals(this.text, text)) {
            this.text = text;
            buildItems();
        }
    }

    /**
     * 设置文本颜色
     *
     * @param textColor
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
        if (textColor != 0) {
            for (int i = 0; i < this.getChildCount(); i++) {
                if (this.getChildAt(i) instanceof TextView) {
                    TextView tv = (TextView) this.getChildAt(i);
                    tv.setTextColor(textColor);
                }
            }
        }
    }

    /**
     * 设置文本大小
     *
     * @param textSize
     */
    public void setTextSize(float textSize) {
        this.textSize = textSize;
        if (textSize != 0) {
            for (int i = 0; i < this.getChildCount(); i++) {
                if (this.getChildAt(i) instanceof TextView) {
                    TextView tv = (TextView) this.getChildAt(i);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                }
            }
        }
    }

    private TextView buildText(CharSequence text, boolean dispersedFlag) {
        LayoutParams tvparam = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        if (dispersedFlag) {
            tvparam.setMargins(0, 0, dispersedSpacing, 0);
        } else {
            if (!TextUtils.isEmpty(suffixSymbol)) {
                text = text.toString() + suffixSymbol;
            }
        }
        TextView tv = new TextView(getContext());
        tv.setLayoutParams(tvparam);
        tv.setText(text);
        if (textColor != 0) {
            tv.setTextColor(textColor);
        }
        if (textSize != 0) {
            if (initFlag) {
                tv.setTextSize(textSize);
            } else {
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            }
        }
        return tv;
    }

    private void buildItems() {
        this.removeAllViews();
        text = text.trim();
        for (int i = 0; i < text.length(); i++) {
            this.addView(buildText(String.valueOf(text.charAt(i)), (i + 1) < text.length()));
        }
    }
}
