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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.cloud.core.enums.DateFormatEnum;
import com.cloud.core.utils.DateUtils;
import com.cloud.core.utils.GlobalUtils;

import com.cloud.resources.R;
import com.cloud.resources.glnavigation.PinnedSectionListView;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016-1-19 下午10:23:02
 * @Description: 分组列表+刷新与加载更多
 * @Modifier:
 * @ModifyContent:
 */
public class PinnedSectionRefreshListView extends PinnedSectionListView
        implements OnItemClickListener {

    private Scroller mScroller; // used for scroll back

    private OnXListViewListener mListViewListener = null;

    private int dividerHeight = 0;

    // -- header view
    private XListViewHeader mHeaderView;

    // header view content, use it to calculate the Header's height. And hide it
    // when disable pull refresh.
    private LinearLayout mHeaderViewContent;
    private TextView mHeaderTimeView;

    // header view's height
    private int mHeaderViewHeight;

    // -- footer view
    private XListViewFooter mFooterView;
    private int mAutoScrollBackHeight;

    private boolean mIsFooterReady = false;

    private boolean mEnablePullRefresh = true;

    private boolean mEnablePullLoad = false;

    private boolean mPullLoading = false;

    // is refreashing.
    private boolean mPullRefreshing = false;

    // for mScroller, scroll back from header or footer.
    private int mScrollBack = 0;
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

    // user's scroll listener
    private AbsListView.OnScrollListener mScrollListener;

    // save event y
    private float mLastY = -1;

    private MotionEvent lastMotionEvent;

    /* 记录最后滚动位置 */
    private int lastPosition = 0;
    private int scrollTop = 0;

    // total list items, used to detect is at the bottom of listview.
    private int mTotalItemCount;

    private IXListViewOnTouchEvent mListViewOnTouchEvent = null;

    private OnXListViewScrollListener onXListViewScrollListener = null;

    private OnXListViewItemClickListener onXListViewItemClickListener = null;

    public PinnedSectionRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public PinnedSectionRefreshListView(Context context, AttributeSet attrs,
                                        int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs);
    }

    /**
     * @param onXListViewScrollListener
     */
    public void setOnXListViewScrollListener(OnXListViewScrollListener listener) {
        this.onXListViewScrollListener = listener;
    }

    /**
     * @param onXListViewItemClickListener
     */
    public void setOnXListViewItemClickListener(
            OnXListViewItemClickListener listener) {
        this.onXListViewItemClickListener = listener;
    }

    private void initView(Context context, AttributeSet attrs) {
        mScroller = new Scroller(context, new DecelerateInterpolator());
        super.setOnScrollListener(this);
        this.setOnItemClickListener(this);
        if (attrs != null) {
            int[] attrarray = new int[]{android.R.attr.dividerHeight};
            TypedArray alst = context.obtainStyledAttributes(attrs, attrarray);
            dividerHeight = alst.getDimensionPixelSize(0, 1);
            alst.recycle();
        }
        this.setDividerHeight(0);
        RelativeLayout.LayoutParams mainparam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setLayoutParams(mainparam);
        // init header view
        mHeaderView = new XListViewHeader(context);

        mHeaderViewContent = (LinearLayout) mHeaderView
                .findViewById(R.id.xlistview_header_content);
        mHeaderTimeView = (TextView) mHeaderView
                .findViewById(R.id.xlistview_header_time);
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
        addHeaderView(mHeaderView);
        // init footer view
        mFooterView = new XListViewFooter(context);
        mAutoScrollBackHeight = GlobalUtils.getScreenHeight(context) / 3;
    }

    public void setXListViewListener(OnXListViewListener listener) {
        mListViewListener = listener;
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
            mFooterView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startLoadMore();
                }
            });
        }
    }

    public void setAutoPullDown(boolean auto) {
        if (auto && mScroller != null) {
            if (mScrollBack != SCROLL_HEADER_AUTO) {
                mHeaderView.setVisiableHeight(mHeaderViewHeight);
                this.setDividerHeight(0);
                mFooterView.setVisibility(View.INVISIBLE);
                setRefreshTime(DateUtils.getDateTime(DateFormatEnum.YYYYMMDDHHMMSS));
                mScrollBack = SCROLL_HEADER_AUTO;
                mScroller.startScroll(0, 0, 0, mAutoScrollBackHeight,
                        AUTO_SCROLL_DURATION);
                dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0,
                        0));
                invalidate();
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

    @Override
    public void setOnScrollListener(AbsListView.OnScrollListener l) {
        mScrollListener = l;
        super.setOnScrollListener(l);
    }

    private void invokeOnScrolling() {
        if (mScrollListener instanceof OnXScrollListener) {
            OnXScrollListener l = (OnXScrollListener) mScrollListener;
            l.onXScrolling(this);
        }
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

    private boolean isDraggingHorizontally(MotionEvent event) {
        if (lastMotionEvent == null || event == null)
            return false;
        float deltaX = Math.abs(event.getX() - lastMotionEvent.getX());
        float deltaY = Math.abs(event.getY() - lastMotionEvent.getY());
        return deltaX > deltaY;
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
                mLastY = -1;
                if (getFirstVisiblePosition() == 0) {
                    if (mEnablePullRefresh
                            && mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                        mPullRefreshing = true;
                        mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
                        if (mListViewListener != null) {
                            mListViewListener.onRefresh();
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
                break;
        }
        saveLastMotionEvent(ev);
        return super.onTouchEvent(ev);
    }

    private void saveLastMotionEvent(MotionEvent event) {
        lastMotionEvent = MotionEvent.obtain(event);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (mScrollBack == SCROLLBACK_HEADER) {
                mHeaderView.setVisiableHeight(Math.abs(mScroller.getCurrY()));
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
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        if (onXListViewItemClickListener != null) {
            onXListViewItemClickListener
                    .onItemClick(parent, view, position, id);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        super.onScrollStateChanged(view, scrollState);
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            if (getChildAt(0) != null)
                scrollTop = (getChildAt(0) == null) ? 0 : getChildAt(0).getTop();
        }
        if (mScrollListener != null) {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        mTotalItemCount = totalItemCount;
        if (mScrollListener != null) {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
                    totalItemCount);
        }
        if (onXListViewScrollListener != null) {
            onXListViewScrollListener.onScrollPosition(firstVisibleItem);
            onXListViewScrollListener.onLastVisibleItem(firstVisibleItem + visibleItemCount);
        }
    }

    /**
     * you can listen ListView.OnScrollListener or this one. it will invoke
     * onXScrolling when header/footer scroll back.
     */
    public interface OnXScrollListener extends AbsListView.OnScrollListener {
        public void onXScrolling(View view);
    }

    /**
     * @Function:本地方法，滚动到指定位置
     */
    public void setSelectionFromTop() {
        this.setSelectionFromTop(lastPosition, scrollTop);
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
