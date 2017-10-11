/**
 * 
 */
package com.cloud.resources.xlistview;

import android.view.View;
import android.widget.AdapterView;

/**
 * 
 * @Author LIJINGHUAN
 * @Email:ljh0576123@163.com
 * @CreateTime:2016年3月7日 下午1:09:06
 * @Description:
 * @Modifier:
 * @ModifyContent:
 *
 */
public interface OnXListViewItemClickListener {
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id);
}
