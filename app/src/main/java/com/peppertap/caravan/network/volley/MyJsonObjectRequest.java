package com.peppertap.caravan.network.volley;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.google.gson.JsonObject;
import com.peppertap.caravan.utils.JsonHelper;

import java.util.Map;

import timber.log.Timber;

/**
 * Created by samvedana on 16/6/15.
 */
public class MyJsonObjectRequest extends BaseRequest<JsonObject> {


    public MyJsonObjectRequest(Fragment fragment, String url,
                               ResponseListener<JsonObject> responseListener) {
        super(fragment, url, responseListener);
    }

    public MyJsonObjectRequest(Fragment fragment, String url, Map<String, String> params,
                                ResponseListener<JsonObject> responseListener) {
        super(fragment, url, params, responseListener);
    }

    public MyJsonObjectRequest(Activity activity, String url, Map<String, String> params,
                               ResponseListener<JsonObject> responseListener) {
        super(activity, url, params, responseListener);
    }

    public JsonObject convertStringResponseToRequiredFormat(String response) {
        if (response == null) {
            Timber.e("response null received from server");
        }
        return JsonHelper.StringToJsonObject(response);
    }

}
