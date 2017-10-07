package com.cloud.core.loadings;

import android.content.Context;
import android.view.ViewGroup;

import com.cloud.core.beans.LoadingRes;
import com.cloud.core.enums.Direction;
import com.cloud.core.enums.LoadingType;
import com.cloud.core.enums.MaskAlign;

public class BaseLoading {

	/**
	 * 遮罩加载
	 */
	private MaskLoading mloading;

	/**
	 * 一般用于标题栏
	 */
	private SmallLoading msmalloading;

	private int theme;

	private LoadingRes lres;

	public BaseLoading() {

	}

	/**
	 * LoadingType类型为MASK_LOADING时用此构造函数
	 * 
	 * @param theme
	 *            mask loading theme
	 */
	public BaseLoading(int theme) {
		this.theme = theme;
	}

	/**
	 * 设置loading组件资源;目前LoadingType类型为MASK_LOADING时需设置此资源,其它可不设置;
	 * 
	 * @param lres
	 */
	public void setLoadingResource(LoadingRes lres) {
		this.lres = lres;
	}

	/**
	 * show loading
	 * 
	 * @param context
	 * @param type
	 *            LoadingType类型;
	 * @param smallloadingcontainer
	 *            LoadingType类型为SMALL_LOADING时需输入承载的容器,否则可为null;
	 * @param tipinfo
	 *            LoadingType类型为MASK_LOADING时需输入的提示内容,否则可为空;
	 * @param mlalign
	 *            LoadingType类型为MASK_LOADING时需输入相应的检举类型,否则输入MaskLoadingAlign.
	 *            DEFAULT;
	 * @param hasloading
	 *            LoadingType类型为MASK_LOADING时有效,否则此变量不起作用;
	 */
	public void show(Context context, LoadingType type,
			ViewGroup smallloadingcontainer, String tipinfo, MaskAlign mlalign,
			boolean hasloading, Direction loadingalign) {
		if (type == LoadingType.MASK_LOADING) {
			if (mloading == null) {
				mloading = new MaskLoading(context, theme);
			}
			mloading.setResource(this.lres);
			mloading.show(tipinfo, mlalign, hasloading, loadingalign);
		} else if (type == LoadingType.SMALL_LOADING) {
			if (msmalloading == null) {
				msmalloading = new SmallLoading();
			}
			msmalloading.show(context, smallloadingcontainer);
		}
	}

	public void showMaskLoading(Context context, String tipinfo,
			MaskAlign mlalign, boolean hasloading, Direction loadingalign) {
		this.show(context, LoadingType.MASK_LOADING, null, tipinfo, mlalign,
				hasloading, loadingalign);
	}

	public void showSmallLoading(Context context,
			ViewGroup smallloadingcontainer, Direction loadingalign) {
		this.show(context, LoadingType.SMALL_LOADING, smallloadingcontainer,
				"", MaskAlign.DEFAULT, false, loadingalign);
	}

	/**
	 * 销毁或禁用 loading 视图
	 * 
	 * @param smallloadingcontainer
	 *            LoadingType类型为SMALL_LOADING时需输入承载的容器,否则可为null;
	 */
	public void dismiss(LoadingType type, ViewGroup smallloadingcontainer) {
		if (type == LoadingType.MASK_LOADING) {
			if (mloading != null) {
				mloading.dismiss();
			}
		} else if (type == LoadingType.SMALL_LOADING) {
			if (msmalloading != null) {
				msmalloading.dismiss(smallloadingcontainer);
			}
		}
	}

	public void dismissMaskLoading() {
		this.dismiss(LoadingType.MASK_LOADING, null);
	}

	public void dismissSmallLoading(ViewGroup smallloadingcontainer) {
		this.dismiss(LoadingType.SMALL_LOADING, smallloadingcontainer);
	}
}
