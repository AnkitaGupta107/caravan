package com.peppertap.caravan;

import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * Created by samvedana on 19/12/15.
 */
public class AppData {
    //variables to be saved across restarts
    String username;
    String password;

    String customer_name = "Country Inn, Nainital";

    //application lifetime variables

    public static class Keys {
        public static final String USERNAME = "Username";
        public static final String PASSWORD = "Password";
    }

    public AppData() {

    }

    public void clearAppData() {

    }

    public String getCustomerName() {
        return customer_name;
    }
}
