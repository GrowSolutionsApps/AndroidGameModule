package com.game.gamemodule.View;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.game.gamemodule.AdLoder.BannerAdLoader;
import com.game.gamemodule.AdLoder.DownloadClickIntAdmobAdLoader;
import com.game.gamemodule.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/*
    Created by Arti on 11/6/21
*/
public class GameActivity extends AppCompatActivity /*implements AdvancedWebView.Listener*/ {
    RelativeLayout progress_view;
    ProgressBar simpleProgressBar;
    TextView txt_progress;
    WebView myWebView;
    String link;
    FrameLayout bannerUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);
        hidenavView();
        findViewByIds();
        initValue();
        loadAds();
        LoadGame(link);

    }

    private void hidenavView() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void loadAds() {
        DownloadClickIntAdmobAdLoader.loadAd(this);
    }

    private void initValue() {
        link = getIntent().getStringExtra("game_link");
        Log.w("msg", "Link== " + link);
        String orientation = getIntent().getStringExtra("orientation");

        assert orientation != null;
        if (orientation.equalsIgnoreCase("landscape")) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            BannerAdLoader.loadAdmob_BannerADs(this);
        }
    }

    private void findViewByIds() {
        simpleProgressBar = findViewById(R.id.simpleProgressBar);
        progress_view = findViewById(R.id.progress_view);
        txt_progress = findViewById(R.id.progress);
        myWebView = findViewById(R.id.webview);
        bannerUnit = findViewById(R.id.bannerUnit);
    }

    @Override

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        myWebView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        myWebView.restoreState(savedInstanceState);
    }




    @SuppressLint("SetJavaScriptEnabled")
    private void LoadGame(String link) {
        myWebView.clearCache(true);
        myWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        myWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        myWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setDomStorageEnabled(true);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setDisplayZoomControls(false);
        myWebView.getSettings().setSupportZoom(true);
        myWebView.getSettings().setDefaultTextEncodingName("utf-8");
        myWebView.setInitialScale(1);
        myWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        myWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        myWebView.setPadding(0, 0, 0, 0);
        myWebView.setScrollbarFadingEnabled(true);
        myWebView.setVerticalScrollBarEnabled(false);
        myWebView.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
        myWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        myWebView.setLongClickable(false);
        myWebView.loadUrl(link);
        myWebView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                view.setVisibility(View.VISIBLE);
                progress_view.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
            }

        });
        myWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                simpleProgressBar.setProgress(progress);
                txt_progress.setText(progress + " %");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        hidenavView();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Exit");
        alertDialog.setMessage("Are you sure you want to exit?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                myWebView.destroy();
                if (DownloadClickIntAdmobAdLoader.isAdLoaded(GameActivity.this)) {
                    DownloadClickIntAdmobAdLoader.showAd(GameActivity.this, new DownloadClickIntAdmobAdLoader.adfinish() {
                        @Override
                        public void adfinished() {
                            DownloadClickIntAdmobAdLoader.loadAd(GameActivity.this);
                            finish();
                        }
                    });
                } else {
                    finish();
                }
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Resume", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Retry", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                LoadGame(link);
            }
        });
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        myWebView.destroy();
        super.onDestroy();
    }
}