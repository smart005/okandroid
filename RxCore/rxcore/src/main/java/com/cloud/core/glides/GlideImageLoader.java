package com.cloud.core.glides;

import android.content.Context;

import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;
import com.cloud.core.enums.ImgRuleType;

import java.io.InputStream;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/11/17
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */

public class GlideImageLoader extends BaseGlideUrlLoader<IDataModel> {

    private ImgRuleType ruleType = ImgRuleType.None;
    private int imgWidth = 0;
    private int imgHeight = 0;
    private int imgCorners = 0;

    public GlideImageLoader(Context context, ImgRuleType ruleType, int imgWidth, int imgHeight, int imgCorners) {
        super(context);
        this.ruleType = ruleType;
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        this.imgCorners = imgCorners;
    }

    public GlideImageLoader(ModelLoader<GlideUrl, InputStream> urlLoader) {
        super(urlLoader, null);
    }

    @Override
    protected String getUrl(IDataModel model, int width, int height) {
        return model.buildDataModelUrl(ruleType, imgWidth, imgHeight, imgCorners);
    }

    public static class Factory implements ModelLoaderFactory<IDataModel, InputStream> {

        @Override
        public ModelLoader<IDataModel, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new GlideImageLoader(factories.buildModelLoader(GlideUrl.class, InputStream.class));
        }

        @Override
        public void teardown() {
        }
    }
}
