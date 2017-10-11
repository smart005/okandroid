package com.cloud.resources.arcs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;

import com.cloud.resources.R;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/8/3
 * @Description:A Layout that arranges its children around its center. The arc can be set by
 * calling {@link #setArc(float, float) setArc()}. You can override the method
 * {@link #onMeasure(int, int) onMeasure()}, otherwise it is always
 * WRAP_CONTENT.
 * @Modifier:
 * @ModifyContent:
 */
public class ArcLayout extends ViewGroup {
    /**
     * children will be set the same size.
     */
    private int mChildSize;

    private int mChildPadding = 5;

    private int mLayoutPadding = 10;

    public static final float DEFAULT_FROM_DEGREES = 270.0f;

    public static final float DEFAULT_TO_DEGREES = 360.0f;

    private float mFromDegrees = DEFAULT_FROM_DEGREES;

    private float mToDegrees = DEFAULT_TO_DEGREES;

    /* the distance between the layout's center and any child's center */
    private int mRadius = 160;

    private boolean mExpanded = false;

    public ArcLayout(Context context) {
        super(context);
    }

    public ArcLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, com.cloud.resources.R.styleable.ArcLayout, 0, 0);
            mFromDegrees = a.getFloat(com.cloud.resources.R.styleable.ArcLayout_fromDegrees, DEFAULT_FROM_DEGREES);
            mToDegrees = a.getFloat(com.cloud.resources.R.styleable.ArcLayout_toDegrees, DEFAULT_TO_DEGREES);
            mRadius = a.getDimensionPixelSize(R.styleable.ArcLayout_arcRadius, 160);
            mChildSize = a.getDimensionPixelSize(com.cloud.resources.R.styleable.ArcLayout_childSize, 44);
            a.recycle();
        }
    }

    public void setRadius(int radius) {
        this.mRadius = radius;
    }

    private static Rect computeChildFrame(int centerX, int centerY, int radius, float degrees, int size) {
        double childCenterX = centerX + radius * Math.cos(Math.toRadians(degrees));
        double childCenterY = centerY + radius * Math.sin(Math.toRadians(degrees));
        return new Rect((int) (childCenterX - size / 2), (int) (childCenterY - size / 2), (int) (childCenterX + size / 2), (int) (childCenterY + size / 2));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = mRadius * 2 + mChildSize + mChildPadding + mLayoutPadding * 2;
        setMeasuredDimension(size, size);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            getChildAt(i).measure(MeasureSpec.makeMeasureSpec(mChildSize, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(mChildSize, MeasureSpec.EXACTLY));
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = mExpanded ? mRadius : 0;
        int childCount = getChildCount();
        float perDegrees = (mToDegrees - mFromDegrees) / (childCount - 1);
        float degrees = mFromDegrees;
        for (int i = 0; i < childCount; i++) {
            Rect frame = computeChildFrame(centerX, centerY, radius, degrees, mChildSize);
            degrees += perDegrees;
            getChildAt(i).layout(frame.left, frame.top, frame.right, frame.bottom);
        }
    }

    /**
     * refers to {@link LayoutAnimationController#getDelayForView(View view)}
     */
    private static long computeStartOffset(int childCount, boolean expanded, int index, float delayPercent, long duration, Interpolator interpolator) {
        float delay = delayPercent * duration;
        long viewDelay = (long) (getTransformedIndex(expanded, childCount, index) * delay);
        float totalDelay = delay * childCount;
        float normalizedDelay = viewDelay / totalDelay;
        normalizedDelay = interpolator.getInterpolation(normalizedDelay);
        return (long) (normalizedDelay * totalDelay);
    }

    private static int getTransformedIndex(boolean expanded, int count, final int index) {
        if (expanded) {
            return count - 1 - index;
        }
        return index;
    }

    private static Animation createExpandAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta, long startOffset, long duration, Interpolator interpolator) {
        Animation animation = new com.cloud.resources.arcs.RotateAndTranslateAnimation(0, toXDelta, 0, toYDelta, 0, 720);
        animation.setStartOffset(startOffset);
        animation.setDuration(duration);
        animation.setInterpolator(interpolator);
        animation.setFillAfter(true);
        return animation;
    }

    private static Animation createShrinkAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta, long startOffset, long duration, Interpolator interpolator) {
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.setFillAfter(true);
        final long preDuration = duration / 2;
        Animation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setStartOffset(startOffset);
        rotateAnimation.setDuration(preDuration);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setFillAfter(true);
        animationSet.addAnimation(rotateAnimation);
        Animation translateAnimation = new RotateAndTranslateAnimation(0, toXDelta, 0, toYDelta, 360, 720);
        translateAnimation.setStartOffset(startOffset + preDuration);
        translateAnimation.setDuration(duration - preDuration);
        translateAnimation.setInterpolator(interpolator);
        translateAnimation.setFillAfter(true);
        animationSet.addAnimation(translateAnimation);
        return animationSet;
    }

    private void bindChildAnimation(View child, int index, long duration) {
        boolean expanded = mExpanded;
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = expanded ? 0 : mRadius;
        int childCount = getChildCount();
        float perDegrees = (mToDegrees - mFromDegrees) / (childCount - 1);
        Rect frame = computeChildFrame(centerX, centerY, radius, mFromDegrees + index * perDegrees, mChildSize);
        int toXDelta = frame.left - child.getLeft();
        int toYDelta = frame.top - child.getTop();
        Interpolator interpolator = mExpanded ? new AccelerateInterpolator() : new OvershootInterpolator(1.5f);
        long startOffset = computeStartOffset(childCount, mExpanded, index, 0.1f, duration, interpolator);
        Animation animation = mExpanded ? createShrinkAnimation(0, toXDelta, 0, toYDelta, startOffset, duration, interpolator) : createExpandAnimation(0, toXDelta, 0, toYDelta, startOffset, duration, interpolator);
        final boolean isLast = getTransformedIndex(expanded, childCount, index) == childCount - 1;
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isLast) {
                    postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            onAllAnimationsEnd();
                        }
                    }, 0);
                }
            }
        });
        child.setAnimation(animation);
    }

    public boolean isExpanded() {
        return mExpanded;
    }

    public void setArc(float fromDegrees, float toDegrees) {
        if (mFromDegrees == fromDegrees && mToDegrees == toDegrees) {
            return;
        }
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        requestLayout();
    }

    public void setChildSize(int size) {
        if (mChildSize == size || size < 0) {
            return;
        }
        mChildSize = size;
        requestLayout();
    }

    public int getChildSize() {
        return mChildSize;
    }

    /**
     * switch between expansion and shrinkage
     *
     * @param showAnimation
     */
    public void switchState(final boolean showAnimation) {
        if (showAnimation) {
            final int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                bindChildAnimation(getChildAt(i), i, 300);
            }
        }
        mExpanded = !mExpanded;
        if (!showAnimation) {
            requestLayout();
        }
        invalidate();
    }

    private void onAllAnimationsEnd() {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).clearAnimation();
        }
        requestLayout();
    }
}
