package com.cloud.resources.xrecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cloud.resources.R;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/10
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class FamiliarDefaultLoadMoreView extends FrameLayout implements IFamiliarLoadMore {
    private ProgressBar mPbLoad;
    private TextView mTvLoadText;

    private boolean isLoading = false;

    public FamiliarDefaultLoadMoreView(Context context) {
        this(context, null);
    }

    public FamiliarDefaultLoadMoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FamiliarDefaultLoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.frv_view_def_load_more, this);
        mPbLoad = (ProgressBar) findViewById(R.id.frv_pbLoad);
        mTvLoadText = (TextView) findViewById(R.id.frv_tvLoadText);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void showNormal() {
        isLoading = false;
        mPbLoad.setVisibility(GONE);
        mTvLoadText.setText("松开加载更多");
    }

    @Override
    public void showLoading() {
        isLoading = true;
        mPbLoad.setVisibility(VISIBLE);
        mTvLoadText.setText("加载中，请稍等...");
    }

    @Override
    public boolean isLoading() {
        return isLoading;
    }
}
