package com.peppertap.caravan.DAOHelpers;

import android.util.Log;
import android.util.Pair;

import com.peppertap.caravan.CaravanApp;

import java.util.List;

import pepperTap.DaoMaster;
import pepperTap.DaoSession;

/**
 * Created by samvedana on 14/3/15.
 */
public class DbHelpers {
    DbWrapper wrapper;

    private static DbHelpers instance = null;

    private DbHelpers(CaravanApp app) {
        app.getDbWrapper();
    }

    public static DbHelpers getInstance(CaravanApp app){
        if(instance == null) {
            instance = new DbHelpers(app);
        }
        return instance;
    }

    public List<Pair<String, String>> getCurrentDbNames() {
        Log.d("DbHelpers", "in getCurrentDbNames");
        List<Pair<String, String>> namesData = wrapper.getDb().getAttachedDbs();
        for (Pair<String, String> pair : namesData) {
            Log.d("DbHelpers", "<" + pair.first + ", " + pair.second + ">");
        }
        return namesData;
    }

    public void clearDb() {
        DaoMaster.dropAllTables(wrapper.getDb(), true);
        DaoMaster.createAllTables(wrapper.getDb(), false);
    }

    public void closeDb() {
        wrapper.getDb().close();
    }

    public void refreshDbMembers() {
        wrapper.generateMembers(true);
    }

    public void clearDaoSession() {
        DaoSession session = wrapper.getDaoSession();
        if (session != null) {
            session.clear();
        }
    }
}
