package com.cloud.resources.xlistview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.cloud.core.enums.DateFormatEnum;
import com.cloud.core.utils.DateUtils;

import com.cloud.resources.CusScrollView;
import com.cloud.resources.R;

public class XRefreshEmptyView extends CusScrollView {

    // the interface to trigger refresh and load more.
    private OnXListViewListener mScrollViewListener;
    private XRefreshEmptyViewListener mXRefreshScrollViewListener = null;

    // the custom header view
    private XListViewHeader mHeaderView;
    private LinearLayout mHeaderViewContent;
    private TextView mHeaderTimeView;

    private int mHeaderViewHeight;

    private MotionEvent lastMotionEvent;

    private boolean mEnablePullRefresh = true;

    private boolean mPullRefreshing = false;

    private final static int SCROLL_DURATION = 400; // scroll back duration
    private final static float OFFSET_RADIO = 1.8f;

    private Scroller mScroller;

    private float mLastY = -1;

    private LinearLayout mLayoutll;

    public XRefreshEmptyView(Context context) {
        super(context);
        initView(context);
    }

    public XRefreshEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public XRefreshEmptyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    /**
     * @param 设置mXRefreshScrollViewListener
     */
    public void setXRefreshScrollViewListener(
            XRefreshEmptyViewListener mXRefreshScrollViewListener) {
        this.mXRefreshScrollViewListener = mXRefreshScrollViewListener;
    }

    private void initView(Context context) {
        mScroller = new Scroller(context, new DecelerateInterpolator());
        setScroller(mScroller);
        RelativeLayout.LayoutParams mainparam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        this.setLayoutParams(mainparam);
        this.setScrollBarStyle(GONE);

        LinearLayout.LayoutParams layoutparam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        mLayoutll = new LinearLayout(context);
        mLayoutll.setLayoutParams(layoutparam);
        mLayoutll.setOrientation(LinearLayout.VERTICAL);

        mHeaderView = new XListViewHeader(context);
        mHeaderViewContent = (LinearLayout) mHeaderView
                .findViewById(R.id.xlistview_header_content);
        mHeaderTimeView = (TextView) mHeaderView
                .findViewById(R.id.xlistview_header_time);
        mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mHeaderViewHeight = mHeaderViewContent.getHeight();
                        getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                    }
                });
        mLayoutll.addView(mHeaderView);
        this.addView(mLayoutll);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
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
                    if (getScrollY() <= 0) {
                        if (mHeaderView.getVisiableHeight() > 0 || deltaY > 0) {
                            updateHeaderHeight(deltaY / OFFSET_RADIO);
                        }
                    } else if (mHeaderView.getState() == XListViewHeader.STATE_READY) {
                        this.setScrollY(0);
                        updateHeaderHeight(deltaY / OFFSET_RADIO);
                    }
                }
                break;
            default:
                if (mEnablePullRefresh
                        && mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                    mPullRefreshing = true;
                    mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
                    // 用XRefreshView时XRefreshListView和XRefreshScrollView监听事件用到mScrollViewListener
                    if (mScrollViewListener != null) {
                        mScrollViewListener.onRefresh();
                    }
                    // 单独使用XRefreshScrollView控件可用mXRefreshScrollViewListener监听
                    if (mXRefreshScrollViewListener != null) {
                        mXRefreshScrollViewListener.onRefresh();
                    }
                }
                resetHeaderHeight();
                break;
        }
        saveLastMotionEvent(ev);
        return super.onTouchEvent(ev);
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

    private void updateHeaderHeight(float delta) {
        handler.sendMessage(handler.obtainMessage());
        mHeaderView.setVisiableHeight((int) delta
                + mHeaderView.getVisiableHeight());
        if (mEnablePullRefresh && !mPullRefreshing) {
            if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                mHeaderView.setState(XListViewHeader.STATE_READY);
            } else {
                mHeaderView.setState(XListViewHeader.STATE_NORMAL);
            }
        }
    }

    /**
     * stop refresh, reset header view.
     */
    public void stopRefresh() {
        if (mPullRefreshing == true) {
            mPullRefreshing = false;
            resetHeaderHeight();
        }
    }

    /**
     * reset header view's height.
     */
    private void resetHeaderHeight() {
        int height = mHeaderView.getVisiableHeight();
        if (height == 0)
            return;
        if (mPullRefreshing && height <= mHeaderViewHeight) {
            return;
        }
        int finalHeight = 0;
        if (mPullRefreshing && height > mHeaderViewHeight) {
            finalHeight = mHeaderViewHeight;
        }
        mScroller.startScroll(0, height, 0, finalHeight - height,
                SCROLL_DURATION);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mHeaderView.setVisiableHeight(mScroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }

    /**
     * enable or disable pull down refresh feature.
     *
     * @param enable
     */
    public void setPullRefreshEnable(boolean enable) {
        mEnablePullRefresh = enable;
        if (!mEnablePullRefresh) {
            mHeaderViewContent.setVisibility(View.INVISIBLE);
        } else {
            mHeaderViewContent.setVisibility(View.VISIBLE);
        }
    }

    /**
     * add content view
     */
    public void addContentView(View contentView) {
        mLayoutll.addView(contentView);
    }

    /**
     * if add scroll content view in activity need invalide scrollview
     */
    public XRefreshEmptyView invalideView() {
        invalidate();
        return this;
    }

    /**
     * set last refresh time
     *
     * @param time
     */
    public void setRefreshTime(String time) {
        mHeaderTimeView.setText(time);
    }

    public void setXRefreshListener(OnXListViewListener l) {
        mScrollViewListener = l;
    }

    public void initRL() {
        stopRefresh();
        setRefreshTime(DateUtils.getDateTime(DateFormatEnum.YYYYMMDDHHMMSS));
    }

    /**
     * 通知更新图片
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mHeaderView.setPullRefreshAnim(mHeaderView.getVisiableHeight());
        }
    };
}
