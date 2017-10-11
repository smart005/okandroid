/**
 * 
 */
package com.cloud.resources.xlistview;

/**
 * 
 * @Author LIJINGHUAN
 * @Email:ljh0576123@163.com
 * @CreateTime:2016年3月7日 下午1:07:25
 * @Description:
 * @Modifier:
 * @ModifyContent:
 *
 */
public interface OnXListViewScrollListener {
	public void onLastVisibleItem(int lastposition);

	public void onScrollPosition(int position);
}
