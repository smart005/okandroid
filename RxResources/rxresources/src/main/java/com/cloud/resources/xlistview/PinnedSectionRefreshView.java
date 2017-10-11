package com.cloud.resources.xlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * @param <T>
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016-1-20 上午8:57:10
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class PinnedSectionRefreshView extends RelativeLayout {

    private XRefreshEmptyView mScrollView;
    private PinnedSectionRefreshListView mListView;
    private PinnedSectionRefreshViewListener mpsrvlistener = null;
    private boolean enableEmptyView = false;

    private OnXListViewItemClickListener onXListViewItemClickListener = null;

    public PinnedSectionRefreshView(Context context) {
        super(context);
        initView(context, null);
    }

    public PinnedSectionRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public PinnedSectionRefreshView(Context context, AttributeSet attrs,
                                    int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs);
    }

    public void setOnPinnedSectionRefreshViewListener(
            PinnedSectionRefreshViewListener listener) {
        this.mpsrvlistener = listener;
    }

    /**
     * @param onXListViewScrollListener
     */
    public void setOnXListViewScrollListener(OnXListViewScrollListener listener) {
        mListView.setOnXListViewScrollListener(listener);
    }

    /**
     * @param onXListViewItemClickListener
     */
    public void setOnXListViewItemClickListener(OnXListViewItemClickListener listener) {
        mListView.setOnXListViewItemClickListener(listener);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutParams mainparam = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        this.setLayoutParams(mainparam);
        mListView = new PinnedSectionRefreshListView(context, attrs);
        mListView.setXListViewListener(mlvlistener);
        mScrollView = new XRefreshEmptyView(context, attrs);
        mScrollView.setXRefreshListener(mlvlistener);
        this.addView(mListView);
        this.addView(mScrollView);
        mListView.setVisibility(View.VISIBLE);
        mScrollView.setVisibility(View.INVISIBLE);
    }

    public PinnedSectionRefreshListView getListView() {
        return mListView;
    }

    public void setEmptyDataView(View v) {
        if (v != null && enableEmptyView) {
            mScrollView.addContentView(v);
        }
    }

    /**
     * 是否启动空视图（false则不管数据是否空都不显示空视图）
     *
     * @param flag
     */
    public void setEnableEmptyView(boolean flag) {
        this.enableEmptyView = flag;
    }

    public XRefreshEmptyView getRefreshScrollView() {
        return mScrollView;
    }

    public void showListView() {
        mListView.setVisibility(View.VISIBLE);
        mScrollView.setVisibility(View.INVISIBLE);
    }

    public void showScrollView() {
        if (enableEmptyView) {
            mScrollView.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.INVISIBLE);
        }
    }

    public void setAutoPullDown(boolean auto) {
        showListView();
        mListView.setAutoPullDown(auto);
    }

    public void setPullLoadEnable(boolean enable) {
        mListView.setPullLoadEnable(enable);
    }

    public void setPullRefreshEnable(boolean enable) {
        mListView.setPullRefreshEnable(enable);
    }

    private OnXListViewListener mlvlistener = new OnXListViewListener() {
        @Override
        public void onRefresh() {
            if (mpsrvlistener != null) {
                mpsrvlistener.onRefresh();
            }
        }

        @Override
        public void onLoadMore() {
            if (mpsrvlistener != null) {
                mpsrvlistener.onLoadMore();
            }
        }
    };
}
