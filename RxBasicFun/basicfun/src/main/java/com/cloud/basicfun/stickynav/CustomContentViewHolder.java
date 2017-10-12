package com.cloud.basicfun.stickynav;

import android.content.Context;
import android.os.Handler;
import android.view.View;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/12/24
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public abstract class CustomContentViewHolder {

    private View contentView = null;

    private Context context = null;

    public CustomContentViewHolder() {
    }

    protected CustomContentViewHolder(Context context, int layout) {
        this.context = context;
        contentView = View.inflate(context, layout, null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onInstance(getContext());
            }
        }, 380);
    }

    public View getContentView() {
        return this.contentView;
    }

    protected Context getContext() {
        return this.context;
    }

    protected abstract void onInstance(Context context);
}
