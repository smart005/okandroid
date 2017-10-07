package com.cloud.core.enums;

import android.text.TextUtils;

import com.cloud.core.cache.RxCache;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/12/30
 * @Description:图片规则类型
 * @Modifier:
 * @ModifyContent:
 */
public enum ImgRuleType {
    /**
     * 原图
     */
    None("?x-oss-process=image/auto-orient,0/format,webp/sharpen,100/quality,q_75/interlace,1",
            "?imageslim"),
    /**
     * 按宽度进行缩放
     */
    GeometricForWidth("?x-oss-process=image/resize,lfit,w_{0},limit_1/auto-orient,0/sharpen,100/quality,q_75/interlace,1/format,webp",
            "?imageMogr2/auto-orient/thumbnail/{0}x/format/webp/interlace/1/q/75/sharpen/1/ignore-error/1"),
    /**
     * 按宽度进行缩放(圆角)
     */
    GeometricRoundedCornersForWidth("?x-oss-process=image/resize,lfit,w_{0},limit_1/auto-orient,0/sharpen,100/rounded-corners,r_{1}/quality,q_75/interlace,1/format,webp",
            "?imageMogr2/auto-orient/thumbnail/{0}x/format/webp/interlace/1/q/75/sharpen/1/ignore-error/1"),
    /**
     * 裁剪(矩形)
     */
    TailoringWHRectangular("?x-oss-process=image/crop,x_0,y_0,w_{0},h_{1},g_center/auto-orient,0/sharpen,100/quality,q_75/interlace,1/format,webp",
            "?imageMogr2/auto-orient/gravity/center/crop/{0}x{1}/format/web/interlace/1/q/75/sharpen/1/ignore-error/1"),
    /**
     * 裁剪(矩形圆角)
     */
    TailoringWHRectangularRoundedCorners("?x-oss-process=image/crop,x_0,y_0,w_{0},h_{1},g_center/auto-orient,0/sharpen,100/rounded-corners,r_{2}/quality,q_75/interlace,1/format,webp",
            "?imageMogr2/auto-orient/gravity/center/crop/{0}x{1}/format/webp/interlace/1/q/75/sharpen/1/ignore-error/1"),
    /**
     * 裁剪(圆形)
     */
    TailoringCircle("?x-oss-process=image/auto-orient,0/circle,r_{0}/sharpen,100/quality,q_75/interlace,1/format,webp",
            "?imageMogr2/auto-orient/gravity/center/crop/{0}x{0}/format/webp/interlace/1/q/75/sharpen/1/ignore-error/1"),
    /**
     * 自适应(圆形)
     */
    GeometricCircleForWidth("?x-oss-process=image/resize,lfit,w_{0},limit_1/circle,r_{1}/auto-orient,0/sharpen,100/quality,q_75/interlace,1/format,webp",
            "?imageMogr2/auto-orient/gravity/center/crop/{0}x{0}/format/webp/interlace/1/q/75/sharpen/1/ignore-error/1");

    private String aliRule = "";

    private String qiniuRule = "";

    private ImgRuleType(String aliRule, String qiniuRule) {
        this.aliRule = aliRule;
        this.qiniuRule = qiniuRule;
    }

    public String getRule() {
        String platform = RxCache.getCacheData("IMAGE_PLATFORM_KEY");
        if (TextUtils.equals(platform, APIPlatformType.Aliyun.name())) {
            return this.aliRule;
        } else if (TextUtils.equals(platform, APIPlatformType.Qiniu.name())) {
            return this.qiniuRule;
        } else {
            return "";
        }
    }
}
