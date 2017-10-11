package com.cloud.resources;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-7-3 下午2:00:31
 * @Description:http://my.oschina.net/fengheju/blog/196608
 * @Modifier:
 * @ModifyContent:
 */
public class CusScrollView extends ScrollView {

    private Scroller mScroller = null;
    private int mPointerId = 0;
    private float y = 0;
    private float mTouchSlop = 0;
    private GestureDetector mGestureDetector;
    private boolean isTnterceptTouchByCustom = false;
    private boolean isClickDealwith = false;
    private float deltaY = 0;
    private Callbacks mCallbacks;

    public CusScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CusScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CusScrollView(Context context) {
        super(context);
        init();
    }

    /**
     * @param 设置isTnterceptTouchByCustom
     */
    public void setTnterceptTouchByCustom(boolean isTnterceptTouchByCustom) {
        this.isTnterceptTouchByCustom = isTnterceptTouchByCustom;
    }

    private void init() {
        mGestureDetector = new GestureDetector(getContext(),
                new YScrollDetector());
        setFadingEdgeLength(0);
    }

    protected void setScroller(Scroller mScroller) {
        this.mScroller = mScroller;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isTnterceptTouchByCustom) {
            boolean isIntercept = false;
            isClickDealwith = false;
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // 如果动画还未结束，则将此事件交给onTouchEvet()处理，
                    // 否则，先分发给子View
                    if (mScroller != null) {
                        isIntercept = !mScroller.isFinished();
                        if (mScroller.isFinished()) {
                            isClickDealwith = true;
                        }
                    }
                    // 如果此时不拦截ACTION_DOWN时间，应该记录下触摸地址及手指id，当我们决定拦截ACTION_MOVE的event时，
                    // 将会需要这些初始信息（因为我们的onTouchEvent将可能接收不到ACTION_DOWN事件）
                    mPointerId = ev.getPointerId(0);
                    y = ev.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int pointerIndex = ev.findPointerIndex(mPointerId);
                    float my = ev.getY(pointerIndex);
                    deltaY = Math.abs(y - my);
                    if (deltaY >= mTouchSlop) {
                        isIntercept = true;
                    }
                    // 如果不拦截的话，我们不会更新位置，这样可以通过累积小的移动距离来判断是否达到可以认为是Move的阈值。
                    // 这里当产生拦截的话，会更新位置（这样相当于损失了mTouchSlop的移动距离，如果不更新，可能会有一点点跳的感觉）
                    if (isIntercept) {
                        y = my;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    solvePointerUp(ev);
                    break;
            }
            if (isClickDealwith || deltaY < 20) {
                return false;
            } else {
                return isIntercept;
            }
        } else {
            return super.onInterceptTouchEvent(ev)
                    && mGestureDetector.onTouchEvent(ev);
        }
    }

    @Override
    public boolean onTouchEvent(android.view.MotionEvent ev) {
        if (mCallbacks != null) {
            switch (ev.getActionMasked()) {
                case android.view.MotionEvent.ACTION_DOWN:
                    mCallbacks.onDownMotionEvent();
                    break;
                case android.view.MotionEvent.ACTION_UP:
                case android.view.MotionEvent.ACTION_CANCEL:
                    mCallbacks.onUpOrCancelMotionEvent();
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    private void solvePointerUp(MotionEvent event) {
        // 获取离开屏幕的手指的索引
        int pointerIndexLeave = event.getActionIndex();
        if (event.getPointerCount() > pointerIndexLeave) {
            int pointerIdLeave = event.getPointerId(pointerIndexLeave);
            if (mPointerId == pointerIdLeave) {
                // 离开屏幕的正是目前的有效手指，此处需要重新调整，并且需要重置VelocityTracker
                int reIndex = (pointerIndexLeave == 0 && event
                        .getPointerCount() > 1) ? 1 : 0;
                mPointerId = event.getPointerId(reIndex);
                // 调整触摸位置，防止出现跳动
                y = event.getY(reIndex);
            }
        }
    }

    private class YScrollDetector extends SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            if (Math.abs(distanceY) >= Math.abs(distanceX)) {
                return true;
            }
            return false;
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mCallbacks != null) {
            mCallbacks.onScrollChanged(t);
        }
    }

    @Override
    public int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }

    public void setCallbacks(Callbacks listener) {
        mCallbacks = listener;
    }

    /**
     * Callback  is interface
     */
    public static interface Callbacks {
        public void onScrollChanged(int scrollY);

        public void onDownMotionEvent();

        public void onUpOrCancelMotionEvent();
    }
}
