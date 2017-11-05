package com.cloud.resources.parallax;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;

import com.cloud.core.logger.Logger;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/11
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class BaseAdjustScrollFragment extends Fragment implements TabHolderScrollingContent {

    private int mPosition = 0;
    private HostView mHostView = null;
    private int mScrollY = 0;
    protected LinearLayoutManager linearLayoutManager;
    private Activity activity = null;
    private Fragment parentFragment = null;

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onAttachToContext(context);
        } catch (ClassCastException e) {
            Logger.L.error(e);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            if (Build.VERSION.SDK_INT < 23) {
                onAttachToContext(activity);
            }
        } catch (ClassCastException e) {
            Logger.L.error(e);
        }
    }

    public void setParentActivity(Activity activity) {
        this.activity = activity;
    }

    public void setParentFragment(Fragment fragment) {
        this.parentFragment = fragment;
    }

    protected void onAttachToContext(Context context) {
        if (activity != null) {
            mHostView = (HostView) activity;
        } else if (parentFragment != null) {
            mHostView = (HostView) parentFragment;
        }
    }

    @Override
    public void onDetach() {
        mHostView = null;
        super.onDetach();
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            if (arguments.containsKey("POSITION")) {
                mPosition = arguments.getInt("POSITION");
            }
        }
        linearLayoutManager = new LinearLayoutManager(getActivity());
    }

    @Override
    public void adjustScroll(int scrollTranslation) {
        if (linearLayoutManager == null) {
            return;
        }
        mScrollY = -scrollTranslation;
        linearLayoutManager.scrollToPositionWithOffset(0, scrollTranslation);
    }

    protected RecyclerView.OnScrollListener buildRecyclerViewOnScrollListener() {
        RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mScrollY += dy;
                if (mHostView != null) {
                    mHostView.onScrollingContentScroll(mScrollY, mPosition);
                }
            }
        };
        return scrollListener;
    }

    protected ScrollView.OnScrollChangeListener buildScrollViewOnScrollChangeListener() {
        View.OnScrollChangeListener changeListener = new ScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int x, int y, int oldX, int oldY) {
                mScrollY += (y - oldY);
                if (mHostView != null) {
                    mHostView.onScrollingContentScroll(mScrollY, mPosition);
                }
            }
        };
        return changeListener;
    }

    protected View.OnScrollChangeListener buildViewOnScrollChangeListener() {
        View.OnScrollChangeListener changeListener = new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                mScrollY += (scrollY - oldScrollY);
                if (mHostView != null) {
                    mHostView.onScrollingContentScroll(mScrollY, mPosition);
                }
            }
        };
        return changeListener;
    }
}
