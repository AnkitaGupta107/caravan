package com.peppertap.caravan.DAOHelpers;

import android.database.sqlite.SQLiteDatabase;


import com.peppertap.caravan.CaravanApp;
import com.peppertap.caravan.cartHelpers.PrimaryCartHelper;

import pepperTap.CartDao;
import pepperTap.DaoMaster;
import pepperTap.DaoSession;
import pepperTap.LineItemDao;

/**
 * Created by samvedana on 6/4/15.
 */
public class DbWrapper {
    CaravanApp application;

    private UpgradeHelper helper;
    private SQLiteDatabase db;
    private DaoSession daoSession;
    private CartDao cartDao;
    private LineItemDao lineItemDao;

    public DbWrapper(CaravanApp app) {
        application = app;
        generateMembers(false);
    }

    public void generateMembers(boolean refresh) {
        if (helper != null) {
            //need to refresh everything
            helper.close();
        }

        helper = new UpgradeHelper(application, "example-db", null);
        if (db != null) {
            db.close();
        }
        db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

        cartDao = daoSession.getCartDao();
        lineItemDao = daoSession.getLineItemDao();
        if (refresh) {
            PrimaryCartHelper.updateInstance(application);
        }
    }

    public UpgradeHelper getHelper() {
        return helper;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public CartDao getCartDao() {
        return cartDao;
    }

    public LineItemDao getLineItemDao() {
        return lineItemDao;
    }
}
