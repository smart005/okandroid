package com.cloud.core.intro;

import android.content.Context;
import android.util.TypedValue;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/6/12
 * @Description:更多(详情)标记内部视图
 * @Modifier:
 * @ModifyContent:
 */
public class MoreTagView extends TextView {

    public MoreTagView(Context context) {
        super(context);
        RelativeLayout.LayoutParams rlparam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(rlparam);
        this.setId(IntroPck.MORE_TAG_VIEW_ID);
    }

    public void build(IntroViewProperties properties, CharSequence text) {
        //文本大小
        if (properties.getTagTextSize() > 0) {
            this.setTextSize(TypedValue.COMPLEX_UNIT_SP, properties.getTagTextSize());
        }
        //文本颜色
        if (properties.getTagTextColor() != null) {
            this.setTextColor(properties.getTagTextColor());
        }
        //视图行间距
        if (properties.getViewLineSpacing() != 0) {
            this.setLineSpacing(properties.getViewLineSpacing(), 1);
        }
        //最大行数，超过设置的行数将以省略号显示
        if (properties.getLines() > 0) {
            this.setLines(properties.getLines());
        }
        this.setSingleLine(true);
        if (text != null) {
            this.setText(text);
        }
    }
}
