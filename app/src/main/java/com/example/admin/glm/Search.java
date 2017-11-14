package com.example.admin.glm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class Search extends AppCompatActivity {
   private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        webView=(WebView)findViewById(R.id.search);
        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        webView.loadUrl("http://m.kuaidi100.com/index.jsp?from=openv");
    }
}
