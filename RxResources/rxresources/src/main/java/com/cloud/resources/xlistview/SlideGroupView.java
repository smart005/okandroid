package com.cloud.resources.xlistview;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * 
 * @Author LiJingHuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-6-8 上午9:30:58
 * @Description:构建边栏操作项
 * @Modifier:
 * @ModifyContent:
 * 
 */
public class SlideGroupView extends LinearLayout {

	public SlideGroupView(Context context, int width) {
		super(context);
		LayoutParams lparam = new LayoutParams(
				width > 60 ? width : 60, LayoutParams.MATCH_PARENT);
		setLayoutParams(lparam);
		setOrientation(LinearLayout.HORIZONTAL);
	}
}
