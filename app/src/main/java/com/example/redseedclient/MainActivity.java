package com.example.redseedclient;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar pageProgress;

    @Override
    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        pageProgress = findViewById(R.id.pageProgress);

        WebView.setWebContentsDebuggingEnabled(true);

        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);

        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);

        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);

        settings.setMixedContentMode(
                WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        );

        webView.addJavascriptInterface(
                new AndroidBridge(),
                "AndroidApp"
        );

        webView.setWebChromeClient(
                new WebChromeClient() {
                    @Override
                    public boolean onConsoleMessage(
                            ConsoleMessage message
                    ) {
                        android.util.Log.d(
                                "REDSEED_WEBVIEW",
                                message.message()
                                        + " — line "
                                        + message.lineNumber()
                                        + " — "
                                        + message.sourceId()
                        );

                        return true;
                    }
                }
        );

        webView.setWebViewClient(
                new WebViewClient() {

                    @Override
                    public boolean shouldOverrideUrlLoading(
                            WebView view,
                            WebResourceRequest request
                    ) {
                        String url =
                                request.getUrl().toString();

                        view.loadUrl(url);

                        return true;
                    }

                    @Override
                    public void onPageStarted(
                            WebView view,
                            String url,
                            android.graphics.Bitmap favicon
                    ) {
                        super.onPageStarted(
                                view,
                                url,
                                favicon
                        );

                        if (pageProgress != null) {
                            pageProgress.setVisibility(
                                    View.VISIBLE
                            );
                        }
                    }

                    @Override
                    public void onPageFinished(
                            WebView view,
                            String url
                    ) {
                        super.onPageFinished(
                                view,
                                url
                        );

                        if (pageProgress != null) {
                            pageProgress.setVisibility(
                                    View.GONE
                            );
                        }
                    }
                }
        );

        getOnBackPressedDispatcher().addCallback(
                this,
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        if (webView.canGoBack()) {
                            webView.goBack();
                        } else {
                            finish();
                        }
                    }
                }
        );

        webView.loadUrl(
                "file:///android_asset/login-supabase.html"
        );
    }

    private class AndroidBridge {

        @JavascriptInterface
        public void showToast(String message) {
            runOnUiThread(() ->
                    Toast.makeText(
                            MainActivity.this,
                            message,
                            Toast.LENGTH_SHORT
                    ).show()
            );
        }
    }
}