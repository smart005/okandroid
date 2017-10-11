package com.cloud.resources;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.cloud.core.enums.ImgRuleType;
import com.cloud.core.glides.FormatDataModel;
import com.cloud.core.logger.Logger;

import java.io.File;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/6/13
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class GlideProcess {

    static class GlideTransProperties {
        public Context context = null;
        public String url = "";
        public Uri uri = null;
        public File file = null;
        public int defImg = 0;
        public int crossFade = 0;
        public ImageView imageView = null;
        public Target target = null;
        public int width = 0;
        public int height = 0;
        public int corners = 0;
        public int preLoadWidth = 0;
        public int preLoadHeight = 0;
        public ImgRuleType ruleType = ImgRuleType.None;
    }

    private static DrawableTypeRequest getRequestManager(Context context, ImgRuleType ruleType, String url, int imgWidth, int imgHeight, int imgCorners) {
        return Glide.with(context)
                .load(FormatDataModel.getUrl(url, ruleType, imgWidth, imgHeight, imgCorners));
    }

    private static DrawableRequestBuilder loadConfig(DrawableTypeRequest request, int preLoadWidth, int preLoadHeight, int defImg, int crossFade) {
        //内存缓存
        DrawableRequestBuilder drawableRequestBuilder = request.skipMemoryCache(false);
        if (defImg != 0) {
            //默认背景(下载之前显示图片)
            drawableRequestBuilder.placeholder(defImg);
            //图片加载失败也显示默认图片
            drawableRequestBuilder.error(defImg);
        }
        if (crossFade > 0) {
            //淡入淡出动画效果,300为默认持续时间
            drawableRequestBuilder.crossFade(crossFade);
        }
        //图片显示模式
        drawableRequestBuilder.fitCenter()
                //没有动画效果
                //.dontAnimate()
                .thumbnail(0.1f);
        if (preLoadWidth > 0 && preLoadHeight > 0) {
            drawableRequestBuilder.preload(preLoadWidth, preLoadHeight);
        }
        drawableRequestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE);
        drawableRequestBuilder.fitCenter();
        return drawableRequestBuilder;
    }

    private static DrawableRequestBuilder loadConfig(DrawableTypeRequest request, int defImg) {
        return loadConfig(request, 42, 42, defImg, 300);
    }

    private static DrawableRequestBuilder loadConfig(DrawableTypeRequest request) {
        return loadConfig(request, R.drawable.def_bg);
    }

    public static void load(Context context, ImgRuleType ruleType, String url, int defImg, int imgWidth, int imgHeight, int imgCorners, ImageView imageView) {
        GlideTransProperties transProperties = new GlideTransProperties();
        transProperties.context = context;
        transProperties.url = url;
        transProperties.defImg = defImg;
        transProperties.imageView = imageView;
        transProperties.ruleType = ruleType;
        transProperties.width = imgWidth;
        transProperties.height = imgHeight;
        transProperties.corners = imgCorners;
        loadImageUrlProcess(transProperties);
    }

    public static void load(Context context, ImgRuleType ruleType, String url, int imgWidth, int imgHeight, int imgCorners, ImageView imageView) {
        load(context, ruleType, url, R.drawable.def_bg, imgWidth, imgHeight, imgCorners, imageView);
    }

    public static void load(Context context, File file, int defImg, ImageView imageView) {
        GlideTransProperties transProperties = new GlideTransProperties();
        transProperties.context = context;
        transProperties.file = file;
        transProperties.defImg = defImg;
        transProperties.imageView = imageView;
        loadImageFileTargetProcess(transProperties);
    }

    public static void load(Context context, File file, ImageView imageView) {
        load(context, file, R.drawable.def_bg, imageView);
    }

    public static void load(Context context, Uri uri, Target target) {
        GlideTransProperties transProperties = new GlideTransProperties();
        transProperties.context = context;
        transProperties.uri = uri;
        transProperties.defImg = R.drawable.def_bg;
        transProperties.crossFade = 300;
        transProperties.target = target;
        loadImageUriTargetProcess(transProperties);
    }

    private static void loadImageUrlProcess(GlideTransProperties transProperties) {
        try {
            loadConfig(getRequestManager(transProperties.context, transProperties.ruleType, transProperties.url, transProperties.width, transProperties.height, transProperties.corners), transProperties.defImg).into(transProperties.imageView);
        } catch (Exception e) {
            Logger.L.error("load image process error:", e);
        }
    }

    private static void loadImageFileTargetProcess(GlideTransProperties transProperties) {
        try {
            loadConfig(Glide.with(transProperties.context).load(transProperties.file), 42, 42, transProperties.defImg, 300).into(transProperties.imageView);
        } catch (Exception e) {
            Logger.L.error("load image process error:", e);
        }
    }

    private static void loadImageUriTargetProcess(GlideTransProperties transProperties) {
        try {
            loadConfig(Glide.with(transProperties.context).load(transProperties.uri), transProperties.preLoadWidth, transProperties.preLoadHeight, R.drawable.def_bg, transProperties.crossFade).into(transProperties.target);
        } catch (Exception e) {
            Logger.L.error("load image process error:", e);
        }
    }
}
