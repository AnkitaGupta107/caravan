package com.peppertap.caravan.activities;

import android.os.Bundle;
import android.view.MenuItem;

import com.peppertap.caravan.R;

/**
 * Created by KhushbooGupta on 12/19/15.
 */
public class CartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

    }

    @Override
    protected void configureToolbar() {

    }

    @Override
    protected void configureAppBarTabs() {

    }

    @Override
    protected boolean useFabButton(){
        return false;
    }
    protected boolean useTabInAppBar() {
        return false;
    }
    @Override
    protected void setUpNavigationView() {

    }

    @Override
    protected void onFabButtonClick() {

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
