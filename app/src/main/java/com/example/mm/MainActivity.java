package com.example.mm;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    public static int reloaded = 0;
    public final int reloadmax = -4;
    //String pdfUrl = "https://www.glump.net/_media/howto/desktop/vim-graphical-cheat-sheet-and
    // -tutorial/vi-vim-cheat-sheet-and-tutorial.pdf";
    String pdfUrl = "https://www.data.jma.go.jp/fcd/yoho/data/jishin/kaisetsu_tanki_latest.pdf";
    String urlKaisetsu = "https://docs.google.com/gview?embedded=true&url=" + pdfUrl;
    String urlHimawari = "https://www.jma.go.jp/bosai/map.html#contents=himawari";
    public String url = urlKaisetsu;

    private WebView webview;

    @TargetApi(Build.VERSION_CODES.O)
    public void log(String... message) {
        String str = String.join("\t", message);
        String msg = ""
                + String.format("%1$3d",
                Thread.currentThread().getStackTrace()[3].getLineNumber()) + " "
                + Thread.currentThread().getStackTrace()[3].getMethodName() + " " + str;
        android.util.Log.d("MYDEBUG", msg);
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        log();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webview = (WebView) findViewById(R.id.webView1);

        //Toolbar toolbar = findViewById(R.id.toolbar3);
        //setSupportActionBar(toolbar);
        //toolbar.setTitle("caso");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager cookieSyncMngr =
                    CookieSyncManager.createInstance(getApplicationContext());
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            //cookieManager.removeAllCookie();
            //cookieManager.removeSessionCookie();
            cookieManager.setAcceptCookie(true);

            //cookieManager.setAcceptThirdPartyCookies(webview, true);
            //cookieSyncMngr.stopSync();
            //cookieSyncMngr.sync();
        }


        //if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB){
        //    reloadmax = 0;
        //} else {
        //    reloadmax = 5;
        //}

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webview.getSettings().setSupportZoom(true);
        //webview.getSettings().setUseWideViewPort(false);
        //webview.getSettings().setLoadWithOverviewMode(false);
        //webview.setInitialScale(300);
        //webview.getSettings().setBuiltInZoomControls(true);
        //webview.getSettings().setDisplayZoomControls(false);
        //webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        //webview.getSettings().setPluginState(WebSettings.PluginState.ON);

        //webview.setVerticalScrollBarEnabled(true);
        //webview.setHorizontalScrollBarEnabled(true);
        //webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        log();
        if (savedInstanceState == null) {
            log("savedInstanceState==null");
            //url = urlKaisetsu;
            url = "https://ifconfig.me";
            webview.loadUrl(url);
        } else {
            log("NOTsavedInstanceState==null");
        }

        ProgressDialog loading = new ProgressDialog(this);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab1.setOnClickListener(this);

        webview.setWebViewClient(new WebViewClient() {

            boolean checkOnPageStartedCalled = false;

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //return super.shouldOverrideUrlLoading(view, url);
                webview.loadUrl(urlHimawari);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                loading.show();
                log();
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
                log("everreloaded=" + reloaded);
                //SystemClock.sleep(1000);
                if (reloaded < reloadmax) {
                    if (checkOnPageStartedCalled) {
                        reloaded++;
                        webview.loadUrl("javascript:window.location.reload( true )");
                        log("checkOnPageStartedCalled==true reloaded=" + reloaded);
                    } else {
                        showPdfFile(url);
                        log("NOTcheckOnPageStartedCalled==true");
                    }
                }
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                super.shouldInterceptRequest(view, url);
                //log();
                return null;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description,
                                        String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                log();
            }

        });

    }

    public void onClick(View v) {
        log();
        if (v.getId() == R.id.fab) {
            webview.clearCache(false);
            url = urlKaisetsu;
            SystemClock.sleep(1000);
            webview.loadUrl(url);
            SystemClock.sleep(1000);
        }
        if (v.getId() == R.id.fab1) {
            webview.clearCache(false);
            url = urlHimawari;
            webview.loadUrl(url);
            SystemClock.sleep(1000);
        }
    }

    public void onRestart() {
        log();
        super.onRestart();
    }

    @Override
    public void onResume() {
        log();
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        log();
        super.onSaveInstanceState(outState);
        webview.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        log();
        super.onRestoreInstanceState(savedInstanceState);
        webview.restoreState(savedInstanceState);
    }

    private void showPdfFile(final String urlString) {
        log();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webview.getSettings().setJavaScriptEnabled(true);
        }
        //webview.getSettings().setSupportZoom(true);
        //webview.getSettings().setDisplayZoomControls(false);
        webview.loadUrl(urlString);
        webview.setWebViewClient(new WebViewClient() {
            boolean checkOnPageStartedCalled = false;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                log();
                checkOnPageStartedCalled = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //SystemClock.sleep(1000);
                if (reloaded <= 0) {
                    if (checkOnPageStartedCalled) {
                        //SystemClock.sleep(1000);
                        webview.reload();
                        reloaded++;
                        log("checkOnPageStartedCalled==true reloaded=" + reloaded);
                    } else {
                        showPdfFile(url);
                        log("NOTcheckOnPageStartedCalled==true");
                    }
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description,
                                        String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                log();
            }

        });
    }
}