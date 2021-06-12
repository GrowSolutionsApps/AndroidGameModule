package com.game.gamemodule.StaticData;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/*
    Created by Arti on 11/6/21
*/
public class Data {
    public static String Base_Url = "http://picturekeyboardapps.in/";
    public static String game_limit = "18";
    public static Context context;
    //    AdmobAdVar
    public static String isAdmobNativeAdShown = "isAdmobNativeAdShown";
    public static String isAbmobRewardedAdShown = "isAbmobRewardedAdShown";
    public static String isAbmobIntAdShown = "isAbmobIntAdShown";
    public static String defaultAdmobNativeAdShown = "true";
    public static String defaultAbmobRewardedAdShown = "true";
    public static String defaultintRewardedAdShown = "true";
    public static boolean checkAdVisible(String check) {
        return check.equals("true");
    }

    public static void setContext(Context con) {
        context = con;
    }

    public static Context getContext() {
        return context;
    }

    public static boolean isNetWorkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            return ni != null && ni.isConnected();
        }
        return false;
    }
}
