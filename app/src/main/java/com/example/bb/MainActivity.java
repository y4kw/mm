package com.example.bb;


import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.text.SpannableStringBuilder;
import android.webkit.WebView;

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
/*
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        //setContentView(linearLayout);


        LinearLayout ctlLinearLayout = new LinearLayout(this);
        ctlLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        textUrl = new EditText(this);
        textUrl.setText("http://");

        buttonGo = new Button(this);
        buttonGo.setText("Go");
        buttonGo.setOnClickListener(this);

        ctlLinearLayout.addView(buttonGo, createParam(WC, WC));
        ctlLinearLayout.addView(textUrl, createParam(FP, WC));
*/

        setContentView(R.layout.activity_main);
        //WebView webview = (WebView) findViewById(R.id.webView1);
        webview = (WebView) findViewById(R.id.webView1);
        //webview = new WebView(this);
        //webView.setWebViewClient(new LocalWebViewClient()); /* ãµãã¯ã©ã¹ã®LocalWebViewClientãå®ç¾©ãã */



        webview.getSettings().setJavaScriptEnabled(true);

        String pdfUrl = "https://www.data.jma.go.jp/fcd/yoho/data/jishin/kaisetsu_tanki_latest.pdf";
        String url = "http://docs.google.com/gview?embedded=true&url=" + pdfUrl;

        webview.loadUrl(url);
        /*
        //webview.setWebViewClient(new WebViewClient() {
            boolean checkOnPageStartedCalled = false;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                checkOnPageStartedCalled = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (checkOnPageStartedCalled) {
                    pdfView.loadUrl(removePdfTopIcon);
                    hideProgress();
                } else {
                    showPdfFile(imageString);
                }
            }
        });

         */

        //linearLayout.addView(ctlLinearLayout, createParam(FP, WC));
        //linearLayout.addView(webview, createParam(WC, WC));
    }

    private LinearLayout.LayoutParams createParam(int w, int h){
        return new LinearLayout.LayoutParams(w, h);
    }

    public void onClick(View v) {
        SpannableStringBuilder url = (SpannableStringBuilder)textUrl.getText();
        webview.loadUrl(url.toString());
    }
}