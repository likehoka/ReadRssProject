package com.hoka.readrssproject;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetails extends Activity {

    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        webview.loadUrl(bundle.getString("Link"));
    }
}
