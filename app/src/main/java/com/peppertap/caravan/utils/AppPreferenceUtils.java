package com.peppertap.caravan.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.peppertap.caravan.CaravanApp;
import com.peppertap.caravan.R;

public class AppPreferenceUtils {
    static Context appContext;
    static String preferenceKey;

    public static void initialise(CaravanApp app) {
        appContext = app.getApplicationContext();
        preferenceKey = appContext.getString(R.string.preference_key);
    }

    public static void putInSharedPreferences(String key, String value){
        SharedPreferences sharedPref = appContext.getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void putInSharedPreferences(String key, boolean value){
        SharedPreferences sharedPref = appContext.getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void putInSharedPreferences(String key, float value){
        SharedPreferences sharedPref = appContext.getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static void putInSharedPreferences(String key, int value){
        SharedPreferences sharedPref = appContext.getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static SharedPreferences getSharedPreferences() {
        return appContext.getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
    }

    public static String getString(String key, String defValue) {
        return getSharedPreferences().getString(key, defValue);
    }

    public static int getInteger(String key, int defValue) {
        return getSharedPreferences().getInt(key,defValue);
    }
    public static void removeFromSharedPreferences(String key) {
        SharedPreferences sharedPref = appContext.getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(key);
        editor.apply();
    }

    public static void clearSharedPreferences() {
        getSharedPreferences().edit().clear().apply();
    }
}
