package com.geek.mibao.widgets.rl;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;

/**
 * Created by Bond on 2017/03/08 17:02
 * you can contact me at qbaowei@qq.com
 */

public class RlmViewPager extends ViewPager {

    private float mXDownPos;
    private float mYDownPos;
    private float mXDistance;
    private float mYDistance;
    private boolean mFirstMove;
    private boolean mShouldDeal;

    public RlmViewPager(Context context) {
        super(context);
    }

    public RlmViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                requestParentDisallowInterceptTouchEvent(true);
                mXDownPos = ev.getRawX();
                mYDownPos = ev.getRawY();
                mFirstMove = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mFirstMove) {
                    if (mShouldDeal) {
                        requestParentDisallowInterceptTouchEvent(true);
                        return super.onTouchEvent(ev);
                    } else {
                        requestParentDisallowInterceptTouchEvent(false);
                        return false;
                    }
                }

                mXDistance = Math.abs(ev.getRawX() - mXDownPos);
                mYDistance = Math.abs(ev.getRawY() - mYDownPos);
                mFirstMove = false || mXDistance == 0 || mYDistance == 0;
                mShouldDeal = mXDistance > mYDistance;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                requestParentDisallowInterceptTouchEvent(false);
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void requestParentDisallowInterceptTouchEvent(boolean b) {
        ViewParent vp = getParent();
        if (vp != null) {
            vp.requestDisallowInterceptTouchEvent(b);
        }
    }
}
