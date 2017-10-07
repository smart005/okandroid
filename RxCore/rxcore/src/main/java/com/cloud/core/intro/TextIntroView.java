package com.cloud.core.intro;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/6/12
 * @Description:文本简介视图(IntroView内部视图)
 * @Modifier:
 * @ModifyContent:
 */
public class TextIntroView extends TextView {

    private int atForegroundColor = 0;

    public TextIntroView(Context context) {
        super(context);
        RelativeLayout.LayoutParams rlparam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(rlparam);
        this.setId(IntroPck.TEXT_INTRO_VIEW_ID);
    }

    public void setAtForegroundColor(int atForegroundColor) {
        this.atForegroundColor = atForegroundColor;
    }

    public void build(IntroViewProperties properties, CharSequence text) {
        if (properties == null) {
            return;
        }
        //文本大小
        if (properties.getTextSize() > 0) {
            this.setTextSize(TypedValue.COMPLEX_UNIT_SP, properties.getTextSize());
        }
        //文本颜色
        if (properties.getTextColor() != null) {
            this.setTextColor(properties.getTextColor());
        }
        //视图行间距
        if (properties.getViewLineSpacing() != 0) {
            this.setLineSpacing(properties.getViewLineSpacing(), 1);
        }
        //最大行数，超过设置的行数将以省略号显示
        if (properties.getLines() > 0) {
            this.setMaxLines(properties.getLines());
        }
        if (text != null) {
            SpannableStringBuilder ssb = new SpannableStringBuilder(text);
            if (atForegroundColor != 0) {
                int atindex = text.toString().indexOf("@");
                if (atindex >= 0) {
                    ForegroundColorSpan span_1 = new ForegroundColorSpan(atForegroundColor);
                    ssb.setSpan(span_1, atindex, ssb.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                }
            }
            this.setText(ssb);
        }
    }
}
