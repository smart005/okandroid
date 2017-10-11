package com.cloud.resources.qrcode;

import com.google.zxing.Result;

/**
 * 
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-10-28 下午4:22:17
 * @Description:
 * @Modifier:
 * @ModifyContent:
 * 
 */
public interface DecodeListener {
	public void onDecodeSucceeded(Result result, PlanarYUVLuminanceSource source);

	public void onDecodeFailed();
}
