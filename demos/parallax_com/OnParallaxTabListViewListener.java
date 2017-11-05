package com.cloud.resources.parallax;

import android.content.Context;
import android.support.v4.view.ViewPager;

import com.cloud.resources.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.cloud.resources.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/11
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public interface OnParallaxTabListViewListener {
    public IPagerTitleView getTitleView(Context context, final int index, ViewPager viewPager);

    public IPagerIndicator getIndicator(Context context);
}
