/*
 * Copyright (C) 2012 Capricorn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cloud.resources.arcs;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cloud.core.utils.PixelUtils;
import com.cloud.resources.R;

/**
 * A custom view that looks like the menu in <a href="https://path.com">Path
 * 2.0</a> (for iOS).
 *
 * @author Capricorn
 */
public class ArcMenu extends RelativeLayout {
    private ArcLayout mArcLayout;

    private ImageView mHintView;
    private int vertexBackgroundSelector = 0;
    private int vertexHintImageResourceSelector = 0;
    private int vertexSize = 0;
    private ViewGroup controlLayout = null;
    private OnArcVertexClickListener onArcVertexClickListener = null;

    public ArcMenu(Context context) {
        super(context);
        init(context);
    }

    public ArcMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        applyAttrs(attrs);
    }

    public void setVertexBackgroundSelector(int resid) {
        this.vertexBackgroundSelector = resid;
        if (resid != 0) {
            controlLayout.setBackgroundResource(resid);
        }
    }

    public void setVertexHintImageResourceSelector(int resid) {
        this.vertexHintImageResourceSelector = resid;
        if (resid != 0) {
            mHintView.setImageResource(resid);
        }
    }

    /**
     * 设置顶点大小
     *
     * @param typedValue TypedValue.COMPLEX_UNIT_DIP
     * @param vertexSize 顶点大小
     */
    public void setVertexSize(int typedValue, int vertexSize) {
        if (vertexSize != 0) {
            int size = LayoutParams.WRAP_CONTENT;
            if (typedValue == TypedValue.COMPLEX_UNIT_DIP) {
                size = PixelUtils.dip2px(getContext(), vertexSize);
            }
            LayoutParams clparam = new LayoutParams(size, size);
            clparam.addRule(RelativeLayout.CENTER_IN_PARENT);
            controlLayout.setLayoutParams(clparam);
        }
    }

    public void setRadius(int radius) {
        mArcLayout.setRadius(radius);
    }

    public void setOnArcVertexClickListener(OnArcVertexClickListener listener) {
        this.onArcVertexClickListener = listener;
    }

    private void init(Context context) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(R.layout.arc_menu, this);
        mArcLayout = (ArcLayout) findViewById(com.cloud.resources.R.id.item_layout);
        controlLayout = (ViewGroup) findViewById(com.cloud.resources.R.id.control_layout);
        controlLayout.setClickable(true);
        controlLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mHintView.startAnimation(createHintSwitchAnimation(mArcLayout.isExpanded()));
                    mArcLayout.switchState(true);
                }
                return false;
            }
        });
        mHintView = (ImageView) findViewById(com.cloud.resources.R.id.control_hint);
    }

    private void applyAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, com.cloud.resources.R.styleable.ArcLayout, 0, 0);
            float fromDegrees = a.getFloat(com.cloud.resources.R.styleable.ArcLayout_fromDegrees, ArcLayout.DEFAULT_FROM_DEGREES);
            float toDegrees = a.getFloat(com.cloud.resources.R.styleable.ArcLayout_toDegrees, ArcLayout.DEFAULT_TO_DEGREES);
            mArcLayout.setArc(fromDegrees, toDegrees);
            int defaultChildSize = mArcLayout.getChildSize();
            int newChildSize = a.getDimensionPixelSize(com.cloud.resources.R.styleable.ArcLayout_childSize, defaultChildSize);
            mArcLayout.setChildSize(newChildSize);
            vertexBackgroundSelector = a.getInt(com.cloud.resources.R.styleable.ArcMenu_vertexBackgroundSelector, 0);
            if (vertexBackgroundSelector != 0) {
                mArcLayout.setBackgroundResource(vertexBackgroundSelector);
            }
            vertexHintImageResourceSelector = a.getInt(com.cloud.resources.R.styleable.ArcMenu_vertexHintImageResourceSelector, 0);
            if (vertexHintImageResourceSelector != 0) {
                mHintView.setImageResource(vertexHintImageResourceSelector);
            }
            a.recycle();
        }
    }

    public void addItem(View item, OnClickListener listener) {
        mArcLayout.addView(item);
        item.setOnClickListener(getItemClickListener(listener));
    }

    private OnClickListener getItemClickListener(final OnClickListener listener) {
        return new OnClickListener() {

            @Override
            public void onClick(final View viewClicked) {
                Animation animation = bindItemAnimation(viewClicked, true, 400);
                animation.setAnimationListener(new AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                itemDidDisappear();
                            }
                        }, 0);
                    }
                });

                final int itemCount = mArcLayout.getChildCount();
                for (int i = 0; i < itemCount; i++) {
                    View item = mArcLayout.getChildAt(i);
                    if (viewClicked != item) {
                        bindItemAnimation(item, false, 300);
                    }
                }

                mArcLayout.invalidate();
                mHintView.startAnimation(createHintSwitchAnimation(true));

                if (listener != null) {
                    listener.onClick(viewClicked);
                }
            }
        };
    }

    private Animation bindItemAnimation(final View child, final boolean isClicked, final long duration) {
        Animation animation = createItemDisapperAnimation(duration, isClicked);
        child.setAnimation(animation);

        return animation;
    }

    private void itemDidDisappear() {
        final int itemCount = mArcLayout.getChildCount();
        for (int i = 0; i < itemCount; i++) {
            View item = mArcLayout.getChildAt(i);
            item.clearAnimation();
        }

        mArcLayout.switchState(false);
    }

    private Animation createItemDisapperAnimation(final long duration, final boolean isClicked) {
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new ScaleAnimation(1.0f, isClicked ? 2.0f : 0.0f, 1.0f, isClicked ? 2.0f : 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f));
        animationSet.addAnimation(new AlphaAnimation(1.0f, 0.0f));

        animationSet.setDuration(duration);
        animationSet.setInterpolator(new DecelerateInterpolator());
        animationSet.setFillAfter(true);

        return animationSet;
    }

    private Animation createHintSwitchAnimation(final boolean expanded) {
        Animation animation = new RotateAnimation(expanded ? 45 : 0, expanded ? 0 : 45, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setStartOffset(0);
        animation.setDuration(100);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setFillAfter(true);
        animation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (onArcVertexClickListener != null) {
                    onArcVertexClickListener.onArcVertexClick(mArcLayout.isExpanded());
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return animation;
    }
}
