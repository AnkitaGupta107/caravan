package com.peppertap.caravan.DAOHelpers;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import pepperTap.DaoMaster;

/**
 * Created by samvedana on 24/11/14.
 */
public class UpgradeHelper extends DaoMaster.OpenHelper{

    private static final String TAG = "Upgrade Helper";

    public UpgradeHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "UpgradeHelper onUpgrade from " + oldVersion + " to " + newVersion);
        switch (newVersion) {
            default:
                return;
        }
    }
}
