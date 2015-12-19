package com.peppertap.caravan.network;

import com.peppertap.caravan.CaravanApp;

/**
 * Created by samvedana on 17/11/15.
 */
public class CaravanApi {
    public static final long DEFAULT_CACHE_LIMIT_IN_SECONDS = 300;

    CaravanApp application;

    public CaravanApi(CaravanApp app) {
        this.application = app;
    }

}
