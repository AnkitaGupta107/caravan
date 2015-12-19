package com.peppertap.caravan.utils;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by subodh on 23/9/14.
 */

public class JsonHelper {
    private static final String TAG = "JsonHelper";

    public static ArrayList<JsonObject> convertJsonArrayToArrayListJsonObject(JsonArray arr) {
        ArrayList<JsonObject> objectArrayList = new ArrayList<JsonObject>();
        for (int i=0; i<arr.size(); i++){
            objectArrayList.add(arr.get(i).getAsJsonObject());
        }
        return objectArrayList;
    }

    public static ArrayList<String> convertJsonArrayToArrayListString(JsonArray arr) {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i=0; i<arr.size(); i++){
            arrayList.add(arr.get(i).getAsString());
        }
        return arrayList;
    }

    public static JsonObject MapToJsonObject(Map<String, String> map) {
        JsonObject result = new JsonObject();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            result.addProperty(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static JsonObject StringToJsonObject(String objStr) {
        if(objStr != null) {
            JsonElement element;
            try {
                element = new JsonParser().parse(objStr);
                return element.getAsJsonObject();
            } catch (JsonSyntaxException e) {
                return null;
            }
        }else{
            return null;
        }
    }

    public static JsonArray StringToJsonArray(String arrStr) {
        if(arrStr != null) {
            JsonElement element;
            try {
                element = new JsonParser().parse(arrStr);
                return element.getAsJsonArray();
            }catch (JsonSyntaxException e){
                return null;
            }
        }else{
            return null;
        }
    }

    public static String getValueOrNone(JsonObject jsonObject, String key){
        try{
            return jsonObject.get(key).getAsString();
        }catch(Exception e){
            Log.e(TAG, key);
            return "NA";
        }
    }

    public static String getSearchText(JsonObject object){
        String address_line = getValueOrNone(object, "addressLine");
        String area = getValueOrNone(object, "area");
        String city = getValueOrNone(object, "city");
        String text = address_line;

        if (!text.equals("")) text += ", ";
        if (!area.equals("")) text += area + ", ";
        text += city;

        return text;
    }
}
