package com.thecrackertechnology.andrax;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;




import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.security.AccessController.getContext;


public class POSTFuzzer extends AppCompatActivity {


    WebView web;
    String website;
    WebView webcodeview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postfuzzer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Bundle extras = getIntent().getExtras();
        if(extras !=null){
            website = extras.getString("HOSTKEY");
        } else {
            website = "http://www.google.com";
        }

        Button btnfuzzerit = (Button) findViewById(R.id.buttonfuzzerit);

        web = (WebView) findViewById(R.id.webviewpostfuzzer);

        webcodeview = (WebView) findViewById(R.id.webviewpostfuzzercode);


        webcodeview.getSettings().setJavaScriptEnabled(true);
        webcodeview.getSettings().setAllowContentAccess(true);
        webcodeview.setVerticalScrollBarEnabled(true);
        webcodeview.setHorizontalScrollBarEnabled(true);
        webcodeview.canGoBack();

        webcodeview.setWebChromeClient(new WebChromeClient());


        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setAllowContentAccess(true);
        web.setVerticalScrollBarEnabled(true);
        web.setHorizontalScrollBarEnabled(true);
        web.canGoBack();

        web.setWebChromeClient(new WebChromeClient());


        web.setWebViewClient(new myWebclient() {
            @SuppressLint("NewApi")
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {


                return null;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                Log.d("WebView", "your current url when webpage loading.." + url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(web, url);


            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            }
        });

        web.loadUrl(website);

        /**
         *
         * Help me, i'm dying...
         *
         **/


        btnfuzzerit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent intent = new Intent(POSTFuzzer.this, FuzzerPostATTACK.class);
                intent.putExtra("HOSTKEYtwo", website);
                startActivity(intent);


            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.postfuzzer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        int id = item.getItemId();


        if (id == R.id.startpostfuzzer) {

            try {

                Process process = Runtime.getRuntime().exec("su -c /data/data/com.thecrackertechnology.andrax/ANDRAX/bin/postfuzzerdump");
                process.waitFor();
            } catch (IOException e) {
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else if (id == R.id.stoppostfuzzer) {

            try {

                Process process = Runtime.getRuntime().exec("su -c killall tcpdump");
                process.waitFor();
            } catch (IOException e) {
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {

                Process process = Runtime.getRuntime().exec("su -c cat /data/data/com.thecrackertechnology.andrax/ANDRAX/output | sed 's/^.*POST/POST/' | tail -n+5 | head -n-4 | sed '/Accept:/d'");



                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));
                int read;
                char[] buffer = new char[8000];
                StringBuffer output = new StringBuffer();
                while ((read = reader.read(buffer)) > 0) {
                    output.append(buffer, 0, read );
                }
                reader.close();


                process.waitFor();



                /*Codeview.with(getApplicationContext())
                        .withCode(output.toString())
                        .setStyle(Settings.WithStyle.XT256)
                        .setLang(Settings.Lang.JAVASCRIPT)
                        .into(webcodeview); */

                String myHtmlString = "<html><head>" +
                        "<link rel=\"stylesheet\" href=file:///android_asset/xt256.css>" +
                        "<script src=file:///android_asset/highlight.pack.js></script>" +
                        "<script>hljs.initHighlightingOnLoad();</script>" +
                        "</head><body>" +
                        "<pre><code class=\"http\">" + output.toString() + "</code></pre>" +
                        "</body></html>";
                webcodeview.loadDataWithBaseURL(null, myHtmlString, "text/html", "UTF-8", null);


            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }

        return super.onOptionsItemSelected(item);
    }




    private class myWebclient extends WebViewClient {

    }

    @Override
    public void onBackPressed() {
        if (web.canGoBack()) {
            web.goBack();
        } else {
            super.onBackPressed();
        }
    }




}







