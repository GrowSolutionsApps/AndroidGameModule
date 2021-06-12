package com.game.gamemodule.AdLoder;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.game.gamemodule.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class BannerAdLoader {
    public static void loadAdmob_BannerADs(final Activity activity) {
        AdView mAdView = new AdView(activity);
        mAdView.setAdSize(com.google.android.gms.ads.AdSize.BANNER);
        String admob_banner;
        admob_banner = activity.getString(R.string.admob_banner);
        mAdView.setAdUnitId(admob_banner);
        FrameLayout frameLayout = (FrameLayout) activity.findViewById(R.id.admob_banner);
        frameLayout.addView(mAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                frameLayout.setVisibility(View.VISIBLE);
                mAdView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.w("msg", "admob : BannerADs  Game  " + errorCode);
                mAdView.setVisibility(View.GONE);
                frameLayout.setVisibility(View.GONE);
                loadAdmob_BannerADsReLoad(activity);
            }

            @Override
            public void onAdOpened() {
            }

            @Override
            public void onAdClicked() {
            }

            @Override
            public void onAdLeftApplication() {
            }

            @Override
            public void onAdClosed() {
            }
        });
    }

    public static void loadAdmob_BannerADsReLoad(final Activity activity) {
        AdView mAdView = new AdView(activity);
        mAdView.setAdSize(com.google.android.gms.ads.AdSize.BANNER);
        String admob_banner;
        admob_banner = activity.getString(R.string.admob_banner);
        mAdView.setAdUnitId(admob_banner);
        FrameLayout frameLayout = (FrameLayout) activity.findViewById(R.id.admob_banner);
        frameLayout.addView(mAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                frameLayout.setVisibility(View.VISIBLE);
                mAdView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                mAdView.setVisibility(View.GONE);
                frameLayout.setVisibility(View.GONE);
                Log.w("msg", "admob : BannerADsReLoad Game Error " + errorCode);

            }

            @Override
            public void onAdOpened() {
            }

            @Override
            public void onAdClicked() {
            }

            @Override
            public void onAdLeftApplication() {
            }

            @Override
            public void onAdClosed() {
            }
        });
    }
}
