package com.cloud.resources.tablist;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.cloud.core.ObjectJudge;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.PixelUtils;
import com.cloud.resources.magicindicator.MagicIndicator;
import com.cloud.resources.magicindicator.ViewPagerHelper;
import com.cloud.resources.magicindicator.buildins.commonnavigator.CommonNavigator;
import com.cloud.resources.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import com.cloud.resources.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.cloud.resources.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import com.cloud.resources.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import com.cloud.resources.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import com.cloud.resources.tabstrips.TabItem;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/3/9
 * @Description:列表视图快速切换
 * @Modifier:
 * @ModifyContent:
 */
public class TabLayoutList extends RelativeLayout {

    private ContentPagerAdapter curradapter = null;
    private List<TabItem> tabItems = new ArrayList<TabItem>();
    private OnTabLayoutListListener tabLayoutListListener = null;
    private OnTabLayoutListPageSelectedListener tabLayoutListPageSelectedListener = null;
    private int MAGIC_INDICATOR_ID = 1843837891;
    private int TAB_CONTENT_VP = 341867028;
    private OnTabLayoutListItemListener onTabLayoutListItemListener = null;

    public TabLayoutList(Context context) {
        this(context, null);
    }

    public TabLayoutList(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.addView(buildMagicIndicator());
        this.addView(buildViewPager());
    }

    public MagicIndicator buildMagicIndicator() {
        RelativeLayout.LayoutParams miparam = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                PixelUtils.dip2px(getContext(), 38));
        MagicIndicator magicIndicator = new MagicIndicator(getContext());
        magicIndicator.setLayoutParams(miparam);
        magicIndicator.setBackgroundColor(Color.WHITE);
        magicIndicator.setId(MAGIC_INDICATOR_ID);
        return magicIndicator;
    }

    public ViewPager buildViewPager() {
        ViewPager contentVP = new ViewPager(getContext());
        RelativeLayout.LayoutParams vpparam = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        vpparam.addRule(RelativeLayout.BELOW, MAGIC_INDICATOR_ID);
        contentVP.setLayoutParams(vpparam);
        contentVP.setId(TAB_CONTENT_VP);
        contentVP.setBackgroundColor(Color.WHITE);
        return contentVP;
    }

    public void setOffscreenPageLimit(int limit) {
        ViewPager tabContentVp = (ViewPager) findViewById(TAB_CONTENT_VP);
        if (tabContentVp != null) {
            tabContentVp.setOffscreenPageLimit(limit);
        }
    }

    public TabLayoutList setTabItems(List<TabItem> tabItems) {
        this.tabItems.clear();
        if (!ObjectJudge.isNullOrEmpty(tabItems)) {
            this.tabItems.addAll(tabItems);
        }
        return this;
    }

    private boolean isAdded(TabItem tabItem) {
        boolean flag = false;
        for (TabItem item : tabItems) {
            if (TextUtils.equals(item.getId(), tabItem.getId())) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public TabLayoutList addTabItem(TabItem tabItem) {
        if (!isAdded(tabItem)) {
            this.tabItems.add(tabItem);
        }
        return this;
    }

    public TabLayoutList setTabItem(int location, TabItem tabItem) {
        if (!isAdded(tabItem)) {
            this.tabItems.add(location, tabItem);
        }
        return this;
    }

    public TabLayoutList setTabLayoutListListener(OnTabLayoutListListener listener) {
        this.tabLayoutListListener = listener;
        return this;
    }

    public TabLayoutList setTabLayoutListPageSelectedListener(OnTabLayoutListPageSelectedListener listener) {
        this.tabLayoutListPageSelectedListener = listener;
        return this;
    }

    public TabLayoutList setOnTabLayoutListItemListener(OnTabLayoutListItemListener listener) {
        this.onTabLayoutListItemListener = listener;
        return this;
    }

    public void instance(FragmentManager fm) {
        try {
            MagicIndicator tabBarPsts = (MagicIndicator) findViewById(MAGIC_INDICATOR_ID);
            ViewPager tabContentVp = (ViewPager) findViewById(TAB_CONTENT_VP);
            if (tabContentVp != null) {
                tabContentVp.setOffscreenPageLimit(3);
                curradapter = new ContentPagerAdapter(fm);
                tabContentVp.setOnPageChangeListener(viewpagelistnter);
                tabContentVp.setAdapter(curradapter);
                CommonNavigator commonNavigator = new CommonNavigator(getContext());
                commonNavigator.setSkimOver(true);
                commonNavigator.setIndicatorOnTop(false);
                commonNavigator.setAdapter(commonNavigatorAdapter);
                tabBarPsts.setNavigator(commonNavigator);
                ViewPagerHelper.bind(tabBarPsts, tabContentVp);
            }
        } catch (Exception e) {
            Logger.L.warn("instance tab layout list error:", e);
        }
    }

    private CommonNavigatorAdapter commonNavigatorAdapter = new CommonNavigatorAdapter() {

        @Override
        public int getCount() {
            return tabItems.size();
        }

        @Override
        public IPagerTitleView getTitleView(Context context, final int index) {
            if (tabLayoutListListener != null && tabLayoutListListener.onBuildPagerTitleView() != null) {
                return tabLayoutListListener.onBuildPagerTitleView();
            } else {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#262626"));
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#f24949"));
                colorTransitionPagerTitleView.setText(tabItems.get(index).getName());
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ViewPager tabContentVp = (ViewPager) findViewById(TAB_CONTENT_VP);
                        if (tabContentVp != null) {
                            tabContentVp.setCurrentItem(index);
                        }
                    }
                });
                return colorTransitionPagerTitleView;
            }
        }

        @Override
        public IPagerIndicator getIndicator(Context context) {
            if (tabLayoutListListener != null && tabLayoutListListener.onBuildIndicator() != null) {
                return tabLayoutListListener.onBuildIndicator();
            } else {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setLineHeight(0);
                return indicator;
            }
        }
    };

    private ViewPager.OnPageChangeListener viewpagelistnter = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            try {
                EventPageSelecteItem eventPageSelecteItem = new EventPageSelecteItem();
                eventPageSelecteItem.setKey("TAB_LAYOUT_LIST_PAGE_SELECTED_FLAG");
                eventPageSelecteItem.setPosition(position);
                EventBus.getDefault().post(eventPageSelecteItem);
                if (tabLayoutListPageSelectedListener != null) {
                    tabLayoutListPageSelectedListener.onPageSelected(position);
                }
            } catch (Exception e) {
                Logger.L.debug("tab layout list page select error:", e);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            try {
                if (state == 1) {
                    EventBus.getDefault().post("TAB_LAYOUT_LIST_PAGE_SCROLLING_FLAG");
                } else if (state == 2) {
                    EventBus.getDefault().post("TAB_LAYOUT_LIST_PAGE_SCROLL_FINISHED_FLAG");
                }
            } catch (Exception e) {
                Logger.L.debug("tab layout list scroll state error:", e);
            }
        }
    };

    private class ContentPagerAdapter extends FragmentStatePagerAdapter {

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return tabItems.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }

        @Override
        public Fragment getItem(int position) {
            if (tabLayoutListListener != null) {
                return tabLayoutListListener.onBuildItem(position);
            } else {
                return null;
            }
        }
    }

    /**
     * 刷新tab列表
     */
    public void refreshTabList() {
        commonNavigatorAdapter.notifyDataSetChanged();
        curradapter.notifyDataSetChanged();
    }
}
