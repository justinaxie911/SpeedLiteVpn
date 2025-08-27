/*
 * Created by ZacDevz Official VPN on 27/01/24 22:59
 *  Copyright (c) Telegram: @ZacDevzOfficial1 . All rights reserved.
 */
package com.speedlite.vpn;

import android.content.Context;
        import android.content.Intent;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.os.Bundle;
        import androidx.appcompat.widget.Toolbar;


        import android.app.Activity;
        import android.graphics.Bitmap;
        import android.view.KeyEvent;
        import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.ProgressBar;
        import android.widget.RelativeLayout;
        import android.widget.Toast;


public class speed extends AppCompatActivity {
    WebView web;
    ProgressBar progressBar;

    private Toolbar toolbar;
    private ImageView mButtonSet;


    //SIN INTERNET
    private Button reload;
    RelativeLayout successlayout;
    LinearLayout nointernetlayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speed);


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
       setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        successlayout=findViewById(R.id.internetLayout);
        nointernetlayout=findViewById(R.id.nointernetLayout);
        reload=findViewById(R.id.reloadid);

        web = (WebView) findViewById(R.id.webview01);
       progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        web.setWebViewClient(new myWebClient());
        web.getSettings().setJavaScriptEnabled(true);

        loadWebPage();


        // SIN INTERNET
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReconectWebSite();
                    nointernetlayout.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
            }
        });






    }

    private void ReconectWebSite() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }


    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);



        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            loadWebPage();

            return true;

        }


        @Override
        public void onReceivedError(WebView view, int errorCod,String description, String failingUrl) {

            nointernetlayout.setVisibility(View.VISIBLE);
            successlayout.setVisibility(View.GONE);

            Toast.makeText(getApplicationContext(), "Sin Conexión a INTERNET, Active el internet e intente de nuevo " + description , Toast.LENGTH_LONG).show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }

    // To handle "Back" key press event for WebView to go back to previous screen.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
            web.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // SIN INTERNET
    private void loadWebPage(){
        ConnectivityManager cm= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();


        if (info !=null){

            if(info.isConnected()){
                nointernetlayout.setVisibility(View.GONE);
                web.loadUrl("http://puntonet.speedtestcustom.com/");
                progressBar.setVisibility(View.GONE);
                successlayout.setVisibility(View.VISIBLE);
                //web.reload();

            }else {
                nointernetlayout.setVisibility(View.VISIBLE);
                successlayout.setVisibility(View.GONE);
                reload.setVisibility(View.VISIBLE);
            }
        }else {
            nointernetlayout.setVisibility(View.VISIBLE);
            successlayout.setVisibility(View.GONE);
            reload.setVisibility(View.VISIBLE);

        }


    }

    private void ReconectWebSite(View view) {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);

    }

}
