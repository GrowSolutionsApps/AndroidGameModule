package com.game.gamemodule.AdLoder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.gamemodule.R;
import com.game.gamemodule.StaticData.Data;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

/*
    Created by Arti on 11/6/21
*/
public class NativeAdLoader {

    public static void mo32103a(String str, FrameLayout frameLayout, UnifiedNativeAdView unifiedNativeAdView) {
        AdLoader.Builder aVar = new AdLoader.Builder(Data.getContext(), str);
        aVar.forUnifiedNativeAd((UnifiedNativeAd.OnUnifiedNativeAdLoadedListener) new C8991c(unifiedNativeAdView));
        unifiedNativeAdView.findViewById(R.id.txt_ad_loading_lbl).setVisibility(0);
        unifiedNativeAdView.findViewById(R.id.ll_adview).setVisibility(8);
        frameLayout.removeAllViews();
        frameLayout.addView(unifiedNativeAdView);
        aVar.withAdListener((AdListener) new C8992d(Data.getContext()));
        aVar.build().loadAd(new AdRequest.Builder().build());
    }
}

class C8992d extends AdListener {
    C8992d(Context dVar) {
    }

    public void onAdFailedToLoad(int i) {
        Log.w("msg", "Ad Failed== " + i);
    }
}

class C8991c implements UnifiedNativeAd.OnUnifiedNativeAdLoadedListener {

    final /* synthetic */ UnifiedNativeAdView f33527b;

    C8991c(UnifiedNativeAdView unifiedNativeAdView) {
        this.f33527b = unifiedNativeAdView;
    }

    @Override
    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
        Log.w("msg", "unifiedNativeAd=== " + unifiedNativeAd);
        this.f33527b.findViewById(R.id.txt_ad_loading_lbl).setVisibility(8);
        this.f33527b.findViewById(R.id.ll_adview).setVisibility(0);
        populateNativeAdView(unifiedNativeAd, this.f33527b);
    }


    private void populateNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        Log.w("msg", "txt=== " + nativeAd.getHeadline());
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((TextView) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

       /* if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
*/
        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd);

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
       /* VideoController vc = nativeAd.getVideoController();

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {
            videoStatus.setText(String.format(Locale.getDefault(),
                    "Video status: Ad contains a %.2f:1 video asset.",
                    vc.getAspectRatio()));

            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                    refresh.setEnabled(true);
                    videoStatus.setText("Video status: Video playback has ended.");
                    super.onVideoEnd();
                }
            });
        } else {
            videoStatus.setText("Video status: Ad does not contain a video asset.");
            refresh.setEnabled(true);
        }*/
    }
}

