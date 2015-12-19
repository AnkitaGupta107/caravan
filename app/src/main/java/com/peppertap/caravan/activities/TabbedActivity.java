package com.peppertap.caravan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.peppertap.caravan.BuildConfig;
import com.peppertap.caravan.R;
import com.peppertap.caravan.adapters.CaravanFragPagerAdapter;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import timber.log.Timber;

/**
 * Created by samvedana on 19/12/15.
 */
public abstract class TabbedActivity extends BaseActivity {

    @InjectView(R.id.view_pager)
    ViewPager mViewPager;
    private CaravanFragPagerAdapter mPagerAdaptor;
    private ViewPager.OnPageChangeListener mPageChangeListener;

    //for activities using the standard appbar and navigation drawer look and functionality
    protected int navMenuId = NONE;
    int selectedTabPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialise();
        setContentView(R.layout.activity_main);
    }

    private void initialise() {
        navMenuId = R.menu.nav_main;
    }

    protected void configureAppBarTabs() {
        mPagerAdaptor = new CaravanFragPagerAdapter(this, getSupportFragmentManager());
        navigationView.getMenu().getItem(0).setChecked(true);

        mTabLayout.setTabsFromPagerAdapter(mPagerAdaptor);
        mViewPager.setAdapter(mPagerAdaptor);
        mTabLayout.setupWithViewPager(mViewPager);
        //todo create MPageChangeListener
        //mViewPager.addOnPageChangeListener(mPageChangeListener);
        selectedTabPosition = 0;
        //mPageChangeListener.onPageSelected(0);

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        mDrawerToggle.syncState();
    }

    protected void setUpNavigationView() {
        if (navMenuId != NONE) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(navMenuId);
            //navigationView.getMenu().getItem(0).setChecked(true);
            navigationView.setNavigationItemSelectedListener(this);

            TextView mPartnerName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.store_name);
            mPartnerName.setText(globalApplication.getAppData().getCustomerName());

            TextView mAppVersion = (TextView) navigationView.findViewById(R.id.app_info);
            mAppVersion.setText("v" + BuildConfig.VERSION_NAME);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        //Checking if the item is in checked state or not, if not make it in checked state
        //might need to be handled by us instead of working out of the box
        if (menuItem.isChecked())
            menuItem.setChecked(false);
        else
            menuItem.setChecked(true);

        //Closing drawer on item click
        mDrawerLayout.closeDrawers();
        Map<String, String> params = new HashMap<String, String>();
/*
        //Check to see which item was being clicked and perform appropriate action
        switch (menuItem.getItemId()) {
            //Replacing the main content with ContentFragment Which is our Inbox View;
            case R.id.orders:

                if (globalApplication.getAppData().getAppType() == PartnersApp.AppType.STORE) {
                    changeScreenType(TabbedActivityScreenType.STORE_APP_ORDERS_SCREEN);
                } else {
                    changeScreenType(TabbedActivityScreenType.CHAIN_APP_HOME_SCREEN);
                }
                params.put(AnalyticsStrings.DrawerParams.MENU, "orders");
                EventBus.getDefault().post(new DataEvents.PostFlurryEvent(new Event(AnalyticsStrings.ACTION_DRAWER, params)));
                return true;
            case R.id.products:
                if (globalApplication.getAppData().getAppType() == PartnersApp.AppType.STORE) {
                    changeScreenType(TabbedActivityScreenType.STORE_APP_PRODUCTS_SCREEN);
                }
                params.put(AnalyticsStrings.DrawerParams.MENU, "products");
                EventBus.getDefault().post(new DataEvents.PostFlurryEvent(new Event(AnalyticsStrings.ACTION_DRAWER, params)));
                return true;
            case R.id.stores:
                changeScreenType(TabbedActivityScreenType.CHAIN_APP_STORE_SCREEN);
                params.put(AnalyticsStrings.DrawerParams.MENU, "stores");
                EventBus.getDefault().post(new DataEvents.PostFlurryEvent(new Event(AnalyticsStrings.ACTION_DRAWER, params)));
                return true;
            case R.id.change_password:
                startActivity(new Intent(this,ChangePasswordActivity.class));
                return true;
            case R.id.contact:
                params.put(AnalyticsStrings.DrawerParams.MENU, "contact");
                callToCustomerCare();
                EventBus.getDefault().post(new DataEvents.PostFlurryEvent(new Event(AnalyticsStrings.ACTION_DRAWER, params)));
                return true;
            case R.id.logout:
                globalApplication.clearAppData();
                globalApplication.clearCookieCache();
                params.put(AnalyticsStrings.DrawerParams.MENU, "logout");
                EventBus.getDefault().post(new DataEvents.PostFlurryEvent(new Event(AnalyticsStrings.ACTION_DRAWER, params)));
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return false;
        }
        */

        return false;
    }

    @Override
    protected void configureToolbar() {
        getSupportActionBar().setTitle("Home");
    }
}
