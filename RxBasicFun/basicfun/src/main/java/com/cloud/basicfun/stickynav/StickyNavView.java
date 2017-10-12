package com.cloud.basicfun.stickynav;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.OverScroller;
import android.widget.ScrollView;

import com.cloud.basicfun.R;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.PixelUtils;
import com.cloud.resources.beans.TagsItem;
import com.cloud.resources.magicindicator.MagicIndicator;
import com.cloud.resources.magicindicator.ViewPagerHelper;
import com.cloud.resources.magicindicator.buildins.commonnavigator.CommonNavigator;
import com.cloud.resources.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import com.cloud.resources.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.cloud.resources.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import com.cloud.resources.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import com.cloud.resources.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/12/23
 * @Description:自定义视图＋导航＋滚动视图
 * @Modifier:
 * @ModifyContent:
 */
public class StickyNavView extends LinearLayout {

    private View mTop;
    private MagicIndicator mNav;
    private ViewPager mViewPager;

    private int mTopViewHeight;
    private ViewGroup mInnerScrollView;
    private boolean isTopHidden = false;

    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mTouchSlop;
    private int mMaximumVelocity, mMinimumVelocity;

    private float mLastY;
    private boolean mDragging;

    private boolean isInControl = false;

    private int sticky_nav_psts = 2059474143;

    private int id_sticky_nav_vp = 1080604779;

    private int magicIndicatorHeight = 38;

    private List<TagsItem> tagsItems = new ArrayList<TagsItem>();
    private ContentPagerAdapter curradapter = null;
    private OnStickyNavFragments onStickyNavFragments = null;

    private OnCustomIndicatorListener onCustomIndicatorListener;

    public void setOnCustomIndicatorListener(OnCustomIndicatorListener onCustomIndicatorListener) {
        this.onCustomIndicatorListener = onCustomIndicatorListener;
    }

    public StickyNavView(Context context, AttributeSet attrs) {
        super(context, attrs);
        try {
            setOrientation(LinearLayout.VERTICAL);
            mScroller = new OverScroller(context);
            mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
            mMaximumVelocity = ViewConfiguration.get(context)
                    .getScaledMaximumFlingVelocity();
            mMinimumVelocity = ViewConfiguration.get(context)
                    .getScaledMinimumFlingVelocity();
        } catch (Exception e) {
            Logger.L.error("init sticky view error:", e);
        }
    }

    public void setOnStickyNavFragments(OnStickyNavFragments stickyNavFragments) {
        this.onStickyNavFragments = stickyNavFragments;
    }

    @Override
    protected void onFinishInflate() {
        try {
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                if (getChildAt(i) instanceof ViewPager) {
                    mViewPager = (ViewPager) getChildAt(i);
                } else if (getChildAt(i) instanceof MagicIndicator) {
                    mNav = (MagicIndicator) getChildAt(i);
                } else {
                    if (mTop == null) {
                        mTop = getChildAt(i);
                    }
                }
            }
        } catch (Exception e) {
            Logger.L.error("finish inflate error:", e);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mViewPager != null && mNav != null) {
            ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
            params.height = getMeasuredHeight() - mNav.getMeasuredHeight();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mTop != null) {
            mTopViewHeight = mTop.getMeasuredHeight();
        }
    }

    public void setMagicIndicatorHeight(int height) {
        this.magicIndicatorHeight = height;
    }

    private MagicIndicator getMagicIndicator() {
        LayoutParams mparam = new LayoutParams(LayoutParams.MATCH_PARENT, PixelUtils.dip2px(getContext(), magicIndicatorHeight));
        MagicIndicator magicIndicator = new MagicIndicator(getContext());
        magicIndicator.setLayoutParams(mparam);
        magicIndicator.setBackgroundResource(R.drawable.white_bg);
        magicIndicator.setId(sticky_nav_psts);
        return magicIndicator;
    }

    private ViewPager getViewPager() {
        LayoutParams mparam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        ViewPager viewPager = new ViewPager(getContext());
        viewPager.setLayoutParams(mparam);
        viewPager.setId(id_sticky_nav_vp);
        return viewPager;
    }

    /**
     * @param fragmentActivity 当前activity
     * @param holder           头部View
     * @param isBind           是否绑定视图
     * @param <T>
     */
    public <T extends CustomContentViewHolder> void initialize(FragmentActivity fragmentActivity, T holder, boolean isBind) {
        try {
            if (fragmentActivity == null || holder == null) {
                return;
            }
            mTop = holder.getContentView();
            mTop.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (mTop != null) {
                        mTopViewHeight = mTop.getMeasuredHeight();
                    }
                }
            });
            this.addView(mTop);
            mNav = getMagicIndicator();
            this.addView(mNav);
            mViewPager = getViewPager();
            this.addView(mViewPager);
            curradapter = new ContentPagerAdapter(fragmentActivity.getSupportFragmentManager());
            mViewPager.addOnPageChangeListener(financilpagelistnter);
            mViewPager.setAdapter(curradapter);
            CommonNavigator commonNavigator = new CommonNavigator(getContext());
            commonNavigator.setSkimOver(true);
            commonNavigator.setIndicatorOnTop(false);
            commonNavigator.setAdjustMode(true);
            commonNavigator.setAdapter(commonNavigatorAdapter);
            mNav.setNavigator(commonNavigator);
            ViewPagerHelper.bind(mNav, mViewPager);
            if (isBind) {
                ButterKnife.bind(holder, mTop);
            }
        } catch (Exception e) {
            Logger.L.error("init sticky nav view error:", e);
        }
    }

    public void addTabItem(int tabId, String tabName, int location) {
        if (location >= 0 && location < (tagsItems.size() - 1)) {
            tagsItems.add(location, new TagsItem(tabId, tabName));
        } else {
            tagsItems.add(new TagsItem(tabId, tabName));
        }
        commonNavigatorAdapter.notifyDataSetChanged();
        curradapter.notifyDataSetChanged();
    }

    public void addTabItem(int tabId, String tabName) {
        addTabItem(tabId, tabName, -1);
    }

    private CommonNavigatorAdapter commonNavigatorAdapter = new CommonNavigatorAdapter() {

        @Override
        public int getCount() {
            return tagsItems.size();
        }

        @Override
        public IPagerTitleView getTitleView(Context context, final int index) {
            if (onCustomIndicatorListener != null) {
                IPagerTitleView titleView = onCustomIndicatorListener.getTitleView(context, index, mViewPager);
                if (titleView != null) {
                    return titleView;
                }
            }
            ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
            colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#262626"));
            colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#f24949"));
            colorTransitionPagerTitleView.setText(tagsItems.get(index).getName());
            colorTransitionPagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            colorTransitionPagerTitleView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mViewPager.setCurrentItem(index);
                }
            });
            return colorTransitionPagerTitleView;
        }

        @Override
        public IPagerIndicator getIndicator(Context context) {
            if (onCustomIndicatorListener != null) {
                IPagerIndicator indicator = onCustomIndicatorListener.getIndicator(context);
                if (indicator != null) {
                    return indicator;
                }
            }
            LinePagerIndicator indicator = new LinePagerIndicator(context);
            indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
            indicator.setLineHeight(PixelUtils.dip2px(context, 3));
            indicator.setColors(Color.parseColor("#ef2e35"));
            indicator.setXOffset(-PixelUtils.dip2px(context, 12));
            return indicator;
        }
    };

    private class ContentPagerAdapter extends FragmentPagerAdapter {

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return tagsItems.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public Fragment getItem(int position) {
            try {
                TagsItem tabItem = tagsItems.get(position);
                if (onStickyNavFragments != null) {
                    return onStickyNavFragments.getItem(position, tabItem);
                }
            } catch (Exception e) {
                Logger.L.info("get item error:", e);
            }
            return null;
        }

    }

    private ViewPager.OnPageChangeListener financilpagelistnter = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            int action = ev.getAction();
            float y = ev.getY();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mLastY = y;
                    getCurrentScrollView();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float dy = y - mLastY;
                    if (mInnerScrollView instanceof ScrollView) {
                        if (mInnerScrollView.getScrollY() == 0 && isTopHidden && dy > 0
                                && !isInControl) {
                            isInControl = true;
                            ev.setAction(MotionEvent.ACTION_CANCEL);
                            MotionEvent ev2 = MotionEvent.obtain(ev);
                            dispatchTouchEvent(ev);
                            ev2.setAction(MotionEvent.ACTION_DOWN);
                            return dispatchTouchEvent(ev2);
                        }
                    } else if (mInnerScrollView instanceof GridView) {
                        GridView lv = (GridView) mInnerScrollView;
                        View c = lv.getChildAt(lv.getFirstVisiblePosition());
                        if (!isInControl && c != null && c.getTop() == 0 && isTopHidden
                                && dy > 0) {
                            isInControl = true;
                            ev.setAction(MotionEvent.ACTION_CANCEL);
                            MotionEvent ev2 = MotionEvent.obtain(ev);
                            dispatchTouchEvent(ev);
                            ev2.setAction(MotionEvent.ACTION_DOWN);
                            return dispatchTouchEvent(ev2);
                        }
                    } else if (mInnerScrollView instanceof ListView) {
                        ListView lv = (ListView) mInnerScrollView;
                        View c = lv.getChildAt(lv.getFirstVisiblePosition());
                        if (!isInControl && c != null && c.getTop() == 0 && isTopHidden
                                && dy > 0) {
                            isInControl = true;
                            ev.setAction(MotionEvent.ACTION_CANCEL);
                            MotionEvent ev2 = MotionEvent.obtain(ev);
                            dispatchTouchEvent(ev);
                            ev2.setAction(MotionEvent.ACTION_DOWN);
                            return dispatchTouchEvent(ev2);
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            Logger.L.error("dispatchTouchEvent error:", e);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            final int action = ev.getAction();
            float y = ev.getY();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mLastY = y;
                    getCurrentScrollView();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float dy = y - mLastY;
                    if (Math.abs(dy) > mTouchSlop) {
                        mDragging = true;
                        if (mInnerScrollView instanceof ScrollView) {
                            // 如果topView没有隐藏
                            // 或sc的scrollY = 0 && topView隐藏 && 下拉，则拦截
                            if (!isTopHidden
                                    || (mInnerScrollView.getScrollY() == 0
                                    && isTopHidden && dy > 0)) {
                                initVelocityTrackerIfNotExists();
                                mVelocityTracker.addMovement(ev);
                                mLastY = y;
                                return true;
                            }
                        } else if (mInnerScrollView instanceof GridView) {
                            GridView lv = (GridView) mInnerScrollView;
                            View c = lv.getChildAt(lv.getFirstVisiblePosition());
                            // 如果topView没有隐藏
                            // 或sc的GridView在顶部 && topView隐藏 && 下拉，则拦截
                            if (!isTopHidden || //
                                    (c != null //
                                            && c.getTop() == 0//
                                            && isTopHidden && dy > 0)) {
                                initVelocityTrackerIfNotExists();
                                mVelocityTracker.addMovement(ev);
                                mLastY = y;
                                return true;
                            }
                        } else if (mInnerScrollView instanceof ListView) {
                            ListView lv = (ListView) mInnerScrollView;
                            View c = lv.getChildAt(lv.getFirstVisiblePosition());
                            // 如果topView没有隐藏
                            // 或sc的GridView在顶部 && topView隐藏 && 下拉，则拦截
                            if (!isTopHidden || //
                                    (c != null //
                                            && c.getTop() == 0//
                                            && isTopHidden && dy > 0)) {
                                initVelocityTrackerIfNotExists();
                                mVelocityTracker.addMovement(ev);
                                mLastY = y;
                                return true;
                            }
                        }
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    mDragging = false;
                    recycleVelocityTracker();
                    break;
            }
        } catch (Exception e) {
            Logger.L.error("onInterceptTouchEvent error:", e);
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void getCurrentScrollView() {
        try {
            int currentItem = mViewPager.getCurrentItem();
            PagerAdapter a = mViewPager.getAdapter();
            if (a instanceof FragmentPagerAdapter) {
                FragmentPagerAdapter fadapter = (FragmentPagerAdapter) a;
                Fragment item = (Fragment) fadapter.instantiateItem(mViewPager, currentItem);
                ViewGroup vg = getScrollView(item.getView());
                if (vg != null) {
                    mInnerScrollView = vg;
                } else {
                    mInnerScrollView = (ViewGroup) item.getView();
                }
            } else if (a instanceof FragmentStatePagerAdapter) {
                FragmentStatePagerAdapter fsAdapter = (FragmentStatePagerAdapter) a;
                Fragment item = (Fragment) fsAdapter.instantiateItem(mViewPager, currentItem);
                ViewGroup vg = getScrollView(item.getView());
                if (vg != null) {
                    mInnerScrollView = vg;
                } else {
                    mInnerScrollView = (ViewGroup) item.getView();
                }
            }
        } catch (Exception e) {
            Logger.L.error("get current scrollview error:", e);
        }
    }

    private boolean isScrollView(View view) {
        if (view instanceof ScrollView || view instanceof ListView || view instanceof GridView) {
            return true;
        }
        return false;
    }

    private ViewGroup getScrollView(View view) {
        ViewGroup viewGroup = null;
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            if (isScrollView(vg)) {
                viewGroup = vg;
            } else {
                int count = vg.getChildCount();
                for (int i = 0; i < count; i++) {
                    if (isScrollView(vg.getChildAt(i))) {
                        viewGroup = (ViewGroup) vg.getChildAt(i);
                        break;
                    }
                }
            }
        }
        return viewGroup;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            initVelocityTrackerIfNotExists();
            mVelocityTracker.addMovement(event);
            int action = event.getAction();
            float y = event.getY();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    if (!mScroller.isFinished())
                        mScroller.abortAnimation();
                    mLastY = y;
                    return true;
                case MotionEvent.ACTION_MOVE:
                    float dy = y - mLastY;
                    if (!mDragging && Math.abs(dy) > mTouchSlop) {
                        mDragging = true;
                    }
                    if (mDragging) {
                        scrollBy(0, (int) -dy);
                        // 如果topView隐藏，且上滑动时，则改变当前事件为ACTION_DOWN
                        if (getScrollY() == mTopViewHeight && dy < 0) {
                            event.setAction(MotionEvent.ACTION_DOWN);
                            dispatchTouchEvent(event);
                            isInControl = false;
                        }
                    }
                    mLastY = y;
                    break;
                case MotionEvent.ACTION_CANCEL:
                    mDragging = false;
                    recycleVelocityTracker();
                    if (!mScroller.isFinished()) {
                        mScroller.abortAnimation();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    mDragging = false;
                    mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                    int velocityY = (int) mVelocityTracker.getYVelocity();
                    if (Math.abs(velocityY) > mMinimumVelocity) {
                        fling(-velocityY);
                    }
                    recycleVelocityTracker();
                    break;
            }
        } catch (Exception e) {
            Logger.L.error("onTouchEvent error:", e);
        }
        return super.onTouchEvent(event);
    }

    public void fling(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > mTopViewHeight) {
            y = mTopViewHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
        isTopHidden = getScrollY() == mTopViewHeight;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }
}
