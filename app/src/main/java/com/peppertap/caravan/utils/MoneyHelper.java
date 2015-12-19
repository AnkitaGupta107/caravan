package com.peppertap.caravan.utils;

import com.google.gson.JsonObject;

/**
 * Created by samvedana on 11/11/14.
 */
public class MoneyHelper {
    private static final String TAG = "MoneyHelper";

    public static String toMoney(float value) {
        return String.format("%.1f", value);
    }

    public static String toMoneyTwoDecimals(float value) {
        return String.format("%.2f", value);
    }

    public static String toMoney(String value) {
        return String.format("%.1f", new Float(value));
    }

    public static String toMoneyWithoutDecimals(float value){
        return String.format("%.0f", value);
    }

    public static String toMoneyWithoutDecimals(String value){
        return String.format("%.0f", new Float(value));
    }

    public static String stripRupeeSign(String rupeeStr) {
        int trimLen = "\u20B9".length();
        return rupeeStr.substring(trimLen);
    }

    public static float stringToFloat(String value){
        try{
            return new Float(value);
        }catch (NumberFormatException e){
            return 0;
        }
    }
    public static float getPrice(JsonObject current_item, float mDiscount) {
        float amount = current_item.get("sub_total").getAsFloat();
        float shipping = current_item.get("shipping").getAsFloat();
        float cash_points;
        if (!current_item.get("cash_points_applied").isJsonNull()) {
            cash_points = current_item.get("cash_points_applied").getAsFloat();
        } else {
            cash_points = 0;
        }


        return amount + shipping - cash_points - mDiscount;
    }
}
