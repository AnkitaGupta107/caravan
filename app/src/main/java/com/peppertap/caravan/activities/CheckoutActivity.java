package com.peppertap.caravan.activities;

import android.os.Bundle;
import android.view.MenuItem;

import com.peppertap.caravan.R;

/**
 */
public class CheckoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialise();
        setContentView(R.layout.checkout_detail);
    }

    @Override
    protected boolean useTabInAppBar() {
        return false;
    }

    @Override
    protected boolean useNavDrawer() {
        return false;
    }

    @Override
    protected void configureToolbar() {
        getSupportActionBar().setTitle("Review Cart");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void configureAppBarTabs() {

    }

    @Override
    protected void setUpNavigationView() {

    }

    @Override
    protected void onFabButtonClick() {

    }

    @Override
    protected boolean useFabButton() {
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        return false;
    }
}
