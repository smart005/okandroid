package com.cloud.core.widgets;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.cloud.core.logger.Logger;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/9/10
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class TextIndentation extends TextView {

    /**
     * 内容
     */
    private CharSequence textContent = "";
    /**
     * 设置文本内容颜色
     */
    private int textContentColor = 0;
    /**
     * 是否首行缩进
     */
    private boolean firstLineIndentation = false;

    /**
     * 设置是否首行缩进
     *
     * @param firstLineIndentation
     */
    public void setFirstLineIndentation(boolean firstLineIndentation) {
        this.firstLineIndentation = firstLineIndentation;
    }

    /**
     * 设置文本内容
     *
     * @param textContent
     */
    public void setTextContent(CharSequence textContent) {
        this.textContent = textContent;
    }

    public CharSequence getTextContent() {
        return this.getText();
    }

    public void setTextContentColor(int textContentColor) {
        this.textContentColor = textContentColor;
    }

    public TextIndentation(Context context) {
        super(context);
    }

    public TextIndentation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextIndentation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 获取文本内容
     *
     * @param content
     */
    public SpannableStringBuilder buildContent() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        try {
            if (firstLineIndentation) {
                ssb.append("空空");
                ForegroundColorSpan emptyColorSpan = new ForegroundColorSpan(Color.TRANSPARENT);
                ssb.setSpan(emptyColorSpan, 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if (!TextUtils.isEmpty(textContent)) {
                int startat = ssb.length();
                ssb.append(textContent);
                if (textContentColor != 0) {
                    ForegroundColorSpan textColorSpan = new ForegroundColorSpan(textContentColor);
                    ssb.setSpan(textColorSpan, startat, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        } catch (Exception e) {
            Logger.L.error("set content error:", e);
        }
        return ssb;
    }
}
