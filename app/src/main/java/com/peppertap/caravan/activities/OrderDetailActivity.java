package com.peppertap.caravan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.peppertap.caravan.R;

public class OrderDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_orders, menu);
        return true;
    }


    @Override
    protected void configureToolbar() {

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
    protected boolean useFabButton(){
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

//    private void updateBackIconState() {
//        if(mActionBarBackIcon== MaterialMenuDrawable.IconState.X){
//            (ShopActivity) getActivity();.setNavIconStateToCross();
//        }else{
//            getParentShopActivity().setNavIconStateToArrow();
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
