package com.cloud.resources.parallax;

import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/11
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class ParallaxViewPagerChangeListener implements ViewPager.OnPageChangeListener {
    protected ViewPager mViewPager;
    protected ParallaxFragmentPagerAdapter mAdapter;

    protected View mHeader;

    protected int mNumFragments;

    public ParallaxViewPagerChangeListener(ViewPager viewPager, ParallaxFragmentPagerAdapter adapter, View headerView) {
        mViewPager = viewPager;
        mAdapter = adapter;
        mNumFragments = mAdapter.getCount();
        mHeader = headerView;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int currentItem = mViewPager.getCurrentItem();
        if (positionOffsetPixels > 0) {
            SparseArrayCompat<TabHolderScrollingContent> scrollTabHolders = mAdapter.getScrollTabHolders();

            TabHolderScrollingContent fragmentContent;
            if (position < currentItem) {
                fragmentContent = scrollTabHolders.valueAt(position);
            } else {
                fragmentContent = scrollTabHolders.valueAt(position + 1);
            }

            fragmentContent.adjustScroll((int) mHeader.getTranslationY());
        }
    }

    @Override
    public void onPageSelected(int position) {
        SparseArrayCompat<TabHolderScrollingContent> scrollTabHolders = mAdapter.getScrollTabHolders();

        if (scrollTabHolders == null || scrollTabHolders.size() != mNumFragments) {
            return;
        }

        TabHolderScrollingContent currentHolder = scrollTabHolders.valueAt(position);
        currentHolder.adjustScroll((int) mHeader.getTranslationY());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
