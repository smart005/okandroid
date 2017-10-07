/**
 *
 */
package com.cloud.core.tagtext;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloud.core.utils.GlobalUtils;

/**
 * @Author LIJINGHUAN
 * @Email:ljh0576123@163.com
 * @CreateTime:2016年2月29日 下午8:05:04
 * @Description: 标签项视图
 * @Modifier:
 * @ModifyContent:
 */
public class TagItemView extends LinearLayout {

    /**
     * @param context
     */
    public TagItemView(Context context, CharSequence text, int marginLeft,
                       int bgresid, int tvColor, int tvSize) {
        super(context);
        RelativeLayout.LayoutParams rlparam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        rlparam.setMargins(marginLeft, 0, 0, 0);
        this.setLayoutParams(rlparam);
        this.setGravity(Gravity.CENTER);
        this.setId(GlobalUtils.getHashCodeByUUID());
        this.setTag(TagTextPkg.TAG_ITEM_VIEW_TAG);
        if (bgresid != 0) {
            this.setBackgroundResource(bgresid);
        }
        this.addView(buildTextView(context, text, tvColor, tvSize));
    }

    public TagItemView(Context context, int marginLeft, int bgresid) {
        super(context);
        RelativeLayout.LayoutParams rlparam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        rlparam.setMargins(marginLeft, 0, 0, 0);
        this.setLayoutParams(rlparam);
        this.setGravity(Gravity.CENTER);
        this.setId(GlobalUtils.getHashCodeByUUID());
        this.setTag(TagTextPkg.TAG_ITEM_VIEW_TAG);
        if (bgresid != 0) {
            this.setBackgroundResource(bgresid);
        }
        this.addView(buildImageView(context, bgresid));
    }

    private ImageView buildImageView(Context context, int bgresid) {
        LayoutParams ivParam = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        ivParam.gravity = Gravity.CENTER;
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(ivParam);
        imageView.setImageResource(bgresid);
        return imageView;
    }

    private TextView buildTextView(Context context, CharSequence text,
                                   int tvColor, int tvSize) {
        LayoutParams tvparam = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        tvparam.gravity = Gravity.CENTER;
        TextView tv = new TextView(context);
        tv.setLayoutParams(tvparam);
        tv.setText(text);
        if (tvColor != 0) {
            tv.setTextColor(tvColor);
        }
        if (tvSize > 0) {
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, tvSize);
        }
        return tv;
    }
}
