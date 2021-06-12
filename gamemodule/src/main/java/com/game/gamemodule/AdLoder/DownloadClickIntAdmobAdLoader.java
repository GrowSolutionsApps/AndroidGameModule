 /*
  * Copyright (C) 2019 Grow Solution
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *      http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */

/**
 * This is a part of the Emoji AddLoad Helper.
 */
 package com.game.gamemodule.AdLoder;

 import android.content.Context;
 import android.util.Log;

 import com.game.gamemodule.R;
 import com.google.android.gms.ads.AdListener;
 import com.google.android.gms.ads.InterstitialAd;

 /*
    Created by Arti on 11/6/21
*/
 public class DownloadClickIntAdmobAdLoader {
     public static adfinish myadfinish;
     //admob
     public static boolean isAdmobIntAdLoaded = false;
     public static boolean AdmobIntAdLoaded = false;
     public static InterstitialAd mAdmobInterstitialAd = null;
     public static boolean adclose = false;

     //Greedy Ads
//     public static GGInterstitialAd ggInterstitialAd = null;
//     public static boolean isggIntAdLoaded = false;
//     public static boolean ggIntAdLoaded = false;
//     public static boolean ggclose = false;


     public static void loadAd(Context context) {
         Log.w("msg", "splash Greedy  Admob Interstrial  Ad Loaded ");
         loadAdmobInt(context);
     }

     public static boolean isAdLoaded(Context activity) {
         Log.w("msg", "splash Greedy  Admob  Interstrial isAdLoaded  Ad Loaded " + isAdmobIntAdLoaded);
         if (isAdmobIntAdLoaded) {
//                 if (mAdmobInterstitialAd != null && mAdmobInterstitialAd.isLoaded()) {
             AdmobIntAdLoaded = true;
             return true;
         } else {
             AdmobIntAdLoaded = false;
             return false;
         }
     }


     public static void loadAdmobInt(final Context context) {
         Log.w("msg", "Greedy  Admob  loadAdmobInt ");

         try {
             mAdmobInterstitialAd = new InterstitialAd(context);
             String admob_int_ad_id = context.getString(R.string.admob_int_ad_id);
             mAdmobInterstitialAd.setAdUnitId(admob_int_ad_id);

             mAdmobInterstitialAd.loadAd(MediationHelper.getAdReqvest(MediationHelper.ADTYPEINT));
             mAdmobInterstitialAd.setAdListener(new AdListener() {
                 @Override
                 public void onAdLoaded() {
                     Log.w("msg", "splash admob adLoaded");
                     isAdmobIntAdLoaded = true;
                 }

                 @Override
                 public void onAdClosed() {
                     loadAdmobInt(context);
                 }

                 @Override
                 public void onAdFailedToLoad(int errorCode) {
                     isAdmobIntAdLoaded = false;
                     Log.w("msg", "splash admob : InterStrila ADs Load   " + errorCode);
                     loadAdmobIntReLload(context);
                 }

                 @Override
                 public void onAdLeftApplication() {
                 }

                 @Override
                 public void onAdOpened() {
                 }
             });
         } catch (OutOfMemoryError e) {

         }
     }


     public interface adfinish {
         void adfinished();
     }

     public static void onDestroy() {
     }

     public static void showAd(Context context, adfinish myadfinish1) {
//         if (Data.remoteConfig.getString(Data.app_ads_sdk).equals(Data.app_ads_check_var)) {
         Log.w("msg", "Greedy Admob Interstrial showAd ");
         myadfinish = myadfinish1;
         Log.w("msg", "showAd ");
         if (isAdmobIntAdLoaded) {
             //                 if (mAdmobInterstitialAd != null && mAdmobInterstitialAd.isLoaded()) {
             mAdmobInterstitialAd.setAdListener(new AdListener() {

                 @Override
                 public void onAdLoaded() {
                     super.onAdLoaded();
                     mAdmobInterstitialAd.show();
                     myadfinish.adfinished();
                 }

                 @Override
                 public void onAdFailedToLoad(int i) {
                     super.onAdFailedToLoad(i);
                     myadfinish.adfinished();
                 }

                 @Override
                 public void onAdClosed() {
                     super.onAdClosed();
                     myadfinish.adfinished();
                 }
             });
             mAdmobInterstitialAd.show();
         } else {
             adclose = true;
             myadfinish.adfinished();
         }
     }

     public static void loadAdmobIntReLload(final Context context) {
         Log.w("msg", "admob : InterStrila ReLoad   ");

         try {
             mAdmobInterstitialAd = new InterstitialAd(context);
             String admob_int_ad_id = context.getString(R.string.back_admob_int_ad_id);
             mAdmobInterstitialAd.setAdUnitId(admob_int_ad_id);

             mAdmobInterstitialAd.loadAd(MediationHelper.getAdReqvest(MediationHelper.ADTYPEINT));
             mAdmobInterstitialAd.setAdListener(new AdListener() {
                 @Override
                 public void onAdLoaded() {
                     Log.w("msg", "splash admob readLoaded");

                     isAdmobIntAdLoaded = true;
                 }

                 @Override
                 public void onAdClosed() {
                     loadAdmobIntReLload(context);
                 }

                 @Override
                 public void onAdFailedToLoad(int errorCode) {
                     isAdmobIntAdLoaded = false;
                     Log.w("msg", "splash admob : InterStrila ReLoad   " + errorCode);
                 }

                 @Override
                 public void onAdLeftApplication() {
                 }

                 @Override
                 public void onAdOpened() {
                 }
             });
         } catch (OutOfMemoryError e) {

         }
     }

//     public static void greedyloadAd(Context context) {
//         ggInterstitialAd = new GGInterstitialAd(context, context.getString(R.string.greedy_int));
//         ggInterstitialAd.loadAd(new GGInterstitialEventsListener() {
//             @Override
//             public void onAdLeftApplication() {
//
//             }
//
//             @Override
//             public void onAdClosed() {
//                 greedyloadAd(context);
//             }
//
//             @Override
//             public void onAdOpened() {
//                 //Called when ad has been opened
//
//             }
//
//             @Override
//             public void onAdLoadFailed(AdRequestErrors cause) {
//                 Log.w("msg", "Greedy  Interstrial Ad onAdLoadFailed 2 " + cause.toString());
//
//                 isggIntAdLoaded = false;
//             }
//
//             @Override
//             public void onAdLoaded() {
//                 isggIntAdLoaded = true;
//             }
//         });
//     }


 }
