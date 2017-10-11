package com.cloud.resources.widgets;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/5/18
 * @Description:rx view pager
 * @Modifier:
 * @ModifyContent:
 */
public class RxViewPager extends ViewGroup {

    private int page;//当前页的索引
    private GestureDetector detector;//手势探测
    private Context context;//控件创立的上下文
    private RxViewPagerScroller scroller;//缓慢页面切换帮助类，
    private boolean isFling;//用于判断
    private RxViewOnPageChangedListener listener;//页面变化监听事件
    private ListAdapter mAdapter;
    private boolean mAreAllItemsSelectable;
    private OnItemClickListener mOnItemClickListener;

    public RxViewPager(Context context) {
        this(context, null);
    }

    public RxViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        scroller = new RxViewPagerScroller();
        detector = new GestureDetector(context, new GestureDetector.OnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                    float distanceX, float distanceY) {
                scrollBy((int) distanceX, 0);
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2,
                                   float velocityX, float velocityY) {
                isFling = true;
                if (velocityX < 0 && page < getChildCount() - 1) {
                    page++;
                } else if (velocityX > 0 && page > 0) {
                    page--;
                }
                setCurrentItem(page);
                return false;
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).layout(i * getWidth(), 0, getWidth() + i * getWidth(), getHeight());
        }
    }

    private int firstPosition;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                firstPosition = (int) event.getX();
                break;
            case MotionEvent.ACTION_UP:
                if (!isFling) {
                    if (event.getX() - firstPosition > getWidth() / 2) {
                        page = page - 1;
                    } else if (firstPosition - event.getX() > getWidth() / 2) {
                        page = page + 1;
                    }
                    setCurrentItem(page);
                    break;
                }
                isFling = false;
        }
        return true;
    }

    /**
     * 使其移动到page相应的页面
     *
     * @param page
     */
    public void setCurrentItem(int position) {
        if (page <= 0) {
            page = 0;
        }
        if (page >= getChildCount() - 1) {
            page = getChildCount() - 1;
        }
        listener.onPageChanged(page);
        int distance = page * getWidth() - getScrollX();
        scroller.startScroll(getScrollX(), distance);
        invalidate();//刷新页面，重画，并且重新执行computeScroll()
    }

    //重复执行该方法，实现动画效果
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.isScrolling()) {
            scrollTo(scroller.getCurrentX(), 0);
            invalidate();
        }

    }

    /**
     * 设置监听事件方法
     *
     * @param listener
     */
    public void setPageChangedListener(RxViewOnPageChangedListener listener) {
        this.listener = listener;
    }

    /**
     * 监听事件接口，监听页面变化
     *
     * @author Charles
     */
    public interface RxViewOnPageChangedListener {
        public void onPageChanged(int page);
    }

    private class RxViewPagerScroller {
        public int distance;//动画运行距离
        public int startX;//动画开始位置
        public boolean isFinish;//判断动画是否完成
        public int startTime;//动画开始时间
        public int duration = 500;//动画持续时间
        public int currentX;//当前坐标

        public void startScroll(int startX, int distance) {
            this.distance = distance;
            this.startX = startX;
            startTime = (int) SystemClock.uptimeMillis();
            isFinish = false;
        }

        public boolean isScrolling() {
            if (isFinish) {
                return false;
            }

            int lastTime = (int) SystemClock.uptimeMillis() - startTime;
            if (lastTime <= duration) {
                currentX = startX + lastTime * distance / duration;//通过当前时间，判断当前位置

            } else {
                currentX = startX + distance;
                isFinish = true;
            }
            return true;
        }

        public int getCurrentX() {
            return currentX;
        }
    }

    private DataSetObserver mDataObserver = new DataSetObserver() {

        @Override
        public void onChanged() {
            setupChildren();
        }

        @Override
        public void onInvalidated() {
            setupChildren();
        }

    };

    public void setAdapter(ListAdapter adapter) {
        if (mAdapter != null) {
            mAdapter.unregisterDataSetObserver(mDataObserver);
        }
        mAdapter = adapter;
        if (mAdapter != null) {
            mAdapter.registerDataSetObserver(mDataObserver);
            mAreAllItemsSelectable = mAdapter.areAllItemsEnabled();
        }
        setupChildren();
    }

    private void setupChildren() {
        removeAllViews();
        if (mAdapter == null) {
            return;
        }
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View child = mAdapter.getView(i, null, this);
            if (mAreAllItemsSelectable || mAdapter.isEnabled(i)) {
                child.setOnClickListener(new InternalOnClickListener(i));
            }
            addViewInLayout(child, -1, child.getLayoutParams(), true);
        }
    }

    public interface OnItemClickListener {

        /**
         * Callback method to be invoked when an item in this LinearListView has
         * been clicked.
         * <p>
         * Implementers can call getItemAtPosition(position) if they need to
         * access the data associated with the selected item.
         *
         * @param parent   The RxViewPager where the click happened.
         * @param view     The view within the LinearListView that was clicked (this
         *                 will be a view provided by the adapter)
         * @param position The position of the view in the adapter.
         * @param id       The row id of the item that was clicked.
         */
        void onItemClick(RxViewPager parent, View view, int position, long id);
    }

    /**
     * Register a callback to be invoked when an item in this LinearListView has
     * been clicked.
     *
     * @param listener The callback that will be invoked.
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * Internal OnClickListener that this view associate of each of its children
     * so that they can respond to OnItemClick listener's events. Avoid setting
     * an OnClickListener manually. If you need it you can wrap the child in a
     * simple {@link FrameLayout}.
     */
    private class InternalOnClickListener implements View.OnClickListener {

        int mPosition;

        public InternalOnClickListener(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            if ((mOnItemClickListener != null) && (mAdapter != null)) {
                mOnItemClickListener.onItemClick(RxViewPager.this, v,
                        mPosition, mAdapter.getItemId(mPosition));
            }
        }
    }
}
