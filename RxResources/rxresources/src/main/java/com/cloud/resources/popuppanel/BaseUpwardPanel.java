package com.cloud.resources.popuppanel;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow.OnDismissListener;

import com.cloud.core.beans.BaseUpwardDialogParams;
import com.cloud.core.dialog.BaseUpwardDialog;
import com.cloud.core.enums.UpwardDialogContentType;

import com.cloud.resources.R;

/**
 * 
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-6-19 下午5:52:43
 * @Description: 上推面板组件
 * @Modifier:
 * @ModifyContent:
 * 
 */
public class BaseUpwardPanel {

	private boolean iscanceloutlayout = false;
	private int layoutResid = 0;
	private View contentView = null;
	/**
	 * 面板背景颜色值 (兼容部分机型)默认为透明
	 */
	private int panelBackgroundColor = 0;

	private BaseUpwardDialogParams muparams = new BaseUpwardDialogParams();

	protected void onContentView(Context context, View contentView) {

	}

	public void setContentView(int layoutResid) {
		this.layoutResid = layoutResid;
	}

	public void setContentView(View contentView) {
		this.contentView = contentView;
	}

	/**
	 * @param 关闭面板监听
	 */
	public void onClose() {

	}

	/**
	 * @param 设置面板背景颜色值
	 *            (兼容部分机型)默认为透明
	 */
	public void setPanelBackgroundColor(int panelBackgroundColor) {
		this.panelBackgroundColor = panelBackgroundColor;
	}

	private BaseUpwardDialog mudialog = new BaseUpwardDialog() {
		@Override
		protected void onBuildContentView(Context context, View contentView) {
			onContentView(context, contentView);
		}
	};

	public void show(Context context, View parent) {
		muparams.setContext(context);
		muparams.setContenttype(UpwardDialogContentType.Panel.ordinal());
		muparams.setCancelOutLayout(iscanceloutlayout);
		muparams.setLayoutResid(layoutResid);
		muparams.setContentView(contentView);
		muparams.setAnimBottom(R.style.AnimBottom);
		muparams.setPanelBackgroundColor(panelBackgroundColor);
		mudialog.setBaseUpwardDialogParams(muparams);
		mudialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				onClose();
			}
		});
		mudialog.show(parent);
	}

	public void dismiss() {
		if (mudialog != null && mudialog.isShowing()) {
			mudialog.dismiss();
		}
	}

	public boolean isShowing() {
		return mudialog.isShowing();
	}
}
