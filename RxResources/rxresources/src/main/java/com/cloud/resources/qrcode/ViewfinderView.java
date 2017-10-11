/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cloud.resources.qrcode;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.cloud.resources.R;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder
 * rectangle and partial transparency outside it, as well as the laser scanner
 * animation and result points.
 * 
 */
public final class ViewfinderView extends View {
	/**
	 * 刷新界面的时间
	 */
	private final long ANIMATION_DELAY = 10L;
	private final int OPAQUE = 0xFF;

	/**
	 * 四个绿色边角对应的长度
	 */
	private int ScreenRate;

	/**
	 * 四个绿色边角对应的宽度
	 */
	public final int CORNER_WIDTH = 4;
	/**
	 * 扫描框中的中间线的宽度
	 */
	private final int MIDDLE_LINE_WIDTH = 6;

	/**
	 * 扫描框中的中间线的与扫描框左右的间隙
	 */
	private final int MIDDLE_LINE_PADDING = 5;

	/**
	 * 中间那条线每次刷新移动的距离
	 */
	private final int SPEEN_DISTANCE = 5;

	/**
	 * 手机的屏幕密度
	 */
	private float density;

	/**
	 * 画笔对象的引用
	 */
	private Paint paint;

	/**
	 * 中间滑动线的最顶端位置
	 */
	private int slideTop;

	public int rectWidth = 0;

	public int rectHeight = 0;

	private int rectpaddingsize = 120;

	/**
	 * 将扫描的二维码拍下来，这里没有这个功能，暂时不考虑
	 */
	private Bitmap resultBitmap;
	private final int maskColor;
	private final int resultColor;
	private boolean isFirst;
	private CameraManager mcmg = null;
	private Bitmap mlinebt = null;
	private ViewfinderListener mvflistener = null;
	private boolean isDrawed = false;

	public ViewfinderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mcmg = new CameraManager(context);
		density = context.getResources().getDisplayMetrics().density;
		// 将像素转换成dp
		ScreenRate = (int) (12 * density);
		paint = new Paint();
		Resources resources = getResources();
		// 设置遮罩层颜色
		maskColor = resources.getColor(R.color.viewfinder_mask);
		// 设置有效区域颜色
		resultColor = resources.getColor(R.color.result_view);
		mlinebt = ((BitmapDrawable) (resources
				.getDrawable(R.drawable.qrcode_line))).getBitmap();
	}

	public CameraManager getCameraManager() {
		return mcmg;
	}

	public void setOnViewfinderListener(ViewfinderListener listener) {
		this.mvflistener = listener;
	}

	@Override
	public void onDraw(Canvas canvas) {
		// 获取屏幕的宽和高
		rectWidth = canvas.getWidth();
		rectHeight = canvas.getHeight();
		// 中间的扫描框，你要修改扫描框的大小，去CameraManager里面修改
		Rect frame = mcmg.getFramingRect(CORNER_WIDTH, rectWidth, rectHeight);
		if (frame == null) {
			return;
		}
		// 初始化中间线滑动的最上边和最下边
		if (!isFirst) {
			isFirst = true;
			slideTop = frame.top + rectpaddingsize;
		}
		paint.setColor(resultBitmap != null ? resultColor : maskColor);
		// 画出扫描框外面的阴影部分，共四个部分，扫描框的上面到屏幕上面，扫描框的下面到屏幕下面
		// 扫描框的左边面到屏幕左边，扫描框的右边到屏幕右边
		// 上边
		canvas.drawRect(0, 0, rectWidth, frame.top + rectpaddingsize, paint);
		// 左边
		canvas.drawRect(0, frame.top + rectpaddingsize, frame.left,
				frame.bottom - rectpaddingsize, paint);
		// 右边
		canvas.drawRect(frame.right, frame.top + rectpaddingsize, rectWidth,
				frame.bottom - rectpaddingsize, paint);
		// 下边
		canvas.drawRect(0, frame.bottom - rectpaddingsize, rectWidth,
				rectHeight, paint);
		if (resultBitmap != null) {
			// Draw the opaque result bitmap over the scanning rectangle
			paint.setAlpha(OPAQUE);
			canvas.drawBitmap(resultBitmap, frame.left, frame.top
					+ rectpaddingsize, paint);
		} else {
			if (!isDrawed) {
				if (mvflistener != null) {
					mvflistener.onFirstCanvas(canvas, frame.top
							+ rectpaddingsize, paint);
				}
			}
			// 画扫描框边上的角，总共8个部分
			paint.setColor(Color.GREEN);
			// 左上角上边
			canvas.drawRect(frame.left, frame.top + rectpaddingsize, frame.left
					+ ScreenRate, frame.top + rectpaddingsize + CORNER_WIDTH,
					paint);
			// 左上角左边
			canvas.drawRect(frame.left, frame.top + rectpaddingsize, frame.left
					+ CORNER_WIDTH, frame.top + rectpaddingsize + ScreenRate,
					paint);
			// 右上角上边
			canvas.drawRect(frame.right - ScreenRate, frame.top
					+ rectpaddingsize, frame.right, frame.top + rectpaddingsize
					+ CORNER_WIDTH, paint);
			// 右上角右边
			canvas.drawRect(frame.right - CORNER_WIDTH, frame.top
					+ rectpaddingsize, frame.right, frame.top + rectpaddingsize
					+ ScreenRate, paint);
			// 左下角下边
			canvas.drawRect(frame.left, frame.bottom - rectpaddingsize
					- CORNER_WIDTH, frame.left + ScreenRate, frame.bottom
					- rectpaddingsize, paint);
			// 左下角左边
			canvas.drawRect(frame.left, frame.bottom - rectpaddingsize
					- ScreenRate, frame.left + CORNER_WIDTH, frame.bottom
					- rectpaddingsize, paint);
			// 右下角下边
			canvas.drawRect(frame.right - ScreenRate, frame.bottom
					- rectpaddingsize - CORNER_WIDTH, frame.right, frame.bottom
					- rectpaddingsize, paint);
			// 右下角右边
			canvas.drawRect(frame.right - CORNER_WIDTH, frame.bottom
					- rectpaddingsize - ScreenRate, frame.right, frame.bottom
					- rectpaddingsize, paint);
			// 绘制中间的线,每次刷新界面，中间的线往下移动SPEEN_DISTANCE
			slideTop += SPEEN_DISTANCE;
			if (slideTop >= (frame.bottom - rectpaddingsize)) {
				slideTop = (frame.top + rectpaddingsize);
			}
			// 绘制扫描线
			Rect lineRect = new Rect();
			lineRect.left = frame.left + MIDDLE_LINE_PADDING;
			lineRect.right = frame.right - MIDDLE_LINE_PADDING;
			lineRect.top = slideTop - MIDDLE_LINE_WIDTH / 2;
			lineRect.bottom = slideTop + MIDDLE_LINE_WIDTH / 2;
			canvas.drawBitmap(mlinebt, null, lineRect, paint);
			if (!isDrawed) {
				isDrawed = true;
				if (mvflistener != null) {
					mvflistener.onLastCanvas(canvas, frame.bottom
							- rectpaddingsize, paint);
				}
			}
			// 只刷新扫描框的内容，其他地方不刷新
			postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top
					+ rectpaddingsize, frame.right, frame.bottom
					- rectpaddingsize);
		}
	}

	public void drawViewfinder() {
		resultBitmap = null;
		invalidate();
	}

	/**
	 * Draw a bitmap with the result points highlighted instead of the live
	 * scanning display.
	 * 
	 * @param barcode
	 *            An image of the decoded barcode.
	 */
	public void drawResultBitmap(Bitmap barcode) {
		resultBitmap = barcode;
		invalidate();
	}
}
