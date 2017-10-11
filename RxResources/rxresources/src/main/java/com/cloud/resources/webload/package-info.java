/**
 * public class HtmlTest extends BaseWebLoad {
 *
 *		public HtmlTest(Context context, AttributeSet attrs) {
 *			super(context, attrs);
 *		}
 *	
 *		@Override
 *		protected void onPreCreated(Context context) {
 *			// 构建之前
 *			this.setDisplayType(WebLoadDisplayType.LoadingAndText);
 *			this.setProgressDrawableResid(R.anim.progressbar_loading);
 *			this.setJsInterfaceName("derlook");
 *			this.setTextSize(TextSize.NORMAL);
 *		}
 *	
 *		@Override
 *		protected void onCreated(Context context) {
 *			// 构建之后
 *		}
 *	
 *		@Override
 *		protected Object getJavascriptInterface(WebView wv) {
 *			// 获取接口对象
 *			return new AndroidToastForJs();
 *		}
 *	
 *		@Override
 *		protected void onFinished(WebView view) {
 *			// 加载完成;完成之后启用本地的相应控件;在此处调用js即类似页面初始化函数;
 *			view.loadUrl("javascript:testjson('测试')");
 *		}
 *	
 *		@Override
 *		protected void onOverrideUrlLoading(WebView view, String url) {
 *			// 加载中
 *		}
 *	
 *		@Override
 *		protected void onLoadError(WebView view, int errorCode, String description,
 *				String failingUrl) {
 *			// 链接超时、地址无效或加载失败
 *		}
 *	
 *		// js调用对象
 *		class AndroidToastForJs {
 *			// 给js调用的函数接口前需注册@JavascriptInterface;
 *			// android:targetSdkVersion决定sdk版本;
 *			// 4.2.2之前需调用自定义的@JavascriptInterface,4.2.2之后调用系统android.R.webkit.JavascriptInterface;
 *			// js调用android本地函数:JsInterfaceName.LocalMethod(params...);(如derlook.getTestJsonResult('');)
 *			@JavascriptInterface
 *			public void getTestJsonResult(String result) {
 *				ToastUtils.showLong(getContext(), result);
 *			}
 *		}
 *	}
 *
 *	private BrandPromotionDetailWebView mbpdwebview = null;
 *	mbpdwebview = (BrandPromotionDetailWebView) findViewById(R.id.brand_promotion_bpdwv);
 *	mbpdwebview.postUrl(mpitem.getDetailUrl());
 *	mbpdwebview.loadUrl();
 */
/**
 * @author lijinghuan
 *
 */
package com.cloud.resources.webload;


