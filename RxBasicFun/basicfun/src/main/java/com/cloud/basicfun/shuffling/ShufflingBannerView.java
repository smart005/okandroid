package com.cloud.basicfun.shuffling;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import com.cloud.core.ObjectJudge;
import com.cloud.core.ObjectManager;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.JsonUtils;
import com.cloud.resources.beans.BaseImageItem;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/12/25
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class ShufflingBannerView extends Banner {

    private OnShufflingBanner onShufflingBanner = null;
    private static String imageUrlFormat = "";
    private static OnShufflingBannerConfig onShufflingBannerConfig = null;
    private int sbwidth = 0;
    private int sbheight = 0;
    private List<String> slbtitles = null;

    public ShufflingBannerView(Context context) {
        super(context);
    }

    public ShufflingBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnShufflingBanner(OnShufflingBanner listener) {
        this.onShufflingBanner = listener;
    }

    public static void setOnShufflingBannerConfig(OnShufflingBannerConfig config) {
        onShufflingBannerConfig = config;
    }

    /**
     * 初始化轮播图
     *
     * @param proportion 图片宽高比
     */
    public void instance(double proportion) {
        try {
            DisplayMetrics dm = ObjectManager.getDisplayMetrics(getContext());
            int mheight = (int) (dm.widthPixels / proportion);
            getLayoutParams().height = mheight;
            sbwidth = dm.widthPixels;
            sbheight = mheight;
            super.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    if (onShufflingBanner != null) {
                        onShufflingBanner.onShufflingClick(position);
                    }
                }
            });
        } catch (Exception e) {
            Logger.L.error("init shuffling error:", e);
        }
    }

    public int getBannerWidth() {
        return this.sbwidth;
    }

    public int getBannerHeight() {
        return this.sbheight;
    }

    public void setSlbtitles(List<String> titles) {
        this.slbtitles = titles;
    }

    public void bind(List<String> imglst) {
        try {
            if (ObjectJudge.isNullOrEmpty(imglst)) {
                return;
            }
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            this.setImageLoader(new FrescoImageLoader());
            this.setImages(imglst);
            this.setBannerAnimation(Transformer.Default);
            this.isAutoPlay(true);
            this.setDelayTime(1500);
            if (onShufflingBannerConfig != null) {
                if (onShufflingBannerConfig.getIndicatorGravity() != 0) {
                    this.setIndicatorGravity(onShufflingBannerConfig.getIndicatorGravity());
                } else {
                    this.setIndicatorGravity(BannerConfig.CENTER);
                }
            } else {
                this.setIndicatorGravity(BannerConfig.CENTER);
            }
            if (ObjectJudge.isNullOrEmpty(slbtitles)) {
                List<String> titles = new ArrayList<>();
                for (int i = 0; i < imglst.size(); i++) {
                    titles.add("");
                }
                this.setBannerTitles(titles);
            } else {
                this.setBannerTitles(slbtitles);
            }
            this.start();
        } catch (Exception e) {
            Logger.L.error("bind shuffling error:", e);
        }
    }

    /**
     * 绑定轮播图
     *
     * @param imgListJson    图片数组json {@link BaseImageItem}
     * @param shufflingWidth 轮播图宽度
     */
    public void bind(String imgListJson, int shufflingWidth) {
        try {
            if (onShufflingBannerConfig == null) {
                return;
            }
            List<BaseImageItem> baseImageItems = JsonUtils.parseArray(imgListJson, BaseImageItem.class);
            List<String> imglst = new ArrayList<String>();
            for (BaseImageItem baseImageItem : baseImageItems) {
                String url = onShufflingBannerConfig.getShufflingBannerImageFormat();
                url = String.format(url, baseImageItem.getUrl());
                url = MessageFormat.format(url, String.valueOf(shufflingWidth));
                imglst.add(url);
            }
            bind(imglst);
        } catch (Exception e) {
            Logger.L.error("bind shuffling error:", e);
        }
    }
}
