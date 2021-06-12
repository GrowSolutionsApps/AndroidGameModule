package com.game.gamemodule.AdLoder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.game.gamemodule.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;


public class RewardedAdLoader {
    public static RewardedVideoAd mRewardedVideoAd;
    public static boolean isVideoAdLoaded;

    public interface mCallBack {
        public void done(boolean isReworded);
    }

    public static void loadAd(Context context, boolean all_admob_isEnabled) {
        if (all_admob_isEnabled) {
//        if (mRewardedVideoAd != null) {
            mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);
            loadRewardedVideoAd(context);
//        }
        }
    }

    public static boolean isAdLoaded(Context context) {
        if (mRewardedVideoAd != null && mRewardedVideoAd.isLoaded()) {
            return true;
        } else {
            return false;
        }

    }

    public static void destroyAd(Context context) {
        if (mRewardedVideoAd != null) {
            mRewardedVideoAd.destroy(context);
        }
        mRewardedVideoAd = null;
    }

    static boolean isRewarded;

    public static void showVideoAd(Context context, final mCallBack mCallBack) {
        isRewarded = false;
        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
            }

            @Override
            public void onRewardedVideoAdOpened() {
            }

            @Override
            public void onRewardedVideoStarted() {
            }

            @Override
            public void onRewardedVideoAdClosed() {
                loadRewardedVideoAd(context);
                Log.w("msg", "onRewardedVideoAdClosed");
                mCallBack.done(isRewarded);
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                Log.w("msg", "onRewarded");
                isRewarded = true;
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
            }

            @SuppressLint("WrongConstant")
            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                Toast.makeText(context, "Video AD is not available ... please try again", 1).show();
                mCallBack.done(isRewarded);
            }

            @Override
            public void onRewardedVideoCompleted() {
            }
        });
        mRewardedVideoAd.show();
    }

    public static void loadRewardedVideoAd(Context context) {
        AdRequest adRequest = new AdRequest.Builder().build();
        String admob_rewarded = context.getString(R.string.admob_rewarded_ad_id);
        mRewardedVideoAd.loadAd(admob_rewarded, adRequest);
        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {

            @Override
            public void onRewardedVideoAdLoaded() {
                Log.w("msg", "admob : NativeADs  loaded   Reward  load  ");

            }

            @Override
            public void onRewardedVideoAdOpened() {
            }

            @Override
            public void onRewardedVideoStarted() {
            }

            @Override
            public void onRewardedVideoAdClosed() {
                loadRewardedVideoAd(context);
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                isRewarded = true;
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                Log.w("msg", "admob : NativeADs  loaded  Reward error  " + i);

                isRewarded = false;
                loadRewardedVideoAdReLoad(context);
            }

            @Override
            public void onRewardedVideoCompleted() {
            }
        });
    }

    public static void loadRewardedVideoAdReLoad(Context context) {
        AdRequest adRequest = new AdRequest.Builder().build();
        String admob_rewarded = context.getString(R.string.back_admob_rewarded_ad_id);

        mRewardedVideoAd.loadAd(admob_rewarded, adRequest);
        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {

            @Override
            public void onRewardedVideoAdLoaded() {
                Log.w("msg", "admob : NativeADsReloaded  Reward 2 ");

            }

            @Override
            public void onRewardedVideoAdOpened() {
            }

            @Override
            public void onRewardedVideoStarted() {
            }

            @Override
            public void onRewardedVideoAdClosed() {
                loadRewardedVideoAdReLoad(context);
                Log.w("msg", "admob : NativeADsReloaded  Reward 1 ");

            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                isRewarded = true;
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                Log.w("msg", "admob : NativeADsReloaded  Reward  " + i);

                isRewarded = false;
            }

            @Override
            public void onRewardedVideoCompleted() {
            }
        });
    }
}
