package com.peppertap.caravan.network.volley;

import android.app.Activity;
import android.support.v4.app.Fragment;

import java.util.Map;

import timber.log.Timber;

/**
 * Created by samvedana on 16/6/15.
 */
public class MyStringRequest extends BaseRequest<String> {


    public MyStringRequest(Fragment fragment, String url,
                           ResponseListener<String> responseListener) {
        super(fragment, url, responseListener);
    }

    public MyStringRequest(Fragment fragment, String url, Map<String, String> params,
                           ResponseListener<String> responseListener) {
        super(fragment, url, params, responseListener);
    }

    public MyStringRequest(Activity activity, String url, Map<String, String> params,
                           ResponseListener<String> responseListener) {
        super(activity, url, params, responseListener);
    }

    public String convertStringResponseToRequiredFormat(String response) {
        if (response == null) {
            Timber.e("response null received from server");
        }
        return "";
    }
}
