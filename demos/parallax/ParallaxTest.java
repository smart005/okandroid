package com.geek.mibao.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.cloud.basicfun.BaseFragmentActivity;
import com.cloud.core.utils.PixelUtils;
import com.cloud.resources.beans.TagsItem;
import com.cloud.resources.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.cloud.resources.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import com.cloud.resources.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import com.cloud.resources.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import com.cloud.resources.parallax.HostView;
import com.cloud.resources.parallax.OnParallaxTabListViewListener;
import com.cloud.resources.parallax.OnParallaxViewContentFragmentListener;
import com.cloud.resources.parallax.ParallaxTabListView;
import com.geek.mibao.R;
import com.geek.mibao.fragments.ParallaxContentFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/11
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class ParallaxTest extends BaseFragmentActivity implements HostView {

    @Bind(R.id.test_ptlv)
    ParallaxTabListView testPtlv;

    private static ParallaxTest parallaxTest = null;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.parallax_view);
        ButterKnife.bind(this);
        init(bundle);
    }

    public static ParallaxTest getParallaxTest() {
        return parallaxTest;
    }

    private void init(Bundle bundle) {
        parallaxTest = this;
        testPtlv.setDefTabPosition(0);
        testPtlv.setSaveInstanceState(bundle);
        testPtlv.setTopContentOffset(PixelUtils.dip2px(this, 46));
        final List<TagsItem> tagsItems = testPtlv.getTagsItems();
        tagsItems.add(new TagsItem(1, "活动中心"));
        tagsItems.add(new TagsItem(2, "专题"));
        testPtlv.setOnParallaxViewContentFragmentListener(new OnParallaxViewContentFragmentListener() {
            @Override
            public Fragment onBuildItem(int position) {
                ParallaxContentFragment contentFragment = ParallaxContentFragment.newInstance();
                Bundle mb = new Bundle();
                mb.putInt("POSITION", position);
                mb.putInt("PLACEHODLE_HEIGHT", testPtlv.getTopContentHeight());
                contentFragment.setArguments(mb);
                return contentFragment;
            }
        });
        testPtlv.setOnParallaxTabListViewListener(new OnParallaxTabListViewListener() {
            @Override
            public IPagerTitleView getTitleView(Context context, final int index, final ViewPager viewPager) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#262626"));
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#f24949"));
                colorTransitionPagerTitleView.setText(tagsItems.get(index).getName());
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(Color.parseColor("#ef2e35"));
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setLineHeight(0);
                return indicator;
            }
        });
        View inflate = View.inflate(this, R.layout.parallax_top_content_view, null);
        RelativeLayout.LayoutParams vparam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        inflate.setLayoutParams(vparam);
        testPtlv.setTopContentView(inflate);
        testPtlv.initializa(getSupportFragmentManager(), bundle);
    }

    @Override
    public void onScrollingContentScroll(int scrollY, int pagePosition) {
        testPtlv.onScrollingContentScroll(scrollY, pagePosition);
    }
}
