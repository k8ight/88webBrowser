package netai.webbrowser.WebBrowser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.parseColor;
import static android.hardware.camera2.params.RggbChannelVector.GREEN_EVEN;
import static android.hardware.camera2.params.RggbChannelVector.RED;


public class MainActivity extends Activity {
    String infocert;
    String currentUrl;
    WebView web1;
    EditText ed1;
    Button bt1;
    Button bt2;
    Button bt3;
    Button bt4;
    Button bt5;
    Button bt6;
    Button back;
    Button Refresh;
    Button fwd;
    String Address;
    int keyCode;
    String add;
    String tbar;
    String search;
    ProgressBar pbar;
    ArrayAdapter<String> listAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HashMap<String, String> extraHeaders = new HashMap<String, String>();
        extraHeaders.put("DNT", "1");

        web1 = (WebView) findViewById(R.id.webview);
        ed1 = (EditText) findViewById(R.id.editText);
        bt2 = (Button) findViewById(R.id.button4);
        bt3 = (Button) findViewById(R.id.button2);
        bt4=(Button)findViewById(R.id.button3);
        bt5=(Button)findViewById(R.id.button5);
        bt6=(Button)findViewById(R.id.button7);
        pbar = (ProgressBar) findViewById(R.id.progressBar1);
        pbar.setVisibility(View.GONE);
        Refresh=(Button)findViewById(R.id.refresh);
        fwd=(Button)findViewById(R.id.fwd);
        back=(Button)findViewById(R.id.back);

        WebSettings webSetting = web1.getSettings();
        webSetting.setBuiltInZoomControls(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setDisplayZoomControls(false);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setUseWideViewPort(true);
        web1.setWebViewClient(new WebViewClient());
        web1.clearHistory();

        Address = "file:///android_asset/indexx.html";
        web1.loadUrl(Address,extraHeaders);
        web1.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.setMimeType(mimetype);
                request.addRequestHeader("cookie", CookieManager.getInstance().getCookie(url));
                request.addRequestHeader("User-Agent", userAgent);
                request.addRequestHeader("DNT", "1");
                request.setDescription("Downloading file...");
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(1);
                ((DownloadManager) MainActivity.this.getSystemService(DOWNLOAD_SERVICE)).enqueue(request);
                Toast.makeText(MainActivity.this.getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();


            }
        });



        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed1.getText().toString().contains("https://")) {
                    Toast.makeText(MainActivity.this.getApplicationContext(), "Secure Site", Toast.LENGTH_LONG).show();
                } else if (currentUrl.contains("indexx")) {
                    Toast.makeText(MainActivity.this.getApplicationContext(), "secure Site", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this.getApplicationContext(), "Unsecure Site", Toast.LENGTH_LONG).show();
                }

            }
        });


        bt3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Address = "file:///android_asset/indexx.html";
                web1.loadUrl(Address);
            }
        });
        ed1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if((actionId == EditorInfo.IME_ACTION_SEARCH) || (keyCode == KeyEvent.ACTION_DOWN)){
                    HashMap<String, String> extraHeaders = new HashMap<String, String>();
                    extraHeaders.put("DNT", "1");
                    if (ed1.getText().toString().contains("http://")) {
                        Address = ed1.getText().toString();
                        web1.loadUrl(Address,extraHeaders);
                        bt2.setBackgroundColor(parseColor("#FF0000"));
                    } else if (ed1.getText().toString().contains("https://")) {

                        bt2.setBackgroundColor(parseColor("#3AB03A"));
                        Address = ed1.getText().toString();
                        web1.loadUrl(Address,extraHeaders);
                    } else if (ed1.getText().toString().contains(".")) {
                        Address = "http://" + ed1.getText().toString();
                        web1.loadUrl(Address,extraHeaders);
                        bt2.setBackgroundColor(parseColor("#FF0000"));


                    } else {
                        Address = "https://duckduckgo.com/?q=" + ed1.getText().toString()+"&mode=Privacy";
                        web1.loadUrl(Address,extraHeaders);
                        if (web1.getUrl().contains("https")) {
                            bt2.setBackgroundColor(parseColor("#3AB03A"));
                        } else {
                            bt2.setBackgroundColor(parseColor("#FF0000"));


                        }
                    }
                }

                return true;
            }
            });

        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bt5.getVisibility() == View.INVISIBLE){
                    bt5.setVisibility(View.VISIBLE);
                    bt6.setVisibility(View.VISIBLE);
                    fwd.setVisibility(View.VISIBLE);
                    back.setVisibility(View.VISIBLE);
                    fwd.setVisibility(View.VISIBLE);
                    Refresh.setVisibility(View.VISIBLE);
                }
                else{
                    bt6.setVisibility(View.INVISIBLE);
                    bt5.setVisibility(View.INVISIBLE);
                    back.setVisibility(View.INVISIBLE);
                    fwd.setVisibility(View.INVISIBLE);
                    Refresh.setVisibility(View.INVISIBLE);
                }
            }
        });
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));

            }
        });
        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
            }
        });
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(web1.canGoBack()){
                    web1.goBack();
                }
            }
        });
        fwd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(web1.canGoForward()){
                    web1.goForward();
                }
            }
        });
        Refresh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                web1.reload();
            }
        });
        }
    public class WebViewClient extends android.webkit.WebViewClient
    {


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            /* Log.d("WebView", "" + url);*/
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            pbar.setVisibility(View.VISIBLE);
            web1.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {

                    pbar.setProgress(progress);
                }
            });
        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            HashMap<String, String> extraHeaders = new HashMap<String, String>();
            extraHeaders.put("DNT", "1");
            // TODO Auto-generated method stub
            view.loadUrl(url,extraHeaders);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
          web1.clearCache(true);
            // TODO Auto-generated method stub
            /* Log.d("WebView", "" + url);*/
            currentUrl=web1.getOriginalUrl().toString();
            if(currentUrl.contains("indexx")){
                ed1.setText("");
            }
            else{
                ed1.setText(currentUrl);
            }
                        if(currentUrl.contains("https")) {
                bt2.setBackgroundColor(parseColor("#3AB03A"));
            }
            else if(currentUrl.contains("indexx")){
                            bt2.setBackgroundColor(parseColor("#3AB03A"));
            }
            else {
                bt2.setBackgroundColor(parseColor("#FF0000"));

            }

            super.onPageFinished(view, url);
            pbar.setVisibility(View.GONE);

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && web1.canGoBack()) {
            web1.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}
