package com.cloud.basicfun.stickynav;

import android.content.Context;
import android.support.v4.view.ViewPager;

import com.cloud.resources.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.cloud.resources.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;

/**
 * @Author Gs
 * @Email:gs_12@foxmail.com
 * @CreateTime:2017/3/4
 * @Description: 自定义指示器接口
 * @Modifier:
 * @ModifyContent:
 */

public interface OnCustomIndicatorListener {
    IPagerTitleView getTitleView(Context context, int index, ViewPager mViewPager);

    IPagerIndicator getIndicator(Context context);
}
