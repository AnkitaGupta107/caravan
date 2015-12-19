package com.peppertap.caravan.network;

import com.peppertap.caravan.BuildConfig;
import com.peppertap.caravan.data.ProductHelper;

import java.net.URLEncoder;

/**
 * Created by gladiator on 2/9/14.
 */

public class Urls {
    private static String small_image_url_staging = null;
    private static String search_url = null;

    public static final String URL_BASE = "https://api-stg.peppertap.com";
    //public static final String URL_BASE = "http://app01.peppertap.com:8001/";

    private static final String SMALL_IMAGE_URL = "http://peppertap.s3.amazonaws.com/images/product/%s/0/%s_150x150.jpg";

    private static final String SMALL_IMAGE_URL_STAGING = "http://peppertapstaging.s3.amazonaws.com/images/product/%s/0/%s_150x150.jpg";

    private static final String SEARCH_URL_NEW = "/products/search/";

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

    public static String getSearchUrl(String search_text, String session_id, String zone_id) {
        String temp = "";
        if(search_url == null){
            search_url = SEARCH_URL_NEW;
        }
        try{
            temp = getBaseUrl() + search_url + "?q=" + URLEncoder.encode(search_text, "utf-8") + "&zone_id=" + zone_id + "&format=json" + "&session_id=" + session_id;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return temp;
    }

    public static String getAppendedMediaUrl(ProductHelper.ProductItem x){
        return String.format(getSmallImageUrl(),x.getUid(), x.getUid());
    }

}
