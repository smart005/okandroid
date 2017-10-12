package com.cloud.basicfun.shuffling;

import com.youth.banner.BannerConfig;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/12/25
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public interface OnShufflingBannerConfig {

    /**
     * 获取轮播图图片格式(%s?suffix)
     *
     * @return
     */
    public String getShufflingBannerImageFormat();

    /**
     * 获取指示器对齐方式
     * {@link BannerConfig}
     *
     * @return
     */
    public int getIndicatorGravity();
}
