package com.cloud.resources.widgets;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloud.core.ObjectJudge;
import com.cloud.core.logger.Logger;
import com.cloud.resources.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/3/9
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class DepthContentVPFragment extends Fragment {

    private ContentPagerAdapter curradapter = null;
    private List<Integer> ids = new ArrayList<Integer>();
    private List<Fragment> fragments = new ArrayList<Fragment>();

    public void setIds(List<Integer> ids) {
        this.ids.clear();
        if (!ObjectJudge.isNullOrEmpty(ids)) {
            this.ids.addAll(ids);
        }
    }

    public List<Integer> getIds() {
        return this.ids;
    }

    protected Fragment getDetailFragment(int position) {
        return null;
    }

    public void onPageSelected(int position, Fragment fragment) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getContext(), R.layout.tab_layout_list_item_content_view, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ViewPager tabContentVp = (ViewPager) view.findViewById(R.id.tab_content_vp);
        curradapter = new ContentPagerAdapter();
        tabContentVp.setAdapter(curradapter);
        tabContentVp.setPageTransformer(true, new DepthPageTransformer());
        tabContentVp.addOnPageChangeListener(pageChangeListener);
    }

    public void setOffscreenPageLimit(int limit) {
        if (getView() == null) {
            return;
        }
        ViewPager tabContentVp = getViewPager();
        if (tabContentVp == null) {
            return;
        }
        tabContentVp.setOffscreenPageLimit(limit);
    }

    public void setCurrentItem(int position) {
        if (getView() == null) {
            return;
        }
        ViewPager tabContentVp = getViewPager();
        if (tabContentVp == null) {
            return;
        }
        tabContentVp.setCurrentItem(position);
    }

    public void notifyDataSetChanged() {
        if (curradapter != null) {
            curradapter.notifyDataSetChanged();
        }
    }

    public ViewPager getViewPager() {
        ViewPager tabContentVp = (ViewPager) getView().findViewById(R.id.tab_content_vp);
        return tabContentVp;
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            ViewPager tabContentVp = getViewPager();
            FragmentStatePagerAdapter f = (FragmentStatePagerAdapter) tabContentVp.getAdapter();
            Fragment someFragment = (Fragment) f.instantiateItem(tabContentVp, position);
            DepthContentVPFragment.this.onPageSelected(position, someFragment);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private class ContentPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return ObjectJudge.isNullOrEmpty(ids) ? 0 : ids.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            try {
                if (fragments.size() > position) {
                    Fragment fragment = fragments.get(position);
                    container.removeView(fragment.getView());
                }
            } catch (Exception e) {
                Logger.L.error("remove list view item error:", e);
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = null;
            try {
                if (fragments.size() > position) {
                    fragment = fragments.get(position);
                } else {
                    fragment = getDetailFragment(position);
                    fragments.add(fragment);
                }
                //如果fragment还没有added
                if (!fragment.isAdded()) {
                    FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                    ft.add(fragment, String.format("contentdepthtab_%s", position));
                    ft.commit();
                    /**
                     * 在用FragmentTransaction.commit()方法提交FragmentTransaction对象后
                     * 会在进程的主线程中，用异步的方式来执行。
                     * 如果想要立即执行这个等待中的操作，就要调用这个方法（只能在主线程中调用）。
                     * 要注意的是，所有的回调和相关的行为都会在这个调用中被执行完成，因此要仔细确认这个方法的调用位置。
                     */
                    getChildFragmentManager().executePendingTransactions();
                }
                if (fragment.getView().getParent() == null) {
                    container.addView(fragment.getView()); // 为viewpager增加布局
                }
            } catch (Exception e) {
                Logger.L.error("build fragment item error:", e);
            }
            return fragment.getView();
        }
    }
}
