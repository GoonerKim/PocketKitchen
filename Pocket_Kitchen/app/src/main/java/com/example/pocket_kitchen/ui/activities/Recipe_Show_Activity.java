package com.example.pocket_kitchen.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.pocket_kitchen.R;

public class Recipe_Show_Activity extends AppCompatActivity {

    WebView webView;
    String recipeUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe__show);

        Intent intent = getIntent();
        recipeUri = intent.getStringExtra("recipeUri");

        webView = (WebView)findViewById(R.id.recipeView);

        webView.loadUrl(recipeUri);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        }
        else{
            super.onBackPressed();
        }
    }


    /*private class WebViewClientClass extends WebViewClient {//페이지 이동
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("check URL",url);
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }*/


}
