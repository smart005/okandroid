package com.cloud.core.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;

import com.cloud.core.ObjectJudge;

public class VibrationUtils {
	/**
	 * 振动提示
	 * 
	 * @param activity
	 *            当前活动Activity实例
	 * @param milliseconds
	 *            震动的时长，单位是毫秒
	 */
	public static void VibrationTip(Activity activity, long milliseconds) {
		if (activity == null) {
			return;
		}
		Vibrator vib = (Vibrator) activity
				.getSystemService(Context.VIBRATOR_SERVICE);
		vib.vibrate(milliseconds);
	}

	/**
	 * 振动提示
	 * 
	 * @param activity
	 *            当前活动Activity实例
	 * @param pattern
	 *            自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长]时长的单位是毫秒
	 * @param isRepeat
	 *            是否反复震动，如果是true，反复震动，如果是false，只震动一次
	 */
	public static void VibrationTip(Activity activity, long[] pattern,
			Boolean isRepeat) {
		if (activity == null || ObjectJudge.isNullOrEmpty(pattern)) {
			return;
		}
		Vibrator vib = (Vibrator) activity
				.getSystemService(Context.VIBRATOR_SERVICE);
		vib.vibrate(pattern, isRepeat ? 1 : -1);
	}
}
