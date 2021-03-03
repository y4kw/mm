package com.example.ll;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.HttpResponseCache;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.ScaleAnimation;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;


//public class MainActivity extends AppCompatActivity {
public class MainActivity extends Activity implements OnClickListener{

    public void d(String... message) {
        String str = String.join("\t", message);
        android.util.Log.d("MYDEBUG", ""
                + String.format("%1$3d", Thread.currentThread().getStackTrace()[3].getLineNumber()) + " "
                //+ Thread.currentThread().getStackTrace()[3].getClassName() + " "
                + Thread.currentThread().getStackTrace()[3].getMethodName() + " " + str);
    }


    String pdfUrl = "https://www.glump.net/_media/howto/desktop/vim-graphical-cheat-sheet-and-tutorial/vi-vim-cheat-sheet-and-tutorial.pdf";
    //String pdfUrl = "https://www.data.jma.go.jp/fcd/yoho/data/jishin/kaisetsu_tanki_latest.pdf";
    String url = "https://docs.google.com/gview?embedded=true&url=" + pdfUrl;

    private WebView webview;

    public static int reloaded = 0;
    //public int reloadmax = 5;
    public final int reloadmax = -4;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

        //CookieManager.getInstance().removeAllCookies(null);
        //CookieManager.getInstance().flush();

        //WebView webview = (WebView) findViewById(R.id.webView1);
        webview = (WebView) findViewById(R.id.webView1);

        //if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB){
        //    reloadmax = 0;
        //} else {
        //    reloadmax = 5;
        //}

        webview.requestFocus();
        webview.getSettings().setLightTouchEnabled(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        d();
        webview.loadUrl(url);

        //Button button = findViewById(R.id.button);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        ProgressDialog loading = new ProgressDialog(this);
        //loading.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //loading.setMessage("onPageStarted");

        fab.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                d();
                //webview.reload();
                webview.loadUrl(url);
            }
        });

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
                webview.clearCache(true);
                d("everreloaded=" + String.valueOf(reloaded));
                //SystemClock.sleep(1000);
                if (reloaded < reloadmax) {
                    if (checkOnPageStartedCalled == true) {
                        //webview.reload();
                        reloaded++;
                        SystemClock.sleep(1000*reloaded);
                        //webview.reload();
                        //webview.loadUrl(url);
                        webview.loadUrl("javascript:window.location.reload( true )");
                        d("checkOnPageStartedCalled==true reloaded=" + String.valueOf(reloaded));
                        //checkOnPageStartedCalled = false;
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

    //private LinearLayout.LayoutParams createParam(int w, int h){
    //    return new LinearLayout.LayoutParams(w, h);
    //}

    public void onClick(View v) {
        d();
    }

    public void onRestart() {
        //webview.clearCache(true);
        super.onRestart();
        //webview.loadUrl(url);
        //webview.loadUrl("javascript:window.location.reload( true )");
        d();
    }

    @Override
    public void onResume() {
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //    CookieManager.getInstance().removeAllCookies(null);
        //    CookieManager.getInstance().flush();
        //} else {
        //    CookieSyncManager cookieSyncMngr=CookieSyncManager.createInstance(getApplicationContext());
        //    cookieSyncMngr.startSync();
        //    CookieManager cookieManager=CookieManager.getInstance();
        //    cookieManager.removeAllCookie();
        //    cookieManager.removeSessionCookie();
        //    cookieSyncMngr.stopSync();
        //    cookieSyncMngr.sync();
        //}

        super.onResume();
        webview.loadUrl(url);
        webview.clearCache(true);
        //webview.loadUrl("javascript:window.location.reload( true )");
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