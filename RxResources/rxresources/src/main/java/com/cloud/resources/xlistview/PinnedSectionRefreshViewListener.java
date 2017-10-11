package com.cloud.resources.xlistview;

import android.view.View;
import android.widget.AdapterView;

/**
 * 
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016-1-20 上午10:07:38
 * @Description:
 * @Modifier:
 * @ModifyContent:
 * 
 */
public interface PinnedSectionRefreshViewListener {
	public void onRefresh();

	public void onLoadMore();

	public void onScrollListener(int position);

	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id);
}
