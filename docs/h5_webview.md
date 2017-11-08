H5Webview控件
----
首先我们在项目中创建H5WebView控件并继承BaseWebLoad
```java
/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/12/13
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class H5WebView extends BaseWebLoad {

    private OnH5WebViewListener mcwvlistener = null;
    private OnSelectTextListener onSelectTextListener = null;
    private Activity activity = null;
    private LoadingDialog mloading = new LoadingDialog();

    @Override
    protected void onCreated(Context context) {
    	//额外的初始化设置，根据项目情况自行添加；
        WebView webView = getWebView();
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        WindowManager wm = ObjectManager.getWindowManager(context);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
    }

    public H5WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //回调监听
    public void setMcwvlistener(OnH5WebViewListener mcwvlistener) {
        this.mcwvlistener = mcwvlistener;
    }

    //在webview中文本选中回调监听
    public void setOnSelectTextListener(OnSelectTextListener listener) {
        this.onSelectTextListener = listener;
    }

    //设置当前上下文
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreCreated(Context context) {
    	//加载之前提示效果(loading和文本)
        this.setDisplayType(WebLoadDisplayType.LoadingAndText);
        //设置加载中动画资源
        this.setProgressDrawableResid(R.anim.frame_loading);
        //设置脚本方法调用前缀
        this.setJsInterfaceName("mibao");
        //设置webview文本大小
        this.setTextSize(WebSettings.TextSize.NORMAL);
        //设置背影透明度(0~255)
        this.setWebViewAlpha(200);
        //设置背影颜色
        this.setWebViewBackgroundResource(R.drawable.white_bg);
    }

    @Override
    protected boolean onOverrideUrlLoading(WebView view, String url) {
        if (!TextUtils.isEmpty(url)) {
            return mcwvlistener.onUrlListener(url);
        }
        return false;
    }

    @Override
    protected void onLoadFinished(WebView view, boolean success, int errorCode, String description, String url) {
        if (success) {
        	//告诉h5页面已经加载完成
            view.loadUrl("javascript:MIBAO.NativeBridgeJsLoadComplate();");
        }
        if (mcwvlistener != null) {
            mcwvlistener.onLoadFinished(view, success, errorCode, description, url);
        }
    }

    @Override
    protected Object getJavascriptInterface(WebView wv) {
        return new H5ForJs();
    }

    @Override
    protected void onReceivedTitle(String title) {
        if (mcwvlistener != null) {
            mcwvlistener.onReceivedTitle(title);
        }
    }

    public interface OnH5WebViewListener {
        public void onReceivedTitle(String title);

        public void onExtraHeadersListener(Map<String, String> extraHeaders);

        public boolean onUrlListener(String url);

        public void onShowLoginModal(boolean isCallback);

        public void clickedEvent(String extras);

        public void onLoadFinished(WebView view, boolean success, int errorCode, String description, String url);
    }

    public interface OnSelectTextListener {
        public void onSelectText(String selectText);
    }

    class H5ForJs {

        @JavascriptInterface
        public String getToken() {
        	//h5获取本地token
            return "token 值";
        }

        @JavascriptInterface
        public void showLoginModal(boolean isCallback) {
        	//h5可调用window.showLoginModal(isCallback)方法打开本地登录窗口
        	//isCallback:true登录完成后回调h5方法；
            if (mcwvlistener != null) {
                mcwvlistener.onShowLoginModal(isCallback);
            }
        }

        @JavascriptInterface
        public void downloadApk(String url, String extras) {
        	//h5可调用window.downloadApk(url,extras)方法下载
        	//extras:额外参数
            BaseMibaoCommonUtils.downApp(getContext(), url, extras);
        }

        @JavascriptInterface
        public void statisticsClickCountAction(String extras) {
        	//h5调用window.statisticsClickCountAction(extras)方法进行事件统计
        	//extras:额外参数
        }

        @JavascriptInterface
        public void getAPIMethod(String extras) {
        	//h5可调用window.getAPIMethod(extras)方法获取本地任意api接口返回值
        	//其中参数extras定义参考:H5调用APP任意API接口协议(首页有相应的链接)
            mloading.showDialog(activity, R.string.processing_just, null);
            BasicConfigBean basicConfigBean = BaseMibaoApplication.getInstance().getBasicConfigBean();
            String apiUrl = basicConfigBean.getApiUrl();
            H5InteractionAPIUtils.getAPIMethod(activity, apiUrl, extras, new Action2<APIRequestState, APIReturnResult>() {
                @Override
                public void call(APIRequestState apiRequestState, APIReturnResult apiReturnResult) {
                	//回调成功后将结果传回给h5
                    if (apiRequestState == APIRequestState.Success || apiRequestState == APIRequestState.Error) {
                        getWebView().loadUrl(String.format("javascript:MIBAO.returnAPIResultMethod(%s);", JsonUtils.toStr(apiReturnResult)));
                    }
                    mloading.dismiss();
                }
            });
        }

        @JavascriptInterface
        public void eventStatistical(String statisticalJson) {
            //事件统计
        }

        @JavascriptInterface
        public void share(String shareContent) {
        	//h5调用本地分享
            if (TextUtils.isEmpty(shareContent)) {
                return;
            }
            ShareUtils.getInstance().share(activity, JsonUtils.parseT(shareContent, ShareContent.class));
        }

        @JavascriptInterface
        public void clickedEvent(String extras) {
            if (mcwvlistener != null) {
                mcwvlistener.clickedEvent(extras);
            }
        }

        @JavascriptInterface
        public void getSelectText(String selectText) {
        	//获取选中文本
            if (onSelectTextListener != null) {
                onSelectTextListener.onSelectText(selectText);
            }
        }
    }

    //获取webview中选中的文本
    public void getSelectText() {
        getWebView().loadUrl("javascript:window.mibao.getSelectText(window.getSelection().toString());");
    }

    //登录完成后可调用h5中的方法
    public void loginComplateCallback() {
        getWebView().loadUrl("javascript:MIBAO.NativeBridgeJsLoginComplate();");
    }
}
```