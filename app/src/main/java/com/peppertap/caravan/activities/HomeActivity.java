package com.peppertap.caravan.activities;

/**
 * Created by samvedana on 19/12/15.
 */
public class HomeActivity extends TabbedActivity {

    @Override
    protected void setActivityType() {
        activityType = ActivityType.HOME;
    }

    @Override
    protected void onFabButtonClick() {
        //todo take to cart
    }
}
