package com.example.bb;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.text.SpannableStringBuilder;
import android.webkit.WebViewClient;

//public class MainActivity extends AppCompatActivity {
public class MainActivity extends Activity implements OnClickListener{
//public class Test02_01 extends Activity implements OnClickListener{

    private final int FP = ViewGroup.LayoutParams.FILL_PARENT;
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;

    private EditText textUrl;
    private Button buttonGo;
    private WebView webview;

    @Override protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.activity_main);

        //WebView webview = (WebView) findViewById(R.id.webView1);
        webview = (WebView) findViewById(R.id.webView1);
        //webview = new WebView(this);



        webview.getSettings().setJavaScriptEnabled(true);

        String pdfUrl = "https://www.data.jma.go.jp/fcd/yoho/data/jishin/kaisetsu_tanki_latest.pdf";
        String url = "http://docs.google.com/gview?embedded=true&url=" + pdfUrl;

        //webview.invalidate();
        webview.loadUrl(url);
        //webview.loadUrl("");

        webview.setWebViewClient(new WebViewClient() {
            boolean checkOnPageStartedCalled = false;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                checkOnPageStartedCalled = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (checkOnPageStartedCalled) {
                    //pdfView.loadUrl(removePdfTopIcon);
                    //hideProgress();
                } else {
                    //showPdfFile(imageString);
                    //showPdfFile(url);
                    showPdfFile(pdfUrl);
                }
            }

        });

    }

    private LinearLayout.LayoutParams createParam(int w, int h){
        return new LinearLayout.LayoutParams(w, h);
    }
    public void onClick(View v) {
        SpannableStringBuilder url = (SpannableStringBuilder)textUrl.getText();
        webview.loadUrl(url.toString());
    }

    private void showPdfFile(final String imageString) {
        //showProgress();
        webview.invalidate();
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setSupportZoom(true);
        webview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + imageString);
        webview.setWebViewClient(new WebViewClient() {
            boolean checkOnPageStartedCalled = false;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                checkOnPageStartedCalled = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (checkOnPageStartedCalled) {
                    //webview.loadUrl(removePdfTopIcon);
                    //hideProgress();
                } else {
                    showPdfFile(imageString);
                }
            }
        });
    }
}