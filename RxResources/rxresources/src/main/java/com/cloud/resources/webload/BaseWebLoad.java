package com.cloud.resources.webload;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.http.SslError;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloud.core.ObjectJudge;
import com.cloud.core.ProgressBarIV;
import com.cloud.core.enums.WebLoadDisplayType;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.GlobalUtils;
import com.cloud.core.utils.PixelUtils;
import com.cloud.resources.R;

import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-9-10 下午4:17:45
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class BaseWebLoad extends RelativeLayout {

    private int pbarid = 1307849723;
    private int wvid = 700133986;
    private int progresstextid = 127218767;
    private WebLoadDisplayType mdisplayType = WebLoadDisplayType.Progress;
    private boolean isinstanced = false;
    private int mProgressDrawabbleResid = 0;
    private TextSize mWebTextSize = TextSize.NORMAL;
    private String mJsInterfaceName = "rxfun";
    private int webViewAlpha = 0;
    private int webViewBackgroundResource = 0;
    private boolean isParseError = false;
    private String htmlContent = "";
    private boolean isLoadHtmlContent = false;

    public BaseWebLoad(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseWebLoad(Context context) {
        this(context, null);
    }

    /**
     * @param 设置mdisplayType
     */
    protected void setDisplayType(WebLoadDisplayType mdisplayType) {
        this.mdisplayType = mdisplayType;
    }

    /**
     * 设置webview 透明度 0-255
     *
     * @param alpha
     */
    public void setWebViewAlpha(int alpha) {
        this.webViewAlpha = alpha;
    }

    public void setWebViewBackgroundResource(int resid) {
        this.webViewBackgroundResource = resid;
    }

    /**
     * @param 设置mProgressDrawableResid
     */
    protected void setProgressDrawableResid(int resid) {
        if (resid != 0) {
            try {
                if (isinstanced) {
                    if (mdisplayType == WebLoadDisplayType.Progress) {
                        ProgressBar pbar = (ProgressBar) findViewById(pbarid);
                        Drawable mdrawable = pbar.getResources().getDrawable(
                                resid);
                        pbar.setProgressDrawable(mdrawable);
                    } else if (mdisplayType == WebLoadDisplayType.Loading
                            || mdisplayType == WebLoadDisplayType.LoadingAndText) {
                        ProgressBarIV pbar = (ProgressBarIV) findViewById(pbarid);
                        pbar.setBackgroundResource(resid);
                    }
                } else {
                    mProgressDrawabbleResid = resid;
                }
            } catch (Exception e) {
                Logger.L.error("", e);
            }
        }
    }

    /**
     * @param 设置mTextSize
     */
    protected void setTextSize(TextSize mTextSize) {
        if (isinstanced) {
            WebView wv = (WebView) findViewById(wvid);
            wv.getSettings().setTextSize(mTextSize);
        } else {
            mWebTextSize = mTextSize;
        }
    }

    /**
     * @param 与js交互接口名 (默认tnt)
     */
    protected void setJsInterfaceName(String mJsInterfaceName) {
        this.mJsInterfaceName = mJsInterfaceName;
    }

    protected void onCreated(Context context) {

    }

    protected void onPreCreated(Context context) {

    }

    protected Object getJavascriptInterface(WebView wv) {
        return null;
    }

    /**
     * 获取额外头数据
     *
     * @return
     */
    protected Map<String, String> getExtraHeaders(
            Map<String, String> extraHeaders) {
        return extraHeaders;
    }

    /**
     * web loading callback
     *
     * @param view
     * @param url
     */
    protected boolean onOverrideUrlLoading(WebView view, String url) {
        return false;
    }

    /**
     * Report an error to the host application. These errors are unrecoverable
     * (i.e. the main resource is unavailable). The errorCode parameter
     * corresponds to one of the ERROR_* constants.
     *
     * @param view        The WebView that is initiating the callback.
     * @param success     true success;false fail;
     * @param errorCode   The error code corresponding to an ERROR_* value.
     * @param description A String describing the error.
     * @param failingUrl  The url that failed to load.
     */
    protected void onLoadFinished(WebView view, boolean success, int errorCode, String description,
                                  String failingUrl) {

    }

    protected void onReceivedTitle(String title) {

    }

    private void init(Context context) {
        try {
            onPreCreated(context);

            if (mdisplayType == WebLoadDisplayType.Progress) {
                ProgressBar mpbar = createProgressBar(context);
                WebView webViewLayout = buildWebViewLayout(context, mpbar, null);
                LayoutParams wvparam = new LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT);
                wvparam.addRule(RelativeLayout.BELOW, pbarid);
                this.addView(webViewLayout, wvparam);
                this.addView(mpbar);
            } else if (mdisplayType == WebLoadDisplayType.Loading) {
                ProgressBarIV mpbar = createProgressBarIV(context);
                WebView webViewLayout = buildWebViewLayout(context, mpbar, null);
                LayoutParams wvparam = new LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT);
                this.addView(webViewLayout, wvparam);
                this.addView(mpbar);
            } else if (mdisplayType == WebLoadDisplayType.LoadingAndText) {
                ProgressBarIV mpbar = createProgressBarIV(context);
                TextView mtv = createTextView(context);
                WebView webViewLayout = buildWebViewLayout(context, mpbar, mtv);
                LayoutParams wvparam = new LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT);
                this.addView(webViewLayout, wvparam);
                this.addView(mpbar);
                this.addView(mtv);
            }
            isinstanced = true;
            onCreated(context);
        } catch (Exception e) {
            Logger.L.warn("web view init error:", e);
        }
    }

    private ProgressBar createProgressBar(Context context) {
        LayoutParams pbarparam = new LayoutParams(
                LayoutParams.MATCH_PARENT, PixelUtils.dip2px(
                context, 8));
        pbarparam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        int style = android.R.attr.progressBarStyleHorizontal;
        ProgressBar pbar = new ProgressBar(context, null, style);
        pbar.setId(pbarid);
        pbar.setLayoutParams(pbarparam);
        pbar.setMax(100);
        pbar.setProgress(0);
        pbar.setPadding(0, 0, 0, 0);
        if (mProgressDrawabbleResid != 0
                && mdisplayType == WebLoadDisplayType.Progress) {
            try {
                Drawable mdrawable = context.getResources().getDrawable(
                        mProgressDrawabbleResid);
                pbar.setProgressDrawable(mdrawable);
            } catch (Exception e) {
                Logger.L.error("not found progress drawable resource error:", e);
            }
        }
        return pbar;
    }

    private ProgressBarIV createProgressBarIV(Context context) {
        int size = PixelUtils.dip2px(context, 24);
        LayoutParams pbarparam = new LayoutParams(
                size, size);
        pbarparam.addRule(RelativeLayout.CENTER_IN_PARENT);
        ProgressBarIV pbar = new ProgressBarIV(context);
        pbar.setId(pbarid);
        pbar.setLayoutParams(pbarparam);
        if (mProgressDrawabbleResid != 0
                && (mdisplayType == WebLoadDisplayType.Loading || mdisplayType == WebLoadDisplayType.LoadingAndText)) {
            pbar.setBackgroundResource(mProgressDrawabbleResid);
            pbar.startAnim();
        }
        return pbar;
    }

    private TextView createTextView(Context context) {
        LayoutParams tvparam = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        tvparam.addRule(RelativeLayout.BELOW, pbarid);
        tvparam.addRule(RelativeLayout.CENTER_HORIZONTAL);
        TextView tv = new TextView(context);
        tv.setLayoutParams(tvparam);
        tv.setId(progresstextid);
        return tv;
    }

    private WebView buildWebViewLayout(Context context, final View pbar, final View tv) {
        WebView webView = (WebView) View.inflate(context, R.layout.web_view_layout, null);
        webView.setId(wvid);
        webView.requestFocus();
        webView.setVerticalScrollbarOverlay(false);
        webView.setHorizontalScrollbarOverlay(false);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        if (webViewBackgroundResource != 0) {
            webView.setBackgroundResource(webViewBackgroundResource);
        }
        if (webViewAlpha >= 0 && webViewAlpha < 255 && this.getBackground() != null) {
            webView.getBackground().setAlpha(webViewAlpha);
        }
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        //重新测量
        webView.measure(w, h);
        WebSettings settings = webView.getSettings();
        if (settings != null) {
            settings.setJavaScriptEnabled(true);
            settings.setSupportZoom(false);
            settings.setDefaultZoom(ZoomDensity.MEDIUM);
            settings.setBuiltInZoomControls(true);
            settings.setAllowFileAccess(true);
            settings.setTextSize(mWebTextSize);
            settings.setDefaultTextEncodingName("utf-8");
            settings.setDomStorageEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
            } else {
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            }
            settings.setDatabaseEnabled(true);
            settings.setGeolocationEnabled(true);
            settings.setGeolocationDatabasePath(context.getDir("database", Context.MODE_PRIVATE).getPath());
            settings.setBlockNetworkImage(false);
            settings.setJavaScriptCanOpenWindowsAutomatically(true);
            settings.setLoadsImagesAutomatically(true);
            if (Build.VERSION.SDK_INT >= 21) {
                settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            settings.setSavePassword(false);
            settings.setAppCacheEnabled(true);
        }
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (mdisplayType == WebLoadDisplayType.Progress) {
                    if (pbar != null) {
                        ProgressBar mpbar = (ProgressBar) pbar;
                        mpbar.setProgress(newProgress);
                        mpbar.postInvalidate();
                    }
                } else if (mdisplayType == WebLoadDisplayType.LoadingAndText) {
                    if (tv != null) {
                        TextView mtv = (TextView) tv;
                        mtv.setText(newProgress + "%");
                    }
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                BaseWebLoad.this.onReceivedTitle(title);
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                isParseError = true;
                onLoadFinished(view, false, errorCode, description, failingUrl);
            }

            @Override
            public void onPageStarted(WebView view, String url,
                                      Bitmap favicon) {
                if (mdisplayType == WebLoadDisplayType.Progress) {
                    if (pbar != null) {
                        pbar.setVisibility(View.VISIBLE);
                    }
                } else if (mdisplayType == WebLoadDisplayType.Loading) {
                    if (pbar != null) {
                        pbar.setVisibility(View.VISIBLE);
                    }
                } else if (mdisplayType == WebLoadDisplayType.LoadingAndText) {
                    if (pbar != null) {
                        pbar.setVisibility(View.VISIBLE);
                    }
                    if (tv != null) {
                        tv.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!isParseError) {
                    if (isLoadHtmlContent) {
                        view.loadUrl(MessageFormat.format("javascript:setHtmlContent(\"{0}\");", GlobalUtils.escape(htmlContent)));
                    }
                    onLoadFinished(view, true, 0, "", url);
                }
                if (mdisplayType == WebLoadDisplayType.Progress) {
                    if (view.getContentHeight() != 0 && pbar != null) {
                        ProgressBar mpbar = (ProgressBar) pbar;
                        mpbar.setProgress(0);
                        mpbar.setVisibility(View.GONE);
                    }
                } else if (mdisplayType == WebLoadDisplayType.Loading) {
                    if (pbar != null) {
                        pbar.setVisibility(View.GONE);
                    }
                } else if (mdisplayType == WebLoadDisplayType.LoadingAndText) {
                    if (pbar != null) {
                        pbar.setVisibility(View.GONE);
                    }
                    if (tv != null) {
                        tv.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return onOverrideUrlLoading(view, url);
            }
        });
        webView.addJavascriptInterface(getJavascriptInterface(webView), mJsInterfaceName);
        webView.removeJavascriptInterface("searchBoxJavaBridge_");
        webView.removeJavascriptInterface("accessibilityTra");
        webView.removeJavascriptInterface("accessibility");

        return webView;
    }

    public void postUrl(String url, HashMap<String, String> postData) {
        try {
            isParseError = false;
            this.isLoadHtmlContent = false;
            StringBuffer sb = new StringBuffer();
            if (!ObjectJudge.isNullOrEmpty(postData)) {
                Iterator<Map.Entry<String, String>> iter = postData
                        .entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) iter
                            .next();
                    sb.append(String.format("%s=%s", entry.getKey(),
                            URLEncoder.encode(entry.getValue(), "utf-8")));
                }
            }
            WebView wv = (WebView) findViewById(wvid);
            RelativeLayout.LayoutParams wvparam = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            if (mdisplayType == WebLoadDisplayType.Progress) {
                wvparam.addRule(RelativeLayout.BELOW, pbarid);
            }
            wv.setLayoutParams(wvparam);
            wv.postUrl(url, sb.toString().getBytes());
        } catch (Exception e) {
            Logger.L.error("post url error:", e);
        }
    }

    public void postUrl(String url) {
        postUrl(url, null);
    }

    public void loadUrl(String url, Map<String, String> extraHeaders) {
        try {
            isParseError = false;
            this.isLoadHtmlContent = false;
            WebView wv = (WebView) findViewById(wvid);
            if (extraHeaders == null) {
                extraHeaders = new HashMap<String, String>();
            }
            Map<String, String> headersdata = getExtraHeaders(extraHeaders);
            if (headersdata == null) {
                wv.loadUrl(url);
            } else {
                wv.loadUrl(url, headersdata);
            }
        } catch (Exception e) {
            Logger.L.error("load url error:", e);
        }
    }

    public void loadData(String htmlContent) {
        try {
            isParseError = false;
            this.htmlContent = htmlContent;
            this.isLoadHtmlContent = true;
            WebView wv = (WebView) findViewById(wvid);
            wv.loadUrl("file:///android_asset/html_content_templete.html");
        } catch (Exception e) {
            Logger.L.error("load url error:", e);
        }
    }

    public void loadUrl(String url) {
        loadUrl(url, null);
    }

    /**
     * 回退web或结束当前窗口
     *
     * @param activity
     * @param flag     true直接结束当前窗口,false先回退再结束
     */
    public void finishOrGoBack(Activity activity, boolean flag, int enterAnim, int exitAnim, OnFinishOrGoBackListener finishOrGoBackListener) {
        try {
            if (flag) {
                activity.finish();
            } else {
                WebView wv = (WebView) findViewById(wvid);
                if (wv.canGoBack()) {
                    wv.goBack();
                    if (finishOrGoBackListener != null) {
                        finishOrGoBackListener.onFinishOrGoBack(wv.canGoBack());
                    }
                } else {
                    if (finishOrGoBackListener != null) {
                        finishOrGoBackListener.onFinishOrGoBack(false);
                    } else {
                        activity.finish();
                    }
                    activity.overridePendingTransition(enterAnim != 0 ? enterAnim : 0, exitAnim != 0 ? exitAnim : 0);
                }
            }
        } catch (Exception e) {
            Logger.L.error("finishOrGoBack error:", e);
        }
    }

    /**
     * 回退web或结束当前窗口
     *
     * @param activity
     * @param flag     true直接结束当前窗口,false先回退再结束
     */
    public void finishOrGoBack(Activity activity, boolean flag, OnFinishOrGoBackListener finishOrGoBackListener) {
        finishOrGoBack(activity, flag, 0, 0, null);
    }

    /**
     * 回退web或结束当前窗口
     *
     * @param activity
     * @param flag     true直接结束当前窗口,false先回退再结束
     */
    public void finishOrGoBack(Activity activity) {
        finishOrGoBack(activity, false, null);
    }

    public WebView getWebView() {
        WebView wv = (WebView) findViewById(wvid);
        return wv;
    }
}
