H5Webview控件使用和示例
-----
###### 1.创建H5WebView控件
[H5WebView控件代码](/docs/h5_webview.md)
###### 2.添加布局
```xml
<com.geek.mibao.components.H5WebView
android:id="@+id/h5_wv"
android:layout_width="match_parent"
android:layout_height="match_parent"
android:scrollbars="none" />
```
###### 3.代码部分
```java
//设置上下文
h5Wv.setActivity(this);
//设置回调监听
h5Wv.setMcwvlistener(h5WebViewListener);
private H5WebView.OnH5WebViewListener h5WebViewListener = new H5WebView.OnH5WebViewListener() {
    @Override
    public void onReceivedTitle(String title) {
        //此处可以获取h5的标题
    }

    @Override
    public void onExtraHeadersListener(Map<String, String> extraHeaders) {
    	//加载h5之前可以添加额外的参数
    }

    @Override
    public boolean onUrlListener(String url) {
        //加载url监听
        //返回true中断加载此url，false断续加载此url
        return false;
    }

    @Override
    public void onShowLoginModal(boolean isCallback) {
    	//打开本地登录界面回调
    	//isCallback:登录成功后是否需要通知h5
    }

    @Override
    public void clickedEvent(String extras) {
    	//h5点击事件通知
    }

    @Override
    public void onLoadFinished(WebView webView, boolean b, int i, String s, String s1) {
    	//页面加载结束回调
    }
};
//设置选中文本回调监听
h5Wv.setOnSelectTextListener(selectTextListener);
private H5WebView.OnSelectTextListener selectTextListener = new H5WebView.OnSelectTextListener() {
    @Override
    public void onSelectText(String selectText) {
    	//选中文本回调
    }
};
//加载url形式页面
h5Wv.loadUrl(String url);
//加载html代码形式页面(内有做适配)
h5Wv.loadData(String htmlContent);
//h5页面返回处理
h5Wv.finishOrGoBack(getActivity(), true, 0, 0, new OnFinishOrGoBackListener() {
    @Override
    public void onFinishOrGoBack(boolean isH5GoBack) {
        if (isH5GoBack) {
            //表示h5返回
        } else {
            //非h5返回
        }
    }
});
//其中finishOrGoBack参数依次为:
//activity:上下文
//flag:true直接关闭当前页面;false层级返回，即先返回h5最后再关闭当前页
//enterAnim:进入当前页面动画
//exitAnim:退出当前页面动画
//finishOrGoBackListener:h5回退或关闭当前页面回调

//登录完成调用方法(通告h5页面)
h5Wv.loginComplateCallback();
//获取h5页面选中的文本
h5Wv.getSelectText()
```