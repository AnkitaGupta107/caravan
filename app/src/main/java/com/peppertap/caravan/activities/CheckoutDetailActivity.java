package com.peppertap.caravan.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.peppertap.caravan.R;
import com.peppertap.caravan.fragments.SlotSelectionFragment;

import butterknife.InjectView;
import butterknife.OnClick;

public class CheckoutDetailActivity extends BaseActivity
    implements SlotSelectionFragment.OnFragmentInteractionListener {

    @InjectView(R.id.slot)
    LinearLayout slotLayout;
    @InjectView(R.id.asap)
    LinearLayout asapLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_detail);
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

    protected boolean useTabInAppBar() {
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    @OnClick(R.id.slot)
    public void showRepeatOptions() {
        DialogFragment fr = new SlotSelectionFragment();
        fr.show(getFragmentManager(), "SlotSelection");
    }

    @OnClick(R.id.proceed_btn)
    public void placeOrder() {
        Intent intent = new Intent(this, CheckoutConfirmActivity.class);
        startActivity(intent);
    }

}
