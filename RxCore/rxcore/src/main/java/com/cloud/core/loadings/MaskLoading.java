package com.cloud.core.loadings;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cloud.core.beans.LoadingRes;
import com.cloud.core.enums.Direction;
import com.cloud.core.enums.MaskAlign;
import com.cloud.core.utils.PixelUtils;

public class MaskLoading extends Dialog {

	private LoadingRes dgres;

	private Context context;

	private MaskAlign mlalign;

	private boolean hasloading;

	private String tipinfo;

	private boolean isautodismiss;

	/**
	 * text resid
	 */
	private int loadingtextid = 817703711;

	/**
	 * window layout id
	 */
	private int panellayoutid = 1809843320;

	private int progressbarid = 1285352154;

	private int contentviewid = 832700335;

	/**
	 * 当前视图是否正在显示中
	 */
	private boolean isshowing;

	public MaskLoading(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	public void setResource(LoadingRes dgres) {
		this.dgres = dgres;
		if (dgres != null && dgres.animation != 0) {
			getWindow().setWindowAnimations(dgres.animation);
		}
	}

	/**
	 * show mask loading window
	 * 
	 * @param tipinfo
	 *            提示信息
	 * @param mlalign
	 *            提示窗口对齐方式
	 * @param hasloading
	 *            has container progressbar
	 * @param loadingalign
	 *            loading align: TOP LEFT BUTTOM RIGHT
	 */
	public void show(String tipinfo, MaskAlign mlalign, boolean hasloading,
			Direction loadingalign) {
		try {
			this.tipinfo = tipinfo;
			this.mlalign = mlalign;
			this.hasloading = hasloading;
			if (isshowing) {
				TextView tv = (TextView) findViewById(loadingtextid);
				if (tv == null) {
					setContentView(new MaskLoadingLayout(context, loadingalign));
					super.show();
				} else {
					tv.setText(this.tipinfo);
					tv = null;
					LinearLayout dglayout = (LinearLayout) findViewById(panellayoutid);
					if (mlalign == MaskAlign.CENTER) {
						dglayout.setGravity(Gravity.CENTER);
						dglayout.notify();
					} else if (mlalign == MaskAlign.BOTTOM) {
						dglayout.setGravity(Gravity.BOTTOM
								| Gravity.CENTER_HORIZONTAL);
						dglayout.setPadding(0, 0, 0,
								PixelUtils.dip2px(context, 80));
						dglayout.notify();
					}
					dglayout = null;
					ProgressBar pbar = (ProgressBar) findViewById(progressbarid);
					if (hasloading) {
						if (pbar == null) {
							LinearLayout cvll = (LinearLayout) findViewById(contentviewid);
							cvll.addView(createProgressBar(context), 0);
							cvll.notify();
							cvll = null;
						} else {
							pbar.setVisibility(View.VISIBLE);
						}
					} else {
						if (pbar != null) {
							pbar.setVisibility(View.GONE);
						}
					}
					pbar = null;
				}
			} else {
				isshowing = true;
				setContentView(new MaskLoadingLayout(context, loadingalign));
				super.show();
			}
			if (isautodismiss) {
				isautodismiss = false;
			}
		} catch (Exception e) {
			// show mask loading error
		}
	}

	@Override
	public void onBackPressed() {
		isshowing = false;
		super.onBackPressed();
	}

	public void dismissMaskLoading() {
		if (super.isShowing()) {
			super.dismiss();
		}
		isshowing = false;
	}

	public void hideMaskLoading() {
		if (super.isShowing()) {
			super.hide();
		}
		isshowing = false;
	}

	private int getMinWidth() {
		int minw = PixelUtils.dip2px(context, 200);
		DisplayMetrics dm = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int dw = dm.widthPixels * 2 / 3;
		if (dw <= minw) {
			dw = minw;
		}
		return dw;
	}

	private int getMinHeight() {
		int minh = PixelUtils.dip2px(context, 48);
		return minh;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return false;
	}

	private class MaskLoadingLayout extends LinearLayout {

		public MaskLoadingLayout(Context context, Direction loadingalign) {
			super(context);
			LayoutParams mainparam = new LayoutParams(
					LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			if (mlalign == MaskAlign.CENTER) {
				this.setGravity(Gravity.CENTER);
			} else if (mlalign == MaskAlign.BOTTOM) {
				this.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
				setPadding(0, 0, 0, PixelUtils.dip2px(context, 80));
			}
			this.setOrientation(VERTICAL);
			this.setLayoutParams(mainparam);
			this.setId(panellayoutid);
			int minw = getMinWidth();
			addView(createContentView(minw, loadingalign));
		}

		private LinearLayout createContentView(int minw, Direction loadingalign) {
			LayoutParams cvparam = new LayoutParams(
					LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			LinearLayout cvll = new LinearLayout(context);
			cvll.setId(contentviewid);
			cvll.setLayoutParams(cvparam);
			cvll.setMinimumWidth(minw);
			cvll.setMinimumHeight(getMinHeight());
			if (dgres != null && dgres.maskbackground != 0) {
				cvll.setBackgroundResource(dgres.maskbackground);
			}
			cvll.setPadding(PixelUtils.dip2px(context, 8),
					PixelUtils.dip2px(context, 6),
					PixelUtils.dip2px(context, 4),
					PixelUtils.dip2px(context, 6));
			if (hasloading) {
				if (loadingalign == Direction.TOP
						|| loadingalign == Direction.BUTTOM) {
					cvll.setOrientation(VERTICAL);
				} else if (loadingalign == Direction.LEFT
						|| loadingalign == Direction.RIGHT) {
					cvll.setOrientation(HORIZONTAL);
				}
			} else {
				cvll.setOrientation(HORIZONTAL);
			}
			cvll.setGravity(Gravity.CENTER);
			Drawable drb = cvll.getBackground();
			if (drb != null) {
				drb.setAlpha(120);
			}
			cvll.addView(createTipText());
			if (hasloading) {
				if (loadingalign == Direction.TOP
						|| loadingalign == Direction.LEFT) {
					cvll.addView(createProgressBar(context), 0);
				} else if (loadingalign == Direction.BUTTOM
						|| loadingalign == Direction.RIGHT) {
					cvll.addView(createProgressBar(context));
				}
			}
			return cvll;
		}

		private TextView createTipText() {
			LayoutParams tvparam = new LayoutParams(
					LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			TextView tv = new TextView(context);
			tv.setId(loadingtextid);
			tv.setLayoutParams(tvparam);
			tv.setTextColor(Color.rgb(255, 255, 255));
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
			tv.setSingleLine(false);
			tv.setText(tipinfo);
			return tv;
		}
	}

	private ProgressBar createProgressBar(Context context) {
		LinearLayout.LayoutParams barparam = new LinearLayout.LayoutParams(
				PixelUtils.dip2px(context, 20), PixelUtils.dip2px(context, 20));
		barparam.setMargins(0, 0, PixelUtils.dip2px(context, 8), 0);
		ProgressBar pbar = new ProgressBar(context);
		pbar.setLayoutParams(barparam);
		pbar.setId(progressbarid);
		return pbar;
	}

	/**
	 * 设置n秒后loading自动销毁
	 * 
	 * @param seconds
	 *            <=0时不作处理
	 */
	public void setAutoDismiss(int seconds) {
		if (seconds > 0) {
			this.isautodismiss = true;
		}
	}
}
