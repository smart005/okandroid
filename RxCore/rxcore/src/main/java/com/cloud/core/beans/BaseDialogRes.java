package com.cloud.core.beans;

public class BaseDialogRes {

	/**
	 * 窗口动画效果 R.style.dialog_animation,0不设置动画效果
	 */
	public int animation;

	/**
	 * 除窗口标题区域内容区域外其它区域视图背景(包括按钮区域)
	 */
	public int dialogbackground;

	/**
	 * 按钮背景 R.drawable.btn_def_style
	 */
	public int buttonbackground;

	/**
	 * 按钮文本颜色
	 */
	public int buttonTextColor = 0;

	/**
	 * 分隔线背景颜色
	 */
	public int splitlinebackground = 0;

	/**
	 * 关闭按钮背景
	 */
	public int closebuttonbackground = 0;
}
