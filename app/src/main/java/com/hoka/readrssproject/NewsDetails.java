package com.hoka.readrssproject;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;

    /*
    This activity opens the news in the WebView,
    we can view the complete news from the resource
     */

public class NewsDetails extends Activity {
    @BindView(R.id.webView_activity_news_details)
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
