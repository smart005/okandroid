package com.cloud.resources.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloud.resources.R;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/7/7
 * @Description:设置appext:hint后永久显示
 * @Modifier:
 * @ModifyContent:
 */
public class EditHintText extends RelativeLayout {

    private String hint = "";

    public EditHintText(Context context) {
        super(context);
        init(false, null);
    }

    public EditHintText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(true, attrs);
    }

    public EditHintText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(true, attrs);
    }

    private void init(boolean flag, AttributeSet attrs) {
        if (flag) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EditHintText);
            hint = a.getString(R.styleable.EditHintText_hint);
            a.recycle();
        }
    }

    private TextView buildRemindText() {

        TextView textView = new TextView(getContext());

        return textView;
    }
}
