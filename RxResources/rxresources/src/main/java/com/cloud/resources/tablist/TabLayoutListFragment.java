package com.cloud.resources.tablist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cloud.core.utils.GlobalUtils;
import com.cloud.core.utils.HandlerUtils;
import com.cloud.resources.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/3/10
 * @Description:tab list fragment
 * @Modifier:
 * @ModifyContent:
 */
public class TabLayoutListFragment extends Fragment {

    //是否需要加载数据
    private boolean isNeedLoad = true;
    //是否已创建视图
    private boolean isCreatedView = false;
    private Runnable loadrunnable = null;
    private MirrorAdapter mirrorAdapter = null;
    private int preItemHeight = 90;
    private int mirrorCount = 0;
    private ListView tabLayoutListLv = null;
    private HandlerUtils handlerUtils = new HandlerUtils();
    private int currPosition = 0;
    private int selectPosition = 0;
    private RelativeLayout tabLayoutContentRl = null;

    protected View onCreateUserView() {
        return null;
    }

    protected void onLoadData() {

    }

    public void setCurrPosition(int position) {
        this.currPosition = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = View.inflate(getContext(), R.layout.tab_layout_list_view, null);
        View cusView = onCreateUserView();
        if (cusView != null) {
            tabLayoutContentRl = (RelativeLayout) contentView.findViewById(R.id.tab_layout_content_rl);
            if (tabLayoutContentRl.getChildCount() > 1) {
                for (int i = 1; i < tabLayoutContentRl.getChildCount(); i++) {
                    tabLayoutContentRl.removeViewAt(i);
                }
            }
            cusView.setVisibility(View.GONE);
            tabLayoutContentRl.addView(cusView);
        }
        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.isCreatedView = true;
        loadrunnable = new Runnable() {
            @Override
            public void run() {
                if (currPosition == selectPosition) {
                    onLoadData();
                }
            }
        };
        if (isCreatedView && isNeedLoad) {
            handlerUtils.postDelayed(loadrunnable, 500);
        }
        initMirrorData(view);
    }

    private void initMirrorData(View contentView) {
        int sheight = GlobalUtils.getScreenHeight(getContext());
        mirrorCount = sheight / preItemHeight + 6;
        mirrorAdapter = new MirrorAdapter();
        tabLayoutListLv = (ListView) contentView.findViewById(R.id.tab_layout_list_lv);
        tabLayoutListLv.setAdapter(mirrorAdapter);
        tabLayoutListLv.setVisibility(View.VISIBLE);
    }

    public void setSmoothLoad(boolean isSmoothLoad) {
        if (isSmoothLoad) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventListSelectedItem(EventPageSelecteItem flagEvent) {
        if (TextUtils.equals(flagEvent.getKey(), "TAB_LAYOUT_LIST_PAGE_SELECTED_FLAG")) {
            selectPosition = flagEvent.getPosition();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventListHorScrolling(String flag) {
        if (TextUtils.equals(flag, "TAB_LAYOUT_LIST_PAGE_SCROLLING_FLAG")) {
            isNeedLoad = false;
            handlerUtils.removeMessages(loadrunnable);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventSmoothLoadList(String flag) {
        if (TextUtils.equals(flag, "TAB_LAYOUT_LIST_PAGE_SCROLL_FINISHED_FLAG")) {
            isNeedLoad = true;
            if (isCreatedView && isNeedLoad) {
                handlerUtils.postDelayed(loadrunnable, 500);
            }
        }
    }

    private class MirrorAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mirrorCount;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.mirror_item_view, null);
            }
            return convertView;
        }
    }

    /**
     * 数据加载完成后调用此方法隐藏监听列表
     */
    public void hideMirrorList() {
        mirrorCount = 0;
        mirrorAdapter.notifyDataSetChanged();
        tabLayoutListLv.setVisibility(View.GONE);
        if (tabLayoutContentRl != null && tabLayoutContentRl.getChildCount() > 1) {
            for (int i = 1; i < tabLayoutContentRl.getChildCount(); i++) {
                tabLayoutContentRl.getChildAt(i).setVisibility(View.VISIBLE);
            }
        }
    }
}
