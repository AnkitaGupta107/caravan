package com.peppertap.caravan.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.peppertap.caravan.R;
import com.peppertap.caravan.events.UIEvents;
import com.peppertap.caravan.fragments.SlotSelectionFragment;

import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class CheckoutDetailActivity extends BaseActivity
    implements SlotSelectionFragment.OnFragmentInteractionListener {

    @InjectView(R.id.slot)
    LinearLayout slotLayout;
    @InjectView(R.id.asap)
    LinearLayout asapLayout;

    @InjectView(R.id.slot_name)
    TextView frequency;
    @InjectView(R.id.asap_title)
    TextView asapTitle;

    @InjectView(R.id.divider) View divider;

    private class DeliveryTime {
        private static final int NOW = 0;
        private static final int LATER = 1;
    }

    private int selDeliveryTimeLayout  = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_checkout_detail);
        selDeliveryTimeLayout = DeliveryTime.NOW;
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
        setDeliveryTime(DeliveryTime.LATER);
    }

    @OnClick(R.id.asap)
    public void onAsapClick() {
        setDeliveryTime(DeliveryTime.NOW);
    }

    private void setDeliveryTime(int deliveryTime) {
        switch (deliveryTime) {
            case DeliveryTime.NOW:
                switchOptionUiAspect(DeliveryTime.NOW);
                selDeliveryTimeLayout = DeliveryTime.NOW;
                break;
            case DeliveryTime.LATER:
                //get delivery slots to show from server
                DialogFragment fr = new SlotSelectionFragment();
                fr.show(getFragmentManager(), "SlotSelection");
                break;
        }
    }

    @OnClick(R.id.proceed_btn)
    public void placeOrder() {
        Intent intent = new Intent(this, CheckoutConfirmActivity.class);
        startActivity(intent);
    }

    public void onEventMainThread(UIEvents.OrderFrequencyChosen e){
        if (e.getRepetitionFrequency() != null) {
            frequency.setText(e.getRepetitionFrequency());

            switchOptionUiAspect(DeliveryTime.LATER);
            selDeliveryTimeLayout = DeliveryTime.LATER;

            showDividerIfNeeded();
        }
    }

    private void showDividerIfNeeded() {
        if (selDeliveryTimeLayout == -1) {
            divider.setVisibility(View.VISIBLE);
        }
        else {
            divider.setVisibility(View.GONE);
        }
    }

    private void switchOptionUiAspect(int selection) {
        int whiteColor = getResources().getColor(R.color.white);
        int blackColor = getResources().getColor(R.color.black);

        Drawable leftSelected = getResources().getDrawable(R.drawable.checkout_left_active_btn);
        Drawable rightSelected = getResources().getDrawable(R.drawable.checkout_right_active_btn);

        if (selection == DeliveryTime.NOW) {
            asapTitle.setTextColor(whiteColor);
            asapLayout.setBackgroundDrawable(leftSelected);

            frequency.setTextColor(blackColor);
            frequency.setText("Repeat");
            slotLayout.setBackgroundDrawable(null);
        }
        else {
            asapTitle.setTextColor(blackColor);
            asapLayout.setBackgroundDrawable(null);

            frequency.setTextColor(whiteColor);
            slotLayout.setBackgroundDrawable(rightSelected);
        }

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
