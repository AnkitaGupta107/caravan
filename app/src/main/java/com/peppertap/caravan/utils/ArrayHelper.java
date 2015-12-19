package com.peppertap.caravan.utils;

import java.util.ArrayList;

/**
 * Created by samvedana on 16/12/15.
 */
public class ArrayHelper {

    public static ArrayList<String> getStringArrayList(ArrayList<Long> ids) {
        ArrayList<String> result = new ArrayList<>();
        for (long id : ids) {
            result.add(String.valueOf(id));
        }
        return result;
    }

    public static ArrayList<Long> getLongArrayList(ArrayList<String> ids) {
        ArrayList<Long> result = new ArrayList<>();
        for (String id : ids) {
            result.add(Long.valueOf(id));
        }
        return result;
    }
}
