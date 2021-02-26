package com.example.kk;

import android.os.Build;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.text.SpannableStringBuilder;


//public class MainActivity extends AppCompatActivity {
public class MainActivity extends Activity implements OnClickListener{

    public void d(String... message) {
        String str = String.join("\t", message);
        android.util.Log.d("MYDEBUG", ""
                + String.format("%1$3d", Thread.currentThread().getStackTrace()[3].getLineNumber()) + " "
                //+ Thread.currentThread().getStackTrace()[3].getClassName() + " "
                + Thread.currentThread().getStackTrace()[3].getMethodName() + " " + str);
    }

    boolean checkOnPageStartedCalled = false;
    private EditText textUrl;
    private Button buttonGo;
    private WebView webview;

    public static int reloaded = 0;
    //public int reloadmax = 4;
    public final int reloadmax = 4;

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
        //WebView webview = (WebView) findViewById(R.id.webView1);
        webview = (WebView) findViewById(R.id.webView1);

        //if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB){
        //    reloadmax = 0;
        //} else {
        //    reloadmax = 5;
        //}

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);

        String pdfUrl = "https://www.data.jma.go.jp/fcd/yoho/data/jishin/kaisetsu_tanki_latest.pdf";
        String url = "http://docs.google.com/gview?embedded=true&url=" + pdfUrl;

        webview.loadUrl(url);

        webview.setWebViewClient(new WebViewClient() {
            boolean checkOnPageStartedCalled = false;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                d();
                checkOnPageStartedCalled = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                d();
                //SystemClock.sleep(1000);
                //if (reloaded < reloadmax) {
                    if (checkOnPageStartedCalled == true) {
                        //SystemClock.sleep(1000);
                        //webview.reload();
                        reloaded++;
                        d("checkOnPageStartedCalled==true reloaded=" + String.valueOf(reloaded));
                    } else {
                        showPdfFile(url);
                        d("NOTcheckOnPageStartedCalled==true");
                    }
                //}
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                d();
            }

        });

    }

    private LinearLayout.LayoutParams createParam(int w, int h){
        return new LinearLayout.LayoutParams(w, h);
    }
    public void onClick(View v) {
        //android.util.Log.d("MYDEBUG", "" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        SpannableStringBuilder url = (SpannableStringBuilder)textUrl.getText();
        webview.loadUrl(url.toString());
    }

    public void onResume() {
        //android.util.Log.d("MYDEBUG", "" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        super.onResume();
        webview.reload();
    }

    private void showPdfFile(final String urlString) {
        d();
        //showProgress();
        //webview.invalidate();
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