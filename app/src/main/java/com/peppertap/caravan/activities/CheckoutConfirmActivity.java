package com.peppertap.caravan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.peppertap.caravan.R;

/**
 * Created by KhushbooGupta on 12/19/15.
 */
public class CheckoutConfirmActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_order_confirmation);
    }


    @Override
    protected void configureToolbar() {

    }

    @Override
    protected void configureAppBarTabs() {

    }

    public void order_detail(View view)
    {
        Intent intent = new Intent(this, OrderDetailActivity.class);
        startActivity(intent);
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
