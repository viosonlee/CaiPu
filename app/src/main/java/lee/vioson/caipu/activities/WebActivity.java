package lee.vioson.caipu.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import lee.vioson.caipu.BuildConfig;
import lee.vioson.caipu.R;
import lee.vioson.caipu.utils.ActivitySwitchBase;


/**
 * 说明:h5页面
 * <p/>
 * author: 吕飞
 * since: 2015-09-02
 * Copyright:恒信汽车电子商务有限公司
 */
public class WebActivity extends BackActivity {
    public final static String TITLE = "title";
    public final static String URL = "url";
    public Toolbar mToolBar;
    public WebView mWebView;
    public String mWeb;
    public WebSettings settings;

    LinearLayout parent;

    android.os.Handler handler = new android.os.Handler(new android.os.Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1)
                mWebView.reload();
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        parent = (LinearLayout) findViewById(R.id.ll_parent);
        mWebView = (WebView) findViewById(R.id.web_view);


        settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);

        mWebView.getSettings().setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
//        settings.setBuiltInZoomControls(true);                      //support zoom
        settings.setLoadWithOverviewMode(true);
        settings.setUserAgentString(settings.getUserAgentString() + " HXMall/" + BuildConfig.VERSION_NAME);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("tel:")) {
                    Intent dialIntent = new Intent();
                    dialIntent.setAction(Intent.ACTION_DIAL);
                    dialIntent.setData(Uri.parse(url));
                    startActivity(dialIntent);
                } else {
                    mWebView.loadUrl(url);
                }
                return true;
            }
        });

        if (getIntent().getExtras() != null || getIntent().getBundleExtra(ActivitySwitchBase.DATA) != null) {
            String title = getIntent().getExtras().getString(WebActivity.TITLE) != null
                    ? getIntent().getExtras().getString(WebActivity.TITLE)
                    : getIntent().getBundleExtra(ActivitySwitchBase.DATA).getString(WebActivity.TITLE);
            if (!TextUtils.isEmpty(title)) {
                Log.e(getClass().getName(), title);
                this.setTitle(title);
            }

            String url = getIntent().getExtras().getString(WebActivity.URL)
                    != null ? getIntent().getExtras().getString(WebActivity.URL)
                    : getIntent().getBundleExtra(ActivitySwitchBase.DATA).getString(WebActivity.URL);
            if (!TextUtils.isEmpty(url)) {
                Log.e(getClass().getName(), url);
//                if (url.startsWith("/")) {
//                    mWebView.loadUrl(ApiUtil.MallHostUrl + url);
//                } else {
                mWebView.loadUrl(url);
//                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.setVisibility(View.GONE);
        }
    }


    //控制返回键
    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }


}
