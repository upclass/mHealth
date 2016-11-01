package org.caller.mhealth;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import org.caller.mhealth.webs.BrowserSupport;
import org.caller.mhealth.webs.DiseaseWebViewChromeClient;

public class DiseaseWebActivity extends AppCompatActivity implements BrowserSupport {

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_web);
        String url = getIntent().getStringExtra("url");
        Log.d("123456", "onCreate: " + url);
        if (url != null) {
            WebView webView = (WebView) findViewById(R.id.disease_web_webview);
            mProgressBar = (ProgressBar) findViewById(R.id.disease_detail_web_progress_bar);
            webView.setWebViewClient(new WebViewClient());
            webView.setWebChromeClient(new DiseaseWebViewChromeClient(this));
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            webView.loadUrl(url);
        }

    }

    @Override
    public void onProgressChanged(WebView webView, int newProgress) {
        mProgressBar.setProgress(newProgress);
    }

}
