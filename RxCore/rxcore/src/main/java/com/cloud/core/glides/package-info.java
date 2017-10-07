/**
 *
 *   //可根据显示大小自动请求指定尺寸图片
 *   CustomImageSizeModel customImageRequest = new CustomImageSizeModelFutureStudio("图片url");
 *   Glide.with(当前对象(如Context))
 *   //只为这个请求用这个 model，一般情况不加;
 *   //.using(new CustomImageSizeUrlLoader(当前对象(如Context)))
 *   //加载图片
 *   .load(customImageRequest)
 *   //内存缓存
 *   .skipMemoryCache(false)
 *   //默认背景(下载之前显示图片)
 *   .placeholder(R.drawable.def_bg)
 *   //图片加载失败也显示默认图片
 *   .error(R.drawable.def_bg)
 *   //图片显示模式
 *   .fitCenter()
 *   //淡入淡出动画效果,300为默认持续时间
 *   .crossFade(300)
 *   //没有动画效果
 *   //.dontAnimate()
 *   .into(要显示图片的ImageView视图);
 */
package com.cloud.core.glides;