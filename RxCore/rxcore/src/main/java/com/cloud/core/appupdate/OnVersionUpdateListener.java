package com.cloud.core.appupdate;

import com.cloud.core.beans.ApkInfo;
import com.cloud.core.loadings.MaskLoading;

public interface OnVersionUpdateListener {

	/**
	 * 版本更新
	 * 
	 * @param appinfo
	 *            更新应用信息
	 */
	public void versionUpdate(ApkInfo appinfo, boolean isCompulsoryUpdate);

	/**
	 * 最后版本
	 */
	public void lasterVersion();

	/**
	 * 更新完成
	 * 
	 * @return void
	 */
	public void updateComplated();

	/**
	 * 稍候更新
	 * 
	 * @return void
	 */
	public void laterUpdateListener();

	/**
	 * 立即更新
	 */
	public void nowUpdateListener(ApkInfo appinfo, boolean isCompulsoryUpdate);

	/**
	 * 初始化加载对象
	 * 
	 * @return
	 */
	public MaskLoading onInitLoadingObject();

	/**
	 * 开始加载提示监听
	 * 
	 * @param text
	 */
	public void onBeginLoadingPromptListener(String text);

	/**
	 * 结束加载提示监听
	 */
	public void onEndLoadingPromptListener();
}