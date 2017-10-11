package com.cloud.resources.qrcode;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-10-29 下午4:20:21
 * @Description:
 * @Modifier:
 * @ModifyContent:
 * 
 */
public interface ViewfinderListener {
	/**
	 * 
	 * @param canvas
	 * @param slideTopY
	 *            扫码框上边界y轴坐标
	 * @param paint
	 */
	public void onFirstCanvas(Canvas canvas, int slideTopY, Paint paint);

	/**
	 * 
	 * @param canvas
	 * @param slideBottomY
	 *            扫码框下边界y轴坐标
	 * @param paint
	 */
	public void onLastCanvas(Canvas canvas, int slideBottomY, Paint paint);
}
