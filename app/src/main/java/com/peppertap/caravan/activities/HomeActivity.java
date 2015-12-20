package com.peppertap.caravan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.peppertap.caravan.DAOHelpers.DbHelpers;
import com.peppertap.caravan.R;
import com.peppertap.caravan.cartHelpers.PrimaryCartHelper;
import com.peppertap.caravan.events.CartEvents;
import com.peppertap.caravan.events.DataEvents;

import de.greenrobot.event.EventBus;

/**
 * Created by samvedana on 19/12/15.
 */
public class HomeActivity extends TabbedActivity {
    PrimaryCartHelper primaryCartHelper;

    LayoutInflater mInflator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        primaryCartHelper = PrimaryCartHelper.getInstance(getApplicationContext());
        mInflator = getLayoutInflater();
    }

    @Override
    public void onResume() {
        super.onResume();
        /*
            Following piece of code will clear the DaoSession and fetch updated values from the db
            This has been done to do away with the db caching errors
         */
        DbHelpers.getInstance(getGlobalApplication()).clearDaoSession();
        PrimaryCartHelper.updateInstance(getApplicationContext());

        EventBus.getDefault().post(new CartEvents.ShowCartFragment());
    }

    @Override
    protected void onFabButtonClick() {
        startActivity(new Intent(this, CartActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();

        switch (id) {
            case R.id.action_refresh:
                handleRefresh();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    public void handleRefresh() {
        EventBus.getDefault().post(new DataEvents.refreshEvent(selectedTabPosition));
    }

    public void showToastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
