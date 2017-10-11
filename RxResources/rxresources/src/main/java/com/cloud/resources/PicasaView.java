package com.cloud.resources;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.cloud.core.ObjectJudge;
import com.cloud.core.enums.RuleParams;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.PixelUtils;
import com.cloud.core.utils.ValidUtils;
import com.cloud.core.viewer.PhotoView;
import com.cloud.core.viewer.PhotoViewAttacher;
import com.cloud.resources.beans.BaseImageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/6/13
 * @Description:大图查看视图
 * @Modifier:
 * @ModifyContent:
 */
public class PicasaView extends RelativeLayout {

    private List<BaseImageItem> imgUrls = new ArrayList<BaseImageItem>();
    private PicasaPagerAdapter curradapter = null;
    private int DOT_CONTAINER_ID = 549984850;
    private int VIEW_PAGE_ID = 2041055581;
    private int currentPosition = 0;
    private boolean isDisplayDotView = true;
    private OnPhotoViewClickListener onPhotoViewClickListener = null;

    public PicasaView(Context context) {
        super(context);
        init(true);
    }

    public PicasaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(false);
    }

    public PicasaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(false);
    }

    public interface OnPhotoViewClickListener {
        public void onPhotoViewClick();

        public void onOutsidePhotoTap();
    }

    public void setOnPhotoViewClickListener(OnPhotoViewClickListener listener) {
        this.onPhotoViewClickListener = listener;
    }

    public void setCurrentPosition(int position) {
        try {
            if (position < 0) {
                position = 0;
            }
            if (position > (imgUrls.size() - 1)) {
                position = imgUrls.size() - 1;
            }
            this.currentPosition = position;
            ViewPager viewPager = (ViewPager) findViewById(VIEW_PAGE_ID);
            viewPager.setCurrentItem(this.currentPosition);
        } catch (Exception e) {
            Logger.L.error("set picasa current position error:", e);
        }
    }

    public int getCurrentPosition() {
        return this.currentPosition;
    }

    public void setImgUrls(BaseImageItem[] urls) {
        try {
            if (ObjectJudge.isNullOrEmpty(urls)) {
                return;
            }
            LinearLayout dotcontainer = (LinearLayout) findViewById(DOT_CONTAINER_ID);
            for (BaseImageItem item : urls) {
                if (ValidUtils.valid(RuleParams.Url.getValue(), item.getUrl())) {
                    imgUrls.add(item);
                    ImageView dotiv = createDotView();
                    dotcontainer.addView(dotiv, dotiv.getLayoutParams());
                }
            }
            dotcontainer.setVisibility(isDisplayDotView ? View.VISIBLE : View.GONE);
        } catch (Exception e) {
            Logger.L.error("set img urls error:", e);
        }
    }

    public void setImgUrls(List<BaseImageItem> urls) {
        setImgUrls(urls.toArray(new BaseImageItem[0]));
    }

    public void appendUrl(BaseImageItem item) {
        try {
            if (ValidUtils.valid(RuleParams.Url.getValue(), item.getUrl())) {
                imgUrls.add(item);
                LinearLayout dotcontainer = (LinearLayout) findViewById(DOT_CONTAINER_ID);
                ImageView dotiv = createDotView();
                dotcontainer.addView(dotiv, dotiv.getLayoutParams());
                dotcontainer.setVisibility(isDisplayDotView ? View.VISIBLE : View.GONE);
            }
        } catch (Exception e) {
            Logger.L.error("append url error:", e);
        }
    }

    /**
     * 是否显示点视图
     *
     * @param flag
     */
    public void setDisplayDotView(boolean flag) {
        this.isDisplayDotView = flag;
    }

    private void init(boolean initflag) {
        if (initflag) {
            ViewGroup.LayoutParams vgparam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            this.setLayoutParams(vgparam);
        }
        this.setBackgroundColor(Color.BLACK);
        this.addView(buildViewPager());
        LayoutParams containerparam = new LayoutParams(LayoutParams.MATCH_PARENT, 30);
        containerparam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        containerparam.setMargins(0, 0, 0, PixelUtils.dip2px(getContext(), 60));
        LinearLayout dotcontainer = buildDotContainer();
        this.addView(dotcontainer, containerparam);
    }

    private LinearLayout buildDotContainer() {
        LinearLayout container = new LinearLayout(getContext());
        container.setId(DOT_CONTAINER_ID);
        container.setOrientation(LinearLayout.HORIZONTAL);
        container.setGravity(Gravity.CENTER);
        return container;
    }

    private ImageView createDotView() {
        LinearLayout.LayoutParams ivparam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ivparam.setMargins(0, 0, PixelUtils.dip2px(getContext(), 6), 0);
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(ivparam);
        imageView.setImageResource(R.drawable.dot_normal);
        return imageView;
    }

    private ViewPager buildViewPager() {
        LayoutParams vpparam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        ViewPager viewPager = new ViewPager(getContext());
        viewPager.setLayoutParams(vpparam);
        viewPager.setId(VIEW_PAGE_ID);
        curradapter = new PicasaPagerAdapter();
        viewPager.setAdapter(curradapter);
        viewPager.addOnPageChangeListener(vpagelistener);
        return viewPager;
    }

    private OnPageChangeListener vpagelistener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            try {
                currentPosition = position;
                LinearLayout dotcontainer = (LinearLayout) findViewById(DOT_CONTAINER_ID);
                for (int i = 0; i < imgUrls.size(); i++) {
                    ImageView imageView = (ImageView) dotcontainer.getChildAt(i);
                    if (i == position) {
                        imageView.setImageResource(R.drawable.dot_press);
                    } else {
                        imageView.setImageResource(R.drawable.dot_normal);
                    }
                }
            } catch (Exception e) {
                Logger.L.error("picasa page selected error:", e);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private class PicasaPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imgUrls.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            BaseImageItem item = imgUrls.get(position);
            PhotoView photoView = new PhotoView(container.getContext());
            photoView.setAllowParentInterceptOnEdge(true);
            final PhotoViewAttacher attacher = new PhotoViewAttacher(photoView);
            attacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    if (onPhotoViewClickListener != null) {
                        onPhotoViewClickListener.onPhotoViewClick();
                    }
                }

                @Override
                public void onOutsidePhotoTap() {
                    if (onPhotoViewClickListener != null) {
                        onPhotoViewClickListener.onOutsidePhotoTap();
                    }
                }
            });
            container.addView(photoView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            GlideProcess.load(getContext(), Uri.parse(item.getUrl()), new GlideDrawableImageViewTarget(photoView) {
                @Override
                public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                    super.onResourceReady(drawable, anim);
                    attacher.update();
                }
            });
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    /**
     * 懒加载
     */
    public void lazyLoad() {
        View dotcontainer = findViewById(DOT_CONTAINER_ID);
        dotcontainer.setVisibility(View.GONE);
        curradapter.notifyDataSetChanged();
    }
}
