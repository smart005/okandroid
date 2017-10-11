package com.cloud.resources.xlistview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.cloud.core.enums.DateFormatEnum;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.DateUtils;
import com.cloud.core.utils.GlobalUtils;

import com.cloud.resources.R;

/**
 * @Author LiJingHuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-6-4 下午5:45:50
 * @Modifier:
 * @ModifyContent:
 */
public class XRefreshListView extends ListView implements OnScrollListener,
        OnItemClickListener {
    private float mLastY = -1; // save event y
    private Scroller mScroller; // used for scroll back
    private OnScrollListener mScrollListener; // user's scroll listener

    // the interface to trigger refresh and load more.
    private OnXListViewListener mListViewListener;

    // -- header view
    private XListViewHeader mHeaderView;
    // header view content, use it to calculate the Header's height. And hide it
    // when disable pull refresh.
    private LinearLayout mHeaderViewContent;
    private TextView mHeaderTimeView;
    private int mHeaderViewHeight; // header view's height
    private boolean mEnablePullRefresh = true;
    private boolean mPullRefreshing = false; // is refreashing.
    private int mAutoScrollBackHeight;

    // -- footer view
    private XListViewFooter mFooterView;
    private boolean mEnablePullLoad;
    private boolean mPullLoading;
    private boolean mIsFooterReady = false;

    // -----slide view
    private boolean mEnableSliding = false;

    // total list items, used to detect is at the bottom of listview.
    private int mTotalItemCount;

    // for mScroller, scroll back from header or footer.
    private int mScrollBack;
    private final static int SCROLLBACK_HEADER = 0;
    private final static int SCROLLBACK_FOOTER = 1;
    private final static int SCROLL_HEADER_AUTO = 2;

    private final static int AUTO_SCROLL_DURATION = 1200;
    private final static int SCROLL_DURATION = 600; // scroll back duration
    private final static int PULL_LOAD_MORE_DELTA = 50; // when pull up >= 50px
    // at bottom, trigger
    // load more.
    private final static float OFFSET_RADIO = 1.8f; // support iOS like pull
    // feature.
    private MotionEvent lastMotionEvent;
    private SlidingSlideView mFocusedItemView;

    private IXListViewOnTouchEvent mListViewOnTouchEvent = null;

    private int dividerHeight = 0;

    private OnXListViewItemClickListener onXListViewItemClickListener = null;

    private OnXListViewScrollListener onXListViewScrollListener = null;

    private final int UPDATE_HEADER_HEIGHT_WHAT = 245034804;

    /**
     * 初始头部高度
     */
    private int rawHeaderHeight = 0;

    private OnXListViewInitFinishedListener mOnXListViewInitFinishedListener = null;

    private boolean isAutoPull = false;

    private OnXListViewOverScrolledListener onXListViewOverScrolledListener = null;

    private boolean isReMeasureSize = false;

    /**
     * @param context
     */
    public XRefreshListView(Context context) {
        super(context);
        initWithContext(context, null);
    }

    public XRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithContext(context, attrs);
    }

    public XRefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initWithContext(context, attrs);
    }

    public void setReMeasureSize(boolean isReMeasureSize) {
        this.isReMeasureSize = isReMeasureSize;
        if (isReMeasureSize) {
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            this.onMeasure(this.getMeasuredWidth(), expandSpec);
        }
    }

    /**
     * @param onXListViewItemClickListener
     */
    public void setOnXListViewItemClickListener(
            OnXListViewItemClickListener listener) {
        this.onXListViewItemClickListener = listener;
    }

    /**
     * @param listener
     */
    public void setOnXListViewScrollListener(OnXListViewScrollListener listener) {
        this.onXListViewScrollListener = listener;
    }

    /**
     * 设置列表初始化结束监听
     *
     * @param listener
     */
    public void setOnXListViewInitFinishedListener(OnXListViewInitFinishedListener listener) {
        this.mOnXListViewInitFinishedListener = listener;
    }

    public void setOnXListViewOverScrolledListener(OnXListViewOverScrolledListener listener) {
        this.onXListViewOverScrolledListener = listener;
    }

    private void initWithContext(Context context, AttributeSet attrs) {
        mScroller = new Scroller(context, new DecelerateInterpolator());
        // XListView need the scroll event, and it will dispatch the event to
        // user's listener (as a proxy).
        super.setOnScrollListener(this);

        RelativeLayout.LayoutParams mainparam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        this.setLayoutParams(mainparam);

        // init header view
        mHeaderView = new XListViewHeader(context);

        mHeaderView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        rawHeaderHeight = mHeaderView.getMeasuredHeight();

        mHeaderViewContent = (LinearLayout) mHeaderView
                .findViewById(R.id.xlistview_header_content);
        mHeaderTimeView = (TextView) mHeaderView
                .findViewById(R.id.xlistview_header_time);
        addHeaderView(mHeaderView);

        // init footer view
        mFooterView = new XListViewFooter(context);

        // init header height
        mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mHeaderViewHeight = mHeaderViewContent.getHeight();
                        getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                    }
                });
        mAutoScrollBackHeight = GlobalUtils.getScreenHeight(context) / 3;
        this.setOnItemClickListener(this);
        if (attrs != null) {
            int[] attrarray = new int[]{android.R.attr.dividerHeight};
            TypedArray alst = context.obtainStyledAttributes(attrs, attrarray);
            dividerHeight = alst.getDimensionPixelSize(0, 1);
            alst.recycle();
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XRefreshListView);
            isReMeasureSize = ta.getBoolean(R.styleable.XRefreshListView_reMeasureSize, false);
            ta.recycle();
        }
        this.setDividerHeight(0);
        this.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        // make sure XListViewFooter is the last footer view, and only add once.
        if (mIsFooterReady == false) {
            mIsFooterReady = true;
            addFooterView(mFooterView);
        }
        super.setAdapter(adapter);
    }

    /**
     * enable or disable pull down refresh feature.
     *
     * @param enable
     */
    public void setPullRefreshEnable(boolean enable) {
        mEnablePullRefresh = enable;
        if (!mEnablePullRefresh) { // disable, hide the content
            mHeaderViewContent.setVisibility(View.INVISIBLE);
        } else {
            mHeaderViewContent.setVisibility(View.VISIBLE);
        }
    }

    /**
     * enable or disable pull up load more feature.
     *
     * @param enable
     */
    public void setPullLoadEnable(boolean enable) {
        mEnablePullLoad = enable;
        if (!mEnablePullLoad) {
            mFooterView.hide();
            mFooterView.setOnClickListener(null);
        } else {
            mPullLoading = false;
            mFooterView.show();
            mFooterView.setState(XListViewFooter.STATE_NORMAL);
            // both "pull up" and "click" will invoke load more.
            mFooterView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startLoadMore();
                }
            });
        }
    }

    public void setPullLoadVisible(boolean visible) {
        if (!visible) {
            mFooterView.setState(XListViewFooter.STATE_INIT);
        }
    }

    public void setAutoPullDown(boolean auto) {
        if (auto && mScroller != null) {
            this.isAutoPull = true;
            this.setDividerHeight(0);
            mFooterView.setVisibility(View.INVISIBLE);
            setRefreshTime(DateUtils.getDateTime(DateFormatEnum.YYYYMMDDHHMMSS));
            mScrollBack = SCROLL_HEADER_AUTO;
            mHeaderView.setVisiableHeight(0);
            mScroller.startScroll(0, 0, 0, mAutoScrollBackHeight,
                    AUTO_SCROLL_DURATION);
            dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                    SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0,
                    0));
            invalidate();
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
     * stop load more, reset footer view.
     */
    public void stopLoadMore() {
        if (mPullLoading == true) {
            mPullLoading = false;
            mFooterView.setState(XListViewFooter.STATE_NORMAL);
        }
    }

    /**
     * set last refresh time
     *
     * @param time
     */
    public void setRefreshTime(String time) {
        mHeaderTimeView.setText(time);
    }

    private void invokeOnScrolling() {
        if (mScrollListener instanceof OnXScrollListener) {
            OnXScrollListener l = (OnXScrollListener) mScrollListener;
            l.onXScrolling(this);
        }
    }

    private void updateHeaderHeight(float delta) {
        handler.obtainMessage(UPDATE_HEADER_HEIGHT_WHAT).sendToTarget();
        mHeaderView.setVisiableHeight((int) delta
                + mHeaderView.getVisiableHeight());
        if (mEnablePullRefresh && !mPullRefreshing) {
            if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                mHeaderView.setState(XListViewHeader.STATE_READY);
            } else {
                mHeaderView.setState(XListViewHeader.STATE_NORMAL);
            }
        }
        setSelection(0);
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
        mScrollBack = SCROLLBACK_HEADER;
        mScroller.startScroll(0, height, 0, finalHeight - height,
                SCROLL_DURATION);
        invalidate();
    }

    private void updateFooterHeight(float delta) {
        int height = mFooterView.getBottomMargin() + (int) delta;
        if (mEnablePullLoad && !mPullLoading) {
            if (height > PULL_LOAD_MORE_DELTA) {
                mFooterView.setState(XListViewFooter.STATE_READY);
            } else {
                mFooterView.setState(XListViewFooter.STATE_NORMAL);
            }
        }
        mFooterView.setBottomMargin(height);
    }

    private void resetFooterHeight() {
        int bottomMargin = mFooterView.getBottomMargin();
        if (bottomMargin > 0) {
            mScrollBack = SCROLLBACK_FOOTER;
            mScroller.startScroll(0, bottomMargin, 0, -bottomMargin,
                    SCROLL_DURATION);
            invalidate();
        }
    }

    private void startLoadMore() {
        mPullLoading = true;
        mFooterView.setState(XListViewFooter.STATE_LOADING);
        if (mListViewListener != null) {
            mListViewListener.onLoadMore();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mEnableSliding) {// 横向滑动删除记录
                    int x = (int) ev.getX();
                    int y = (int) ev.getY();
                    int position = pointToPosition(x, y);
                    if (position != INVALID_POSITION) {
                        BaseDataItem data = (BaseDataItem) getItemAtPosition(position);
                        try {
                            mFocusedItemView = data.getSlideView();
                        } catch (Exception e) {
                            mFocusedItemView = null;
                        }
                    }
                }
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                onActionMoveScrollWard(deltaY);
                mLastY = ev.getRawY();
                if (!isDraggingHorizontally(ev)) {
                    if (getFirstVisiblePosition() == 0
                            && (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
                        updateHeaderHeight(deltaY / OFFSET_RADIO);
                        invokeOnScrolling();
                    } else if (getLastVisiblePosition() == mTotalItemCount - 1
                            && (mFooterView.getBottomMargin() > 0 || deltaY < 0)) {
                        updateFooterHeight(-deltaY / OFFSET_RADIO);
                    }
                }
                break;
            default:
                try {
                    mLastY = -1;
                    if (getFirstVisiblePosition() == 0) {
                        if (mEnablePullRefresh
                                && mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                            mPullRefreshing = true;
                            mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
                            if (mListViewListener != null) {
                                mListViewListener.onRefresh();
                            }
                            if (isAutoPull && mOnXListViewInitFinishedListener != null) {
                                mOnXListViewInitFinishedListener.onInitFinished();
                            }
                        }
                        resetHeaderHeight();
                    }
                    if (getLastVisiblePosition() == mTotalItemCount - 1) {
                        if (mEnablePullLoad
                                && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA) {
                            startLoadMore();
                        }
                        resetFooterHeight();
                    }
                    isAutoPull = false;
                } catch (Exception e) {
                    Logger.L.error("xrefreshlist error:", e);
                }
                break;
        }
        if (mEnableSliding) {
            if (mFocusedItemView != null) {
                mFocusedItemView.onRequireTouchEvent(ev);
            }
        }
        saveLastMotionEvent(ev);
        return super.onTouchEvent(ev);
    }

    private void saveLastMotionEvent(MotionEvent event) {
        lastMotionEvent = MotionEvent.obtain(event);
    }

    /**
     * 刷新列表
     */
    public void refresh() {
        if (mListViewListener != null) {
            mListViewListener.onRefresh();
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (mScrollBack == SCROLLBACK_HEADER) {
                mHeaderView.setVisiableHeight(mScroller.getCurrY());
            } else if (mScrollBack == SCROLLBACK_FOOTER) {
                mFooterView.setBottomMargin(mScroller.getCurrY());
            } else if (mScrollBack == SCROLL_HEADER_AUTO) {
                dispatchTouchEvent(MotionEvent.obtain(
                        SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_MOVE, 0, mScroller.getCurrY(), 0));
                if (mScroller.getCurrY() >= mAutoScrollBackHeight) {
                    dispatchTouchEvent(MotionEvent.obtain(
                            SystemClock.uptimeMillis(),
                            SystemClock.uptimeMillis(), MotionEvent.ACTION_UP,
                            0, mScroller.getCurrY(), 0));
                }
            }
            postInvalidate();
            invokeOnScrolling();
        }
        super.computeScroll();
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mScrollListener = l;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
            // lastPosition = getFirstVisiblePosition();
            // if (getChildAt(0) != null)
            // scrollTop = (getChildAt(0) == null) ? 0 : getChildAt(0)
            // .getTop();
        }
        if (mScrollListener != null) {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        mTotalItemCount = totalItemCount;
        if (mScrollListener != null) {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
        if (onXListViewScrollListener != null) {
            onXListViewScrollListener.onScrollPosition(firstVisibleItem);
            onXListViewScrollListener.onLastVisibleItem(firstVisibleItem + visibleItemCount);
        }
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (onXListViewOverScrolledListener != null) {
            onXListViewOverScrolledListener.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        }
    }

    public void setXListViewListener(OnXListViewListener l) {
        mListViewListener = l;
    }

    /**
     * you can listen ListView.OnScrollListener or this one. it will invoke
     * onXScrolling when header/footer scroll back.
     */
    public interface OnXScrollListener extends OnScrollListener {
        public void onXScrolling(View view);
    }

    private boolean isDraggingHorizontally(MotionEvent event) {
        if (lastMotionEvent == null || event == null)
            return false;
        float deltaX = Math.abs(event.getX() - lastMotionEvent.getX());
        float deltaY = Math.abs(event.getY() - lastMotionEvent.getY());
        return deltaX > deltaY;
    }

    @SuppressWarnings("unused")
    private boolean isDraggingVertically(MotionEvent event) {
        float deltaY = 0.0f;
        if (lastMotionEvent != null) {
            deltaY = Math.abs(event.getY() - lastMotionEvent.getY());
        }
        return deltaY > 20;
    }

    /**
     * @Function设置是否滑动删除
     */
    public void setEnableSliding(boolean mEnableSliding) {
        this.mEnableSliding = mEnableSliding;
    }

    public void shrinkListItem(int position) {
        View item = getChildAt(position);
        if (item != null) {
            try {
                ((SlidingSlideView) item).shrink();
            } catch (ClassCastException e) {
                // ClassCastException
            }
        }
    }

    /**
     * @Function:滚动到顶部
     */
    public void setSelectionFromTop() {
        this.setSelection(0);
    }

    /**
     * @Function:设置滚动方向监听
     */
    public void setXListViewOnTouchEvent(IXListViewOnTouchEvent l) {
        mListViewOnTouchEvent = l;
    }

    /**
     * @Function:滚动方向监听回调调用
     */
    private void onActionMoveScrollWard(float deltaY) {
        if (deltaY > 0) {// mListViewOnTouchEvent
            if (mListViewOnTouchEvent != null)
                mListViewOnTouchEvent.onScrollUpWard(Math.abs(deltaY));
        } else {
            if (mListViewOnTouchEvent != null)
                mListViewOnTouchEvent.onScrollDownWard(Math.abs(deltaY));
        }
    }

    /**
     * @Function:判断滚动方向监听
     */
    public interface IXListViewOnTouchEvent {
        public void onScrollUpWard(float value);

        public void onScrollDownWard(float value);
    }

    /**
     * 刷新、加载初始
     */
    public void initRL() {
        mFooterView.setVisibility(View.VISIBLE);
        stopRefresh();
        stopLoadMore();
        setRefreshTime(DateUtils.getDateTime(DateFormatEnum.YYYYMMDDHHMMSS));
        this.setDividerHeight(dividerHeight);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        if (onXListViewItemClickListener != null) {
            onXListViewItemClickListener
                    .onItemClick(parent, view, position, id);
        }
    }

    /**
     * 通知更新图片
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_HEADER_HEIGHT_WHAT:
                    mHeaderView.setPullRefreshAnim(mHeaderView.getVisiableHeight());
                    break;
            }
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isReMeasureSize) {
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
