/**
 * Created by samvedana on 19/12/15.
 */

package com.peppertap.caravan;


import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.peppertap.caravan.DAOHelpers.DbWrapper;
import com.peppertap.caravan.network.CaravanApi;
import com.peppertap.caravan.utils.AppPreferenceUtils;

import de.greenrobot.event.EventBus;
import timber.log.Timber;

public class CaravanApp extends Application {
    private CaravanApi api = null;
    private RequestQueue mRequestQueue;
    private AppData appData;

    private DbWrapper wrapper = null;

    @Override
    public void onCreate() {
        Timber.plant(new Timber.DebugTree());
        Timber.d("onCreate");
        super.onCreate();

        initialise();
    }

    public void initialise() {
        AppPreferenceUtils.initialise(this);
        appData = new AppData();

        //EventBus.getDefault().register(this);
    }

    public CaravanApi getApi() {
        if (api == null) {
            api = new CaravanApi(this);
        }
        return api;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this);
        }
        return mRequestQueue;
    }

    public DbWrapper getDbWrapper() {
        if (wrapper == null) {
            wrapper = new DbWrapper(this);
        }
        return wrapper;
    }

    public AppData getAppData() {
        return appData;
    }

    public void clearAppData() {
        appData.clearAppData();
    }
}
