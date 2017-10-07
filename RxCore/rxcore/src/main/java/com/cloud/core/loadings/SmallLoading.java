package com.cloud.core.loadings;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloud.core.CycleTextExecutor;
import com.cloud.core.utils.PixelUtils;

public class SmallLoading {

	/**
	 * small loading layout
	 */
	private View mviewll;

	/**
	 * small loading layout id
	 */
	private int sllayoutid = 2109759400;

	/**
	 * small loading remind text id
	 */
	private int slremindtextid = 1903131740;

	private String deftext = "\u52AA\u529B\u52A0\u8F7D\u4E2D";

	private String cycletext = "...";

	/**
	 * show small loading
	 * 
	 * @param context
	 * @param container
	 *            small loading container
	 */
	public void show(Context context, ViewGroup container) {
		if (mviewll != null) {
			mviewll.setVisibility(View.VISIBLE);
			mctexecutor.start(deftext, cycletext);
		} else {
			mviewll = container.findViewById(sllayoutid);
			if (mviewll == null) {
				mviewll = new SmallLoadingLayout(context);
				mviewll.setVisibility(View.VISIBLE);
				container.addView(mviewll);
				mctexecutor.start(deftext, cycletext);
			} else {
				mviewll.setVisibility(View.VISIBLE);
				mctexecutor.start(deftext, cycletext);
			}
		}
	}

	/**
	 * dismiss small loading
	 * 
	 * @param container
	 *            small loading container
	 */
	public void dismiss(ViewGroup container) {
		if (mviewll != null) {
			mviewll.setVisibility(View.GONE);
			mctexecutor.stop();
		} else {
			mviewll = container.findViewById(sllayoutid);
			if (mviewll != null) {
				mviewll.setVisibility(View.GONE);
				mctexecutor.stop();
			}
		}
	}

	private class SmallLoadingLayout extends LinearLayout {

		public SmallLoadingLayout(Context context) {
			super(context);
			RelativeLayout.LayoutParams mainparam = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			mainparam.addRule(RelativeLayout.CENTER_IN_PARENT);
			this.setOrientation(HORIZONTAL);
			this.setVisibility(View.GONE);
			this.setLayoutParams(mainparam);
			this.setId(sllayoutid);
			this.setGravity(Gravity.CENTER);
			this.addView(createProgressBar(context));
			this.addView(createTextView(context, deftext + cycletext));
		}

		private ProgressBar createProgressBar(Context context) {
			LayoutParams barparam = new LayoutParams(
					PixelUtils.dip2px(context, 16), PixelUtils.dip2px(context,
							16));
			ProgressBar pbar = new ProgressBar(context);
			pbar.setLayoutParams(barparam);
			return pbar;
		}

		private TextView createTextView(Context context, String text) {
			LayoutParams tvparam = new LayoutParams(
					LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			tvparam.gravity = Gravity.CENTER_VERTICAL;
			TextView tv = new TextView(context);
			tv.setLayoutParams(tvparam);
			tv.setId(slremindtextid);
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			tv.setTextColor(Color.rgb(50, 50, 50));
			tv.setText(text);
			return tv;
		}
	}

	private CycleTextExecutor mctexecutor = new CycleTextExecutor() {
		@Override
		protected void onDoingExecutor(String deftext, String cycletext) {
			if (mviewll != null) {
				TextView tv = (TextView) mviewll.findViewById(slremindtextid);
				if (tv != null) {
					tv.setText(deftext + cycletext);
				}
			}
		}
	};
}
