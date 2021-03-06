package com.example.mm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View.OnClickListener;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends Activity implements OnClickListener{

    public void d(String... message) {
        String str = String.join("\t", message);
        android.util.Log.d("MYDEBUG", ""
                + String.format("%1$3d", Thread.currentThread().getStackTrace()[3].getLineNumber()) + " "
                //+ Thread.currentThread().getStackTrace()[3].getClassName() + " "
                + Thread.currentThread().getStackTrace()[3].getMethodName() + " " + str);
    }


    //String pdfUrl = "https://www.glump.net/_media/howto/desktop/vim-graphical-cheat-sheet-and-tutorial/vi-vim-cheat-sheet-and-tutorial.pdf";
    String pdfUrl = "https://www.data.jma.go.jp/fcd/yoho/data/jishin/kaisetsu_tanki_latest.pdf";
    String url = "https://docs.google.com/gview?embedded=true&url=" + pdfUrl;
    String url1= "https://www.jma.go.jp/bosai/map.html#contents=himawari";

    private WebView webview;

    public static int reloaded = 0;
    public final int reloadmax = -4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        d();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            d("savedInstanceState==null");
        } else {
            d("NOTsavedInstanceState==null");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager cookieSyncMngr=CookieSyncManager.createInstance(getApplicationContext());
            cookieSyncMngr.startSync();
            CookieManager cookieManager=CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }

        webview = (WebView) findViewById(R.id.webView1);

        //if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB){
        //    reloadmax = 0;
        //} else {
        //    reloadmax = 5;
        //}

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.getSettings().setSupportZoom(true);
        webview.setVerticalScrollbarOverlay(true);
        webview.setInitialScale(300);
        //webview.getSettings().setUseWideViewPort(true);
        //webview.getSettings().setLoadWithOverviewMode(true);
        //
        //webview.setVerticalScrollBarEnabled(true);
        //webview.setHorizontalScrollBarEnabled(true);
        //webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        d();
        webview.loadUrl(url);

        ProgressDialog loading = new ProgressDialog(this);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab1.setOnClickListener(this);

        webview.setWebViewClient(new WebViewClient() {

            boolean checkOnPageStartedCalled = false;

            @Override
            public boolean shouldOverrideUrlLoading (WebView view, String url) {
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                loading.show();
                d();
                //SystemClock.sleep(1000);
                checkOnPageStartedCalled = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                loading.dismiss();
                super.onPageFinished(view, url);
                //webview.postDelayed(new Runnable() {
                //    @Override
                //    public void run() {
                //        webview.scrollTo(webview.getContentHeight(),webview.getContentHeight());
                //    }
                //}, 100);
                //webview.clearCache(true);

                d("everreloaded=" + String.valueOf(reloaded));
                //SystemClock.sleep(1000);
                if (reloaded < reloadmax) {
                    if (checkOnPageStartedCalled == true) {
                        reloaded++;
                        webview.loadUrl("javascript:window.location.reload( true )");
                        d("checkOnPageStartedCalled==true reloaded=" + String.valueOf(reloaded));
                    } else {
                        showPdfFile(url);
                        d("NOTcheckOnPageStartedCalled==true");
                    }
                }
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                super.shouldInterceptRequest(view, url);
                //d();
                return null;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                d();
            }

        });

    }

    public void onClick(View v) {
        d();
        if (v.getId() == R.id.fab) {
            webview.clearCache(false);
            SystemClock.sleep(1000);
            webview.loadUrl(url);
            SystemClock.sleep(1000);
        }
        if (v.getId() == R.id.fab1) {
            webview.clearCache(false);
            webview.loadUrl(url1);
            SystemClock.sleep(1000);
        }
    }

    public void onRestart() {
        super.onRestart();
        d();
    }

    @Override
    public void onResume() {
        super.onResume();
        d();
    }

    private void showPdfFile(final String urlString) {
        d();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            webview.getSettings().setJavaScriptEnabled(true);
        }
        //webview.getSettings().setSupportZoom(true);
        //webview.getSettings().setDisplayZoomControls(false);
        webview.loadUrl(urlString);
        webview.setWebViewClient(new WebViewClient() {
            boolean checkOnPageStartedCalled = false;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                d();
                checkOnPageStartedCalled = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //SystemClock.sleep(1000);
                if (reloaded <= 0) {
                    if (checkOnPageStartedCalled == true) {
                        //SystemClock.sleep(1000);
                        webview.reload();
                        reloaded++;
                        d("checkOnPageStartedCalled==true reloaded=" + String.valueOf(reloaded));
                    } else {
                        showPdfFile(url);
                        d("NOTcheckOnPageStartedCalled==true");
                    }
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                d();
            }

        });
    }
}