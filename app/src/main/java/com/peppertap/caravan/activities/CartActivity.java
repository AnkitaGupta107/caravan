package com.peppertap.caravan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

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

    public void sendMessage(View view)
    {
        Intent intent = new Intent(this, CheckoutActivity.class);
        startActivity(intent);
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
