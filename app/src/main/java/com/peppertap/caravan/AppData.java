package com.peppertap.caravan;

import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * Created by samvedana on 19/12/15.
 */
public class AppData {
    //variables to be saved across restarts

    String customer_name = "Country Inn, Nainital";
    String session_id = "i3nk0tluvfw4p2t";
    String zone_id = "1";

    //application lifetime variables

    public static class Keys {
    }

    public AppData() {

    }

    public void clearAppData() {

    }

    public String getCustomerName() {
        return customer_name;
    }

    public String getSession_id() {
        return session_id;
    }

    public String getZone_id() {
        return zone_id;
    }
}
