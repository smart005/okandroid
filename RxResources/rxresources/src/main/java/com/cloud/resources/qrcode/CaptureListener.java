package com.cloud.resources.qrcode;

import android.graphics.Bitmap;

import com.google.zxing.Result;

/**
 * 
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-10-28 下午4:04:21
 * @Description:
 * @Modifier:
 * @ModifyContent:
 * 
 */
public interface CaptureListener {
	public void handleDecode(Result result, Bitmap barcode);
}
