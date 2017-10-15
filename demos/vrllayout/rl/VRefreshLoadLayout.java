package com.geek.mibao.widgets.rl;

import android.content.Context;
import android.util.AttributeSet;

import com.cloud.core.enums.DateFormatEnum;
import com.cloud.core.utils.DateUtils;
import com.cloud.resources.xlistview.OnXListViewListener;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/15
 * @Description:刷新加载视图控件;支持:listview recyclerview gridview scrollview view
 * @Modifier:
 * @ModifyContent:
 */
public class VRefreshLoadLayout extends RefreshLoadMoreLayout implements CallBack {

    private Config config = null;
    private OnXListViewListener onXListViewListener = null;

    public VRefreshLoadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        config = new Config(this);
        config.showLastRefreshTime(DateUtils.getDateTime(DateFormatEnum.YYYYMMDDHHMMSS));
    }

    //是否启用刷新
    public void canRefresh(boolean canRefresh) {
        if (config == null) {
            return;
        }
        config.canRefresh(canRefresh);
    }

    //启用加载更多
    public void canLoadMore(boolean canLoadMore) {
        if (config == null) {
            return;
        }
        config.canLoadMore(canLoadMore);
    }

    public void initializa() {
        if (config == null) {
            return;
        }
        super.init(config);
    }

    public void setOnXListViewListener(OnXListViewListener listener) {
        this.onXListViewListener = listener;
    }

    @Override
    public void onRefresh() {
        if (onXListViewListener != null) {
            onXListViewListener.onRefresh();
        }
        super.stopRefresh();
    }

    @Override
    public void onLoadMore() {
        if (onXListViewListener != null) {
            onXListViewListener.onLoadMore();
        }
        super.stopLoadMore();
    }

    public void refresh() {
        this.onRefresh();
    }
}
