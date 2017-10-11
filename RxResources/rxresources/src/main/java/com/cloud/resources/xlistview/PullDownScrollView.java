package com.cloud.resources.xlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.cloud.core.logger.Logger;

public class PullDownScrollView extends ScrollView {

    private ViewGroup mHeaderView;
    private View mImageView;
    private int mHeaderHeight = 0;
    private int mHeaderWidth = 0;
    private float mLastY;
    private MotionEvent lastMotionEvent;
    private final static float OFFSET_RADIO = 1.8f;
    private OnPullDownScrollListener onPullDownScrollListener = null;

    public PullDownScrollView(Context context) {
        super(context);
    }

    public PullDownScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullDownScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnPullDownScrollListener(OnPullDownScrollListener listener) {
        this.onPullDownScrollListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            if (mLastY == -1) {
                mLastY = ev.getRawY();
            }
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mLastY = ev.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    final float deltaY = ev.getRawY() - mLastY;
                    mLastY = ev.getRawY();
                    if (!isDraggingHorizontally(ev)) {
                        if (getScrollY() <= 0 && deltaY > 0) {
                            updateHeaderHeight(deltaY / OFFSET_RADIO);
                        }
                    }
                    break;
                default:
                    resetHeaderHeight();
                    mLastY = -1;
                    break;
            }
            saveLastMotionEvent(ev);
        } catch (Exception e) {
            Logger.L.error("scroll view touch event process error:", e);
        }
        return super.onTouchEvent(ev);
    }

    private void updateHeaderHeight(float delta) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mHeaderView
                .getLayoutParams();
        int sheight = (int) (delta + mHeaderView.getLayoutParams().height);
        if (sheight < mHeaderHeight) {
            sheight = mHeaderHeight;
        }
        lp.height = sheight;
        lp.width = mHeaderWidth;
        mHeaderView.setLayoutParams(lp);
        mImageView.setLayoutParams(lp);
        mImageView.requestLayout();
    }

    private void resetHeaderHeight() {
        int height = mHeaderView.getLayoutParams().height;
        if (height <= mHeaderHeight) {
            return;
        }
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mHeaderView
                .getLayoutParams();
        lp.width = mHeaderWidth;
        lp.height = mHeaderHeight;
        mHeaderView.setLayoutParams(lp);
        mImageView.setLayoutParams(lp);
        mImageView.requestLayout();
    }

    private void saveLastMotionEvent(MotionEvent event) {
        lastMotionEvent = MotionEvent.obtain(event);
    }

    private boolean isDraggingHorizontally(MotionEvent event) {
        if (lastMotionEvent == null || event == null)
            return false;
        float deltaX = Math.abs(event.getX() - lastMotionEvent.getX());
        float deltaY = Math.abs(event.getY() - lastMotionEvent.getY());
        return deltaX > deltaY;
    }

    public void setHeaderView(ViewGroup view, int imgWidth, int imgHeight) {
        this.mHeaderView = view;
        this.mHeaderWidth = imgWidth;
        this.mHeaderHeight = imgHeight;
        if (mHeaderView.getChildCount() > 0) {
            this.mImageView = mHeaderView.getChildAt(0);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mHeaderView != null) {
            mHeaderView.setY(t / 2);
        }
        if (onPullDownScrollListener != null) {
            onPullDownScrollListener.onScrollChanged(l, t, oldl, oldt);
        }
    }

}
