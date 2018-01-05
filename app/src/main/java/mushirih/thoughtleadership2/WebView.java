package mushirih.thoughtleadership2;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;


/**
 * Created by mushirih on 28/12/2017.
 */

public class WebView extends AppCompatActivity {
    String link = "https://xpatlink.info/index.php/xpressiontest/24-entertainment/23-";

    android.webkit.WebView webView;
    ProgressDialog loading;
    String cost = "033";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loading = ProgressDialog.show(this, null, "Fetching content", true, false);
        setContentView(R.layout.webview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setIcon(R.drawable.xpat_link_logo);
        getSupportActionBar().setTitle("Xpression");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        webView = (android.webkit.WebView) findViewById(R.id.webview);
        webView.setVisibility(View.GONE);
        webView.getSettings().setAppCacheMaxSize(5 * 1024 * 1024); // 5MB
        webView.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);

        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // load online by default

        if (!isNetworkAvailable()) { // loading offline
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(android.webkit.WebView view, String url) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    webView.evaluateJavascript("window.onload=document.getElementsByClassName('mid_header')[0].style.display='none';" +
                                    "window.onload=document.getElementsByClassName('container')[2].style.display='none';" +
                                    "window.onload=document.getElementsByClassName('container')[0].style.display='none';",

                            new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String s) {
                                    webView.setVisibility(View.VISIBLE);
                                    loading.dismiss();
                                }
                            });

                }


            }
        });

        //webView.loadUrl("https://google.com");
        webView.loadUrl(link);
    }
        private boolean isNetworkAvailable() {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

}