package com.peppertap.caravan.network;

import com.peppertap.caravan.BuildConfig;

/**
 * Created by gladiator on 2/9/14.
 */

public class Urls {
    private static String small_image_url_staging = null;

    public static final String URL_BASE = "https://api-stg.peppertap.com";


    private static final String SMALL_IMAGE_URL = "http://peppertap.s3.amazonaws.com/images/product/%s/0/%s_150x150.jpg";

    private static final String SMALL_IMAGE_URL_STAGING = "http://peppertapstaging.s3.amazonaws.com/images/product/%s/0/%s_150x150.jpg";

    public static String getBaseUrl() {
        return URL_BASE;
    }

    public static String getSmallImageUrl() {
        if (small_image_url_staging == null) {
            small_image_url_staging = SMALL_IMAGE_URL_STAGING;
        }
        return small_image_url_staging;
    }

    public static String getAppendedMediaUrl(String uid) {
        return String.format(getSmallImageUrl(), uid, uid);
    }

}
