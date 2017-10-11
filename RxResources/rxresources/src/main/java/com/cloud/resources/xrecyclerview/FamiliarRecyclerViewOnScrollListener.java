package com.cloud.resources.xrecyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/10
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public abstract class FamiliarRecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mGridLayoutManager;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private int mLayoutManagerType = 1; // 1 LinearLayoutManager, 2 GridLayoutManager, 3 StaggeredGridLayoutManager
    private boolean isCanScrolledCallback = false;
    private int[] mStaggeredFirstPositions, mStaggeredLastPositions;
    private int surplusDataNumber = 0;

    private int callbackType = 0; // 0 not callback, 1 scrolled to top, 2 scrolled to bottom

    public FamiliarRecyclerViewOnScrollListener(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager.getClass().isAssignableFrom(LinearLayoutManager.class)) {
            mLayoutManagerType = 1;
            mLinearLayoutManager = (LinearLayoutManager) layoutManager;
        } else if (layoutManager.getClass().isAssignableFrom(GridLayoutManager.class)) {
            mLayoutManagerType = 2;
            mGridLayoutManager = (GridLayoutManager) layoutManager;
        } else if (layoutManager.getClass().isAssignableFrom(StaggeredGridLayoutManager.class)) {
            mLayoutManagerType = 3;
            mStaggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            mStaggeredLastPositions = new int[mStaggeredGridLayoutManager.getSpanCount()];
            mStaggeredFirstPositions = new int[mStaggeredGridLayoutManager.getSpanCount()];
        }
    }

    @Override
    public final void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (!isCanScrolledCallback) return;
        if (checkToTop()) {
            callbackType = 1;
            if (!isIdleCallBack()) {
                onScrolledToTop();
            }
        } else if (checkToBottom(recyclerView)) {
            callbackType = 2;
            if (!isIdleCallBack()) {
                onScrolledToBottom();
            }
        } else {
            callbackType = 0;
            onScrollToMoving();
        }
    }

    @Override
    public final void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        switch (newState) {
            case RecyclerView.SCROLL_STATE_IDLE:
                isCanScrolledCallback = false;
                if (isIdleCallBack()) {
                    if (callbackType == 1) {
                        onScrolledToTop();
                    } else if (callbackType == 2) {
                        onScrolledToBottom();
                    }
                }
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:
                isCanScrolledCallback = true;
                break;
        }
    }

    private boolean checkToTop() {
        switch (mLayoutManagerType) {
            case 1:
                return mLinearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0;
            case 2:
                return mGridLayoutManager.findFirstCompletelyVisibleItemPosition() == 0;
            case 3:
                mStaggeredGridLayoutManager.findFirstCompletelyVisibleItemPositions(mStaggeredFirstPositions);
                return mStaggeredFirstPositions[0] == 0;
        }

        return false;
    }

    public void setSurplusDataNumberEnableLoadMore(int surplusDataNumber) {
        if (surplusDataNumber < 0) {
            surplusDataNumber = 0;
        }
        this.surplusDataNumber = surplusDataNumber;
    }

    private boolean checkToBottom(RecyclerView recyclerView) {
        switch (mLayoutManagerType) {
            case 1: {
                int currPosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                int lastPosition = recyclerView.getAdapter().getItemCount() - 1;
                return (currPosition + surplusDataNumber) >= lastPosition;
            }
            case 2: {
                int currPosition = mGridLayoutManager.findLastCompletelyVisibleItemPosition();
                int lastPosition = recyclerView.getAdapter().getItemCount() - 1;
                return (currPosition + surplusDataNumber) >= lastPosition;
            }
            case 3: {
                mStaggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(mStaggeredLastPositions);
                if (mStaggeredLastPositions.length > 0) {
                    int totalCount = recyclerView.getAdapter().getItemCount() - 1;
                    for (int curPos : mStaggeredLastPositions) {
                        if ((curPos + surplusDataNumber) >= totalCount) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public abstract void onScrolledToTop();

    public abstract void onScrolledToBottom();

    public abstract void onScrollToMoving();

    protected boolean isIdleCallBack() {
        return true;
    }
}
