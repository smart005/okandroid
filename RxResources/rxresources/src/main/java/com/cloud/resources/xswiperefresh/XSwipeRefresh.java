package com.cloud.resources.xswiperefresh;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.AttributeSet;
import android.view.View;

/**
 * 
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016-1-30 下午3:07:06
 * @Description:
 * @Modifier:
 * @ModifyContent:
 * 
 */
public class XSwipeRefresh extends SwipeRefreshLayout implements
		OnRefreshListener {

	private OnSwipeRefreshListener mOnSwipeRefreshListener = null;

	public XSwipeRefresh(Context context) {
		super(context);
		init(true);
	}

	public XSwipeRefresh(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(false);
	}

	/**
	 * @param 设置mOnSwipeRefreshListener
	 */
	public void setOnSwipeRefreshListener(OnSwipeRefreshListener listener) {
		this.mOnSwipeRefreshListener = listener;
	}

	/**
	 * @param 设置contentView
	 */
	public void setContentView(View contentView) {
		if (contentView == null) {
			return;
		}
		this.removeAllViews();
		this.addView(contentView);
	}

	private void init(boolean isInstance) {
		if (isInstance) {
			LayoutParams vgparam = new LayoutParams(
					LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			this.setLayoutParams(vgparam);
		}
		this.setOnRefreshListener(this);
		this.setColorSchemeResources(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
	}

	@Override
	public void onRefresh() {
		if (mOnSwipeRefreshListener != null) {
			mOnSwipeRefreshListener.onRefresh();
		}
	}
}
