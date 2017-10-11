package com.cloud.resources.widgets;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/7/8
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class TopTabs {

    private int screenWidth = 0;
    private int currentItem = 0;
    private int tabnum = 0;
    private Activity activity;

    protected void onTabChanged(int position) {

    }

    public interface OnTabItemListener {
        public boolean onTabItemClick();
    }

    private OnTabItemListener onTabItemListener = null;

    public void setOnTabItemListener(OnTabItemListener listener) {
        onTabItemListener = listener;
    }

    public void setTabnum(int tabnum) {
        this.tabnum = tabnum;
    }

    public int getTabnum() {
        return this.tabnum;
    }

    /**
     * 设置滑动条的宽度为屏幕的1/n(根据Tab的个数而定)
     */
    public void initTabLineWidth(Activity activity, ImageView cursor, int tabnum) {
        this.activity = activity;
        this.tabnum = tabnum;
        DisplayMetrics dpMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        if (cursor != null) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) cursor
                    .getLayoutParams();
            lp.width = screenWidth / tabnum;
            cursor.setLayoutParams(lp);
        }
    }

    public class OnTabsPageChangeListener implements ViewPager.OnPageChangeListener {

        private ImageView mcursor = null;

        public OnTabsPageChangeListener(ImageView cursor, int tabnum) {
            this.mcursor = cursor;
            TopTabs.this.tabnum = tabnum;
        }

        /**
         * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
         */
        @Override
        public void onPageScrollStateChanged(int state) {

        }

        /**
         * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比 offsetPixels:当前页面偏移的像素位置
         */
        @Override
        public void onPageScrolled(int position, float offset, int offsetPixels) {
            switchSelector(mcursor, position, offset);
        }

        @Override
        public void onPageSelected(int position) {
            currentItem = position;
            onTabChanged(position);
        }
    }

    public void switchSelector(ImageView cursor, int position, float offset) {
        if (cursor != null) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) cursor
                    .getLayoutParams();
            double itemwidth = screenWidth * 1.0 / tabnum;
            if (currentItem == position) {
                lp.leftMargin = (int) (offset * itemwidth + currentItem
                        * itemwidth);
            } else {
                lp.leftMargin = (int) (-(1 - offset) * itemwidth + currentItem
                        * itemwidth);
            }
            cursor.setLayoutParams(lp);
        }
    }

    public class FragmentTabClickListener implements View.OnClickListener {
        private int index = 0;
        private ViewPager mVPager = null;

        public FragmentTabClickListener(ViewPager mpager, int index) {
            this.index = index;
            this.mVPager = mpager;
        }


        @Override
        public void onClick(View v) {
            if (onTabItemListener != null) {
                if (onTabItemListener.onTabItemClick()) {
                    if (mVPager != null) {
                        mVPager.setCurrentItem(index);
                    }
                }
            } else {
                if (mVPager != null) {
                    mVPager.setCurrentItem(index);
                }
            }
        }
    }
}
