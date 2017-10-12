package com.cloud.basicfun.shuffling;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.cloud.resources.GlideProcess;
import com.youth.banner.loader.ImageLoader;


public class FrescoImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, final ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Uri uri = Uri.parse(String.valueOf(path));
        GlideProcess.load(context, uri, new GlideDrawableImageViewTarget(imageView) {
            @Override
            public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                super.onResourceReady(drawable, anim);
                imageView.invalidate();
            }
        });
    }
}
