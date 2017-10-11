/**
 * @file XListViewHeader.java
 * @create Apr 18, 2012 5:22:27 PM
 * @author Maxwin
 * @description XListView's header
 */
package com.cloud.resources.xlistview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cloud.core.enums.DateFormatEnum;
import com.cloud.core.utils.DateUtils;

import com.cloud.resources.R;

public class XListViewHeader extends LinearLayout {
    private View mContainer;
    private ImageView mProgressView;
    private ProgressBar mProgressBar = null;
    private TextView mHintTextView;
    private TextView mTimeTextView;
    private int mState = STATE_NORMAL;

    private AnimationDrawable mProgressAnim;

    // 下拉刷新动画的图片数量
    private final int PULL_DOWN_PIC_NUMBER = 10;

    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_REFRESHING = 2;

    private boolean isStartAnim = false;

    public XListViewHeader(Context context) {
        super(context);
        initView(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public XListViewHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        // 初始情况，设置下拉刷新view高度为0
        LayoutParams lp = new LayoutParams(
                LayoutParams.MATCH_PARENT, 0);
        mContainer = LayoutInflater.from(context).inflate(
                R.layout.xlistview_header, null);
        addView(mContainer, lp);
        setGravity(Gravity.BOTTOM);

        mHintTextView = (TextView) findViewById(R.id.xlistview_header_hint_textview);
        mProgressView = (ImageView) findViewById(R.id.xlistview_header_iv);
        mProgressBar = (ProgressBar) findViewById(R.id.xlistview_header_progressbar);
        mTimeTextView = (TextView) findViewById(R.id.xlistview_header_time);

        Drawable manim = context.getResources().getDrawable(R.anim.frame_loading);
        mProgressAnim = (AnimationDrawable) manim;
        mProgressView.setBackgroundDrawable(mProgressAnim);
        mProgressView.post(new Runnable() {
            @Override
            public void run() {
                mProgressAnim.start();
            }
        });
    }

    public void setState(int state) {
        if (state == mState)
            return;
        if (state == STATE_REFRESHING) { // 显示进度
            clearAnimation();
            mProgressView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        }
        mTimeTextView.setVisibility(View.VISIBLE);
        switch (state) {
            case STATE_NORMAL:
                if (mState == STATE_READY) {
                    isStartAnim = true;
                }
                if (mState == STATE_REFRESHING) {
                    clearAnimation();
                    isStartAnim = false;
                }
                mHintTextView.setText(R.string.xlistview_header_hint_normal);
                break;
            case STATE_READY:
                if (mState != STATE_READY) {
                    clearAnimation();
                    isStartAnim = true;
                    mHintTextView.setText(R.string.xlistview_header_hint_ready);
                }
                break;
            case STATE_REFRESHING:
                mHintTextView.setText(R.string.xlistview_header_hint_loading);
                break;
            default:
        }
        mTimeTextView.setText(DateUtils
                .getDateTime(DateFormatEnum.YYYYMMDDHHMMSS));
        mState = state;
    }

    public int getState() {
        return mState;
    }

    public void setVisiableHeight(int height) {
        if (height < 0)
            height = 0;
        LayoutParams lp = (LayoutParams) mContainer
                .getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    public int getVisiableHeight() {
        return mContainer.getLayoutParams().height;
    }

    public void clearAnimation() {
        mProgressView.setBackgroundResource(R.drawable.loading_01);
    }

    public void setPullRefreshAnim(int position) {
        if (isStartAnim) {
            // 当下拉刷新头全部显示时(高度要大于100)才要出现动画效果
            int pic = (position - 100) / PULL_DOWN_PIC_NUMBER;
            if (pic < 0) {
                mProgressView.setBackgroundResource(R.drawable.loading_01);
            } else if (pic < PULL_DOWN_PIC_NUMBER) {
                Drawable drawable = getResources().getDrawable(
                        (int) (R.drawable.loading_01 + pic));
                mProgressView.setBackgroundDrawable(drawable);
            } else {
                mProgressView.setBackgroundResource(R.drawable.loading_12);
            }
        }
    }

}
