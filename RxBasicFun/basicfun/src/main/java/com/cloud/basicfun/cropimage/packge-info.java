package com.cloud.basicfun.cropimage;
/**
 * 添加权限
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
 * 注册页面
 * <activity
 * android:name=".cropimage.CropImageActivity"
 * android:launchMode="singleTop"
 * android:screenOrientation="portrait"/>
 *
 * 初始化裁剪
 * Crop.pickImage(this);
 * 裁剪回调
 *
 * @Override protected void onActivityResult(int requestCode, int resultCode, Intent result) {
 * if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
 * beginCrop(result.getData());
 * } else if (requestCode == Crop.REQUEST_CROP) {
 * handleCrop(resultCode, result);
 * }
 * }
 * private void beginCrop(Uri source) {
 * Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
 * Crop.of(source, destination).asSquare().start(this);
 * }
 * private void handleCrop(int resultCode, Intent result) {
 * if (resultCode == RESULT_OK) {
 * resultView.setImageURI(Crop.getOutput(result));
 * } else if (resultCode == Crop.RESULT_ERROR) {
 * Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
 * }
 * }
 */
