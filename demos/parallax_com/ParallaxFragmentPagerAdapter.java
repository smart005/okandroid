package com.cloud.resources.parallax;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.view.ViewGroup;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/11
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public abstract class ParallaxFragmentPagerAdapter extends FragmentPagerAdapter {

    private SparseArrayCompat<TabHolderScrollingContent> mScrollTabHolders;
    private int mNumFragments;

    public ParallaxFragmentPagerAdapter(FragmentManager fm, int numFragments) {
        super(fm);
        mScrollTabHolders = new SparseArrayCompat<>();
        mNumFragments = numFragments;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object object = super.instantiateItem(container, position);

        mScrollTabHolders.put(position, (TabHolderScrollingContent) object);

        return object;
    }

    @Override
    public int getCount() {
        return mNumFragments;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public SparseArrayCompat<TabHolderScrollingContent> getScrollTabHolders() {
        return mScrollTabHolders;
    }
}
