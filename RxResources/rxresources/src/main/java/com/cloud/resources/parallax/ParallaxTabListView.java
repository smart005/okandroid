package com.cloud.resources.parallax;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.cloud.core.logger.Logger;
import com.cloud.resources.R;
import com.cloud.resources.beans.TagsItem;
import com.cloud.resources.magicindicator.MagicIndicator;
import com.cloud.resources.magicindicator.ViewPagerHelper;
import com.cloud.resources.magicindicator.buildins.commonnavigator.CommonNavigator;
import com.cloud.resources.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import com.cloud.resources.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.cloud.resources.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/9
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class ParallaxTabListView extends RelativeLayout implements HostView {

    ViewPager parallaxTabListVp;
    RelativeLayout parallaxTabListTopContentRl;
    MagicIndicator parallaxTabListMic;
    RelativeLayout parallaxTabListTopRl;
    RelativeLayout parallaxTabListRl;

    private String TOP_CONTENT_VIEW_TRANSLATION_Y = "d1895cb6e92842c28647b64cf8c7b571";
    private String TOP_CONTAINER_VIEW_TRANSLATION_Y = "873580fb5dcd49c1a739b6a99e279e5c";
    private OnParallaxViewContentFragmentListener onParallaxViewContentFragmentListener = null;
    private PTContentFragmentAdapter adapter = null;
    private List<TagsItem> tagsItems = new ArrayList<TagsItem>();
    private int numFragment = 0;
    private int defTabPosition = 0;
    private int topContentOffset = 0;//头部内容偏移量
    private int mMaxHeaderTranslation = 0;
    private int topContentHeight = 0;//头部内容高度
    private OnParallaxTabListViewListener onParallaxTabListViewListener = null;
    private View topContentView = null;

    public ParallaxTabListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        try {
            View view = View.inflate(context, R.layout.parallax_tab_list_view, null);
            ViewGroup.LayoutParams vgparam = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            parallaxTabListVp = (ViewPager) view.findViewById(R.id.parallax_tab_list_vp);
            parallaxTabListTopContentRl = (RelativeLayout) view.findViewById(R.id.parallax_tab_list_top_content_rl);
            parallaxTabListMic = (MagicIndicator) view.findViewById(R.id.parallax_tab_list_mic);
            parallaxTabListTopRl = (RelativeLayout) view.findViewById(R.id.parallax_tab_list_top_rl);
            parallaxTabListRl = (RelativeLayout) view.findViewById(R.id.parallax_tab_list_rl);
            this.addView(view, vgparam);
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    public void setOnParallaxTabListViewListener(OnParallaxTabListViewListener listener) {
        this.onParallaxTabListViewListener = listener;
    }

    public List<TagsItem> getTagsItems() {
        return this.tagsItems;
    }

    public void setTopContentView(View contentView) {
        this.topContentView = contentView;
    }

    public void setDefTabPosition(int defTabPosition) {
        if (defTabPosition < 0) {
            defTabPosition = 0;
        } else if ((defTabPosition + 1) >= tagsItems.size()) {
            defTabPosition = tagsItems.size() - 1;
        }
        this.defTabPosition = defTabPosition;
    }

    public void initializa(FragmentManager fragmentManager,
                           Bundle savedInstanceState) {
        try {
            if (fragmentManager == null) {
                return;
            }
            if (topContentView != null) {
                parallaxTabListTopContentRl.addView(topContentView);
            }
            if (tagsItems.size() > 3) {
                numFragment = 3;
            } else {
                numFragment = tagsItems.size();
            }
            parallaxTabListTopRl.measure(0, 0);
            mMaxHeaderTranslation = -parallaxTabListTopRl.getMeasuredHeight() + topContentOffset;
            parallaxTabListTopContentRl.measure(0, 0);
            topContentHeight = parallaxTabListTopContentRl.getMeasuredHeight();
            if (savedInstanceState != null) {
                if (savedInstanceState.containsKey(TOP_CONTENT_VIEW_TRANSLATION_Y)) {
                    parallaxTabListTopContentRl.setTranslationY(savedInstanceState.getFloat(TOP_CONTENT_VIEW_TRANSLATION_Y));
                }
                if (savedInstanceState.containsKey(TOP_CONTAINER_VIEW_TRANSLATION_Y)) {
                    parallaxTabListTopRl.setTranslationY(savedInstanceState.getFloat(TOP_CONTAINER_VIEW_TRANSLATION_Y));
                }
            }
            if (adapter == null) {
                adapter = new PTContentFragmentAdapter(fragmentManager, numFragment);
            }
            parallaxTabListVp.setAdapter(adapter);
            parallaxTabListVp.setOffscreenPageLimit(numFragment);
            parallaxTabListVp.addOnPageChangeListener(new ParallaxViewPagerChangeListener(
                    parallaxTabListVp, adapter, parallaxTabListTopRl
            ));
            CommonNavigator commonNavigator = new CommonNavigator(getContext());
            commonNavigator.setSkimOver(true);
            commonNavigator.setIndicatorOnTop(true);
            commonNavigator.setAdjustMode(true);
            commonNavigator.setAdapter(commonNavigatorAdapter);
            parallaxTabListMic.setNavigator(commonNavigator);
            ViewPagerHelper.bind(parallaxTabListMic, parallaxTabListVp);
            adapter.notifyDataSetChanged();
            commonNavigatorAdapter.notifyDataSetChanged();
            parallaxTabListVp.setCurrentItem(defTabPosition);
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    public int getTopContentHeight() {
        return this.topContentHeight;
    }

    private CommonNavigatorAdapter commonNavigatorAdapter = new CommonNavigatorAdapter() {

        @Override
        public int getCount() {
            return tagsItems.size();
        }

        @Override
        public IPagerTitleView getTitleView(Context context, int index) {
            if (onParallaxTabListViewListener != null) {
                return onParallaxTabListViewListener.getTitleView(context, index, parallaxTabListVp);
            }
            return null;
        }

        @Override
        public IPagerIndicator getIndicator(Context context) {
            if (onParallaxTabListViewListener != null) {
                return onParallaxTabListViewListener.getIndicator(context);
            }
            return null;
        }
    };

    public void setSaveInstanceState(Bundle outState) {
        if (outState == null) {
            return;
        }
        outState.putFloat(TOP_CONTENT_VIEW_TRANSLATION_Y, parallaxTabListTopContentRl.getTranslationY());
        outState.putFloat(TOP_CONTAINER_VIEW_TRANSLATION_Y, parallaxTabListTopRl.getTranslationY());
    }

    public void setTopContentOffset(int offset) {
        this.topContentOffset = offset;
    }

    public void setOnParallaxViewContentFragmentListener(OnParallaxViewContentFragmentListener listener) {
        this.onParallaxViewContentFragmentListener = listener;
    }

    private class PTContentFragmentAdapter extends ParallaxFragmentPagerAdapter {

        public PTContentFragmentAdapter(FragmentManager fm, int numFragments) {
            super(fm, numFragments);
        }

        @Override
        public Fragment getItem(int position) {
            if (onParallaxViewContentFragmentListener != null) {
                return onParallaxViewContentFragmentListener.onBuildItem(position);
            }
            return null;
        }
    }

    @Override
    public void onScrollingContentScroll(int scrollY, int pagePosition) {
        if (parallaxTabListVp.getCurrentItem() == pagePosition) {
            float translationY = Math.max(-scrollY, mMaxHeaderTranslation);
            parallaxTabListTopRl.setTranslationY(translationY);
            parallaxTabListTopContentRl.setTranslationY(-translationY / 3);
        }
    }
}
