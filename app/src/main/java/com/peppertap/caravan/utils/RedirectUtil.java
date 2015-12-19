package com.peppertap.caravan.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.util.List;

/**
 * Created by navratansoni on 18/11/15.
 */
public class RedirectUtil {

    private static final String PLAY_STORE_ID = "com.peppertap.partners";

    public static void redirectToPlayStoreForRating(Context mContext) {

        Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + PLAY_STORE_ID));
        boolean isPlayStoreAppPresent = false;

        // Need to directly open play store app only as more apps may have handled market:// Urls
        final List<ResolveInfo> otherApps = mContext.getPackageManager().queryIntentActivities(rateIntent, 0);
        for (ResolveInfo otherApp : otherApps) {
            if (otherApp.activityInfo.applicationInfo.packageName.equals("com.android.vending")) {

                ActivityInfo otherAppActivity = otherApp.activityInfo;
                ComponentName componentName = new ComponentName(
                        otherAppActivity.applicationInfo.packageName,
                        otherAppActivity.name
                );
                rateIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                rateIntent.setComponent(componentName);
                isPlayStoreAppPresent = true;
                break;
            }
        }
        // if GooglePlay not present on device, open web browser
        rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + PLAY_STORE_ID));

        mContext.startActivity(rateIntent);
    }

}
