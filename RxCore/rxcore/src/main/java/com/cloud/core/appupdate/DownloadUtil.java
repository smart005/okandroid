package com.cloud.core.appupdate;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cloud.core.beans.ApkInfo;
import com.cloud.core.beans.BaseDialogRes;
import com.cloud.core.beans.CmdItem;
import com.cloud.core.beans.InstanceUpdateServiceInfoEntity;
import com.cloud.core.dialog.BaseDialog;
import com.cloud.core.enums.DialogButtonsEnum;
import com.cloud.core.utils.PixelUtils;

public class DownloadUtil {

	public class ContextUtil {

		private Context context;

		public Context getContext() {
			return this.context;
		}

		public void setContext(Context context) {
			this.context = context;
		}
	}

	public abstract class VersionInfoDialog {

		private BaseDialog basedg;

		private ApkInfo cappinfo;

		private InstanceUpdateServiceInfoEntity miusinfo = new InstanceUpdateServiceInfoEntity();

		private BaseDialogRes dgres;

		private String title;

		/**
		 * 是否强制更新
		 */
		private boolean iscompulsoryupdate;

		protected abstract void updateSubmitListener(String actionid,
				ApkInfo appinfo);

		protected abstract void laterListener();

		/**
		 * @param 设置miusinfo
		 */
		public void setMiusinfo(InstanceUpdateServiceInfoEntity miusinfo) {
			this.miusinfo = miusinfo;
		}

		public void setDialogRes(BaseDialogRes dgres) {
			this.dgres = dgres;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public void setAppInfo(ApkInfo cappinfo) {
			this.cappinfo = cappinfo;
		}

		/**
		 * 
		 * @param iscompulsory
		 *            true:强制更新;
		 */
		public void setCompulsoryUpdate(boolean iscompulsory) {
			this.iscompulsoryupdate = iscompulsory;
		}

		/**
		 * 是否强制更新
		 * 
		 * @return
		 * @return boolean
		 */
		public boolean getIsCompulsoryUpdate() {
			return this.iscompulsoryupdate;
		}

		public boolean isShowing() {
			if (basedg == null) {
				return false;
			} else {
				return basedg.isShowing();
			}
		}

		public void dismiss() {
			if (basedg != null) {
				basedg.dismiss();
			}
		}

		public void show(Context context) {
			if (basedg == null) {
				basedg = new BaseDialog(context, false,
						DialogButtonsEnum.Custom,
						miusinfo.getMaskLoadingTheme());
			}
			if (iscompulsoryupdate) {
				CmdItem[] cmds = { new CmdItem("nowupdate",
						"\u7acb\u5373\u66f4\u65b0") };
				basedg.setButtons(cmds);
			} else {
				CmdItem[] cmds = {
						new CmdItem("laterupdate", "\u7a0d\u540e\u66f4\u65b0"),
						new CmdItem("nowupdate", "\u7acb\u5373\u66f4\u65b0") };
				basedg.setButtons(cmds);
			}
			basedg.setResource(this.dgres);
			basedg.setOnCustomClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String flag = v.getTag().toString();
					if (TextUtils.equals(flag, "laterupdate")) {
						laterListener();
						if (basedg.isShowing()) {
							basedg.dismiss();
						}
					} else if (TextUtils.equals(flag, "nowupdate")) {
						updateSubmitListener(flag, cappinfo);
						if (basedg.isShowing()) {
							basedg.dismiss();
						}
					}
				}
			});
			basedg.setShowTitle(true);
			basedg.setShowClose(false);
			basedg.setContentGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
			if (!basedg.isShowing()) {
				basedg.show(this.title, this.cappinfo.getUpdateDescription());
			}
		}
	}

	public abstract class NoWifiUpdateTipDialog {

		private BaseDialog basedg;

		private String title;

		private ApkInfo cappinfo;

		private InstanceUpdateServiceInfoEntity miusinfo = new InstanceUpdateServiceInfoEntity();

		private BaseDialogRes dgres;

		public void setTitle(String title) {
			this.title = title;
		}

		/**
		 * @param 设置miusinfo
		 */
		public void setMiusinfo(InstanceUpdateServiceInfoEntity miusinfo) {
			this.miusinfo = miusinfo;
		}

		public void setDialogRes(BaseDialogRes dgres) {
			this.dgres = dgres;
		}

		public void setAppInfo(ApkInfo cappinfo) {
			this.cappinfo = cappinfo;
		}

		protected abstract void yesSubmitListener(ApkInfo appinfo);

		protected abstract void noSubmitListener(ApkInfo appinfo);

		public void show(Context context) {
			basedg = new BaseDialog(context, false, DialogButtonsEnum.YesNo,
					miusinfo.getMaskLoadingTheme());
			basedg.setResource(this.dgres);
			basedg.setOnNoClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					noSubmitListener(cappinfo);
					if (basedg.isShowing()) {
						basedg.dismiss();
					}
				}
			});
			basedg.setOnYesClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					yesSubmitListener(cappinfo);
					if (basedg.isShowing()) {
						basedg.dismiss();
					}
				}
			});
			basedg.setShowTitle(true);
			basedg.setShowClose(false);
			basedg.show(
					this.title,
					"\u5f53\u524d\u7f51\u7edc\u975e\u0077\u0069\u0066\u0069\u72b6\u6001\u002c\u662f\u5426\u4ecd\u7ee7\u7eed\u66f4\u65b0\u003f");
		}
	}

	public abstract class DownloadAPKDialog {

		private BaseDialog basedg;

		private String title;

		private ApkInfo cappinfo;

		private InstanceUpdateServiceInfoEntity miusinfo = new InstanceUpdateServiceInfoEntity();

		private BaseDialogRes dgres;

		private int textresid = 50440726;

		private int pbarlayoutid;

		public void setTitle(String title) {
			this.title = title;
		}

		/**
		 * @param 设置miusinfo
		 */
		public void setMiusinfo(InstanceUpdateServiceInfoEntity miusinfo) {
			this.miusinfo = miusinfo;
		}

		public void setDialogRes(BaseDialogRes dgres) {
			this.dgres = dgres;
		}

		public void setPBarLayoutId(int resid) {
			this.pbarlayoutid = resid;
		}

		public void setAppInfo(ApkInfo cappinfo) {
			this.cappinfo = cappinfo;
		}

		protected abstract void initListener(ProgressBar pbar, int textresid);

		protected abstract void downloadStartListener(ApkInfo appinfo);

		protected abstract void cancelDownloadListener(ApkInfo appinfo);

		public void show(Context context) {
			basedg = new BaseDialog(context, false, DialogButtonsEnum.Custom,
					miusinfo.getMaskLoadingTheme());
			basedg.setResource(this.dgres);
			CmdItem cancelcmd = new CmdItem("ddapkcancel", "\u53d6\u6d88");
			cancelcmd.setEnable(false);
			CmdItem[] cmds = { cancelcmd };
			basedg.setButtons(cmds);
			basedg.setOnCustomClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String flag = v.getTag().toString();
					if (TextUtils.equals(flag, "ddapkcancel")) {
						cancelDownloadListener(cappinfo);
						if (basedg.isShowing()) {
							basedg.dismiss();
						}
					}
				}
			});
			basedg.setShowTitle(true);
			basedg.setShowClose(false);
			basedg.show(this.title, createContentView(context));
			downloadStartListener(cappinfo);
		}

		private LinearLayout createContentView(Context context) {
			LinearLayout.LayoutParams cvllparam = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			LinearLayout cvll = new LinearLayout(context);
			cvll.setLayoutParams(cvllparam);
			cvll.setPadding(PixelUtils.dip2px(context, 6),
					PixelUtils.dip2px(context, 4),
					PixelUtils.dip2px(context, 6),
					PixelUtils.dip2px(context, 4));
			cvll.setOrientation(LinearLayout.VERTICAL);
			TextView apknametv = createText(context);
			cvll.addView(apknametv);
			ProgressBar pbar = (ProgressBar) LayoutInflater.from(context)
					.inflate(pbarlayoutid, null);
			LinearLayout.LayoutParams pbarparam = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT, PixelUtils.dip2px(
							context, 8));
			cvll.addView(pbar, pbarparam);
			initListener(pbar, textresid);
			return cvll;
		}

		private TextView createText(Context context) {
			LinearLayout.LayoutParams tvparam = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			TextView tv = new TextView(context);
			tv.setLayoutParams(tvparam);
			tv.setId(textresid);
			tv.setPadding(0, PixelUtils.dip2px(context, 6), 0,
					PixelUtils.dip2px(context, 6));
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
			tv.setTextColor(Color.rgb(31, 31, 31));
			tv.setGravity(Gravity.CENTER_VERTICAL);
			tv.setText(this.cappinfo.getApkName());

			return tv;
		}

		public View findViewById(int resid) {
			return basedg.findViewById(resid);
		}

		public boolean isShowing() {
			return basedg.isShowing();
		}

		public void dismiss() {
			basedg.dismiss();
		}
	}
}
