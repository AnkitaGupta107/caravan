package com.peppertap.caravan.network.volley;

import android.app.Activity;

import com.google.gson.JsonArray;
import com.peppertap.caravan.utils.JsonHelper;

import timber.log.Timber;

/**
 * Created by samvedana on 16/6/15.
 */
public class MyJsonArrayRequest extends BaseRequest<JsonArray> {

    public JsonArray convertStringResponseToRequiredFormat(String response){
        if(response == null){
            Timber.e("response null received from server");
        }
        return JsonHelper.StringToJsonArray(response);
    }

    public MyJsonArrayRequest(Activity activity, String url,
                               ResponseListener<JsonArray> responseListener) {
        super(activity, url, responseListener);
    }
}
