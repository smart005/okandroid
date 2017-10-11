package com.cloud.resources.tablist;

import android.support.v4.app.Fragment;

import com.cloud.resources.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.cloud.resources.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/3/9
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public interface OnTabLayoutListListener {
    public Fragment onBuildItem(int position);

    public IPagerTitleView onBuildPagerTitleView();

    public IPagerIndicator onBuildIndicator();
}
