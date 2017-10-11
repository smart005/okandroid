package com.cloud.resources.xrecyclerview;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.util.AttributeSet;
import android.view.View;

import com.cloud.resources.xlistview.OnXListViewListener;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/9
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class XRefreshRecyclerView extends FamiliarRefreshRecyclerView {

    private OnXRecyclerViewItemClickListener onXRecyclerViewItemClickListener = null;
    private OnXListViewListener mListViewListener;

    public XRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        FamiliarRecyclerView recyclerView = getFamiliarRecyclerView();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.setOnItemClickListener(new FamiliarRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
                if (onXRecyclerViewItemClickListener != null) {
                    onXRecyclerViewItemClickListener.onItemClick(familiarRecyclerView, view, position);
                }
            }
        });
        this.setOnPullRefreshListener(new OnPullRefreshListener() {
            @Override
            public void onPullRefresh() {
                if (mListViewListener != null) {
                    mListViewListener.onRefresh();
                }
            }
        });
        this.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mListViewListener != null) {
                    mListViewListener.onLoadMore();
                }
            }
        });
    }

    public void setOnXRecyclerViewItemClickListener(
            OnXRecyclerViewItemClickListener listener) {
        this.onXRecyclerViewItemClickListener = listener;
    }

    public void setXListViewListener(OnXListViewListener listener) {
        this.mListViewListener = listener;
    }

    public void setPullLoadEnable(boolean enable) {
        super.setLoadMoreEnabled(enable);
    }

    public void setPullRefreshEnable(boolean enable) {
        super.setPullRefreshEnabled(enable);
    }

    public void refresh() {
        super.setScrolled(false);
        super.setLoadMoreEnabled(false);
        if (mListViewListener != null) {
            mListViewListener.onRefresh();
        }
    }

    public void initRL() {
        this.pullRefreshComplete();
        this.loadMoreComplete();
    }
}
