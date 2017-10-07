package com.cloud.core;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.cloud.core.logger.Logger;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-9-14 上午10:26:16
 * @Description: 自定义进度条
 * @Modifier:
 * @ModifyContent:
 */
public class ProgressBarIV extends ImageView {

    public ProgressBarIV(Context context, AttributeSet attrs) {
        super(context, attrs);
        startAnim();
    }

    public ProgressBarIV(Context context) {
        super(context);
    }

    public void startAnim() {
        try {
            AnimationDrawable anim = (AnimationDrawable) getBackground();
            if (anim != null) {
                anim.start();
            }
        } catch (Exception e) {
            Logger.L.error("start ProgressBarIV anim error:", e);
        }
    }
}