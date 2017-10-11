package com.cloud.resources.xlistview;

import android.content.Context;
import android.graphics.Color;

/**
 * 
 * @Author LiJingHuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-6-5 上午6:43:18
 * @Description:在Adapter getView构建之前初始化该实体
 * @Modifier:
 * @ModifyContent:
 * 
 */
public class XListInstanceEntity<T> {
	private Context context = null;
	/**
	 * 项布局id
	 */
	private int layoutItemId = 0;
	/**
	 * 视图ViewHolder
	 */
	private T holder = null;
	/**
	 * 项操作区宽度(默认为60)
	 */
	private int holderWidth = 60;
	/**
	 * 项操作区背景色(默认白色)
	 */
	private int holderBackgroudColor = Color.WHITE;

	/**
	 * 是否启用边栏
	 */
	private boolean mEnableSliding = false;

	/**
	 * 是否启用项视图单击处理
	 */
	private boolean enableItemViewClick = false;

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public int getLayoutItemId() {
		return layoutItemId;
	}

	public void setLayoutItemId(int layoutItemId) {
		this.layoutItemId = layoutItemId;
	}

	public T getHolder() {
		return holder;
	}

	public void setHolder(T holder) {
		this.holder = holder;
	}

	public int getHolderWidth() {
		return holderWidth;
	}

	public void setHolderWidth(int holderWidth) {
		this.holderWidth = holderWidth;
	}

	public int getHolderBackgroudColor() {
		return holderBackgroudColor;
	}

	public void setHolderBackgroudColor(int holderBackgroudColor) {
		this.holderBackgroudColor = holderBackgroudColor;
	}

	public boolean isEnableSliding() {
		return mEnableSliding;
	}

	public void setEnableSliding(boolean mEnableSliding) {
		this.mEnableSliding = mEnableSliding;
	}

	/**
	 * @return 获取是否启用项视图单击处理
	 */
	public boolean isEnableItemViewClick() {
		return enableItemViewClick;
	}

	/**
	 * @param 设置是否启用项视图单击处理
	 */
	public void setEnableItemViewClick(boolean enableItemViewClick) {
		this.enableItemViewClick = enableItemViewClick;
	}
}
