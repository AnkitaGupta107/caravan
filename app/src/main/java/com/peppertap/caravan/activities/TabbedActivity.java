package com.peppertap.caravan.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.peppertap.caravan.BuildConfig;
import com.peppertap.caravan.R;
import com.peppertap.caravan.adapters.CaravanFragPagerAdapter;
import com.peppertap.caravan.fragments.DashboardFragment;
import com.peppertap.caravan.fragments.ShopFragment;

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

    FrameLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialise();
        setContentView(R.layout.activity_main);


        mContainer = (FrameLayout) findViewById(R.id.container);
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

        //Check to see which item was being clicked and perform appropriate action
        switch (menuItem.getItemId()) {
            //Replacing the main content with ContentFragment Which is our Inbox View;
            case R.id.dashboard:
                mViewPager.setCurrentItem(0);
                return true;
            case R.id.orders:
                startActivity(new Intent(this,OrdersActivity.class));
                return true;
            case R.id.contact:
                callToCustomerCare();
                return true;
            case R.id.faq:
                startActivity(new Intent(this,FaqActivity.class));
                return true;
            case R.id.profile:
                return false;
            case R.id.logout:
                globalApplication.clearAppData();
                startActivity(new Intent(this,HomeActivity.class));
                return true;
            default:
                return false;
        }


    }

    @Override
    protected void configureToolbar() {
        getSupportActionBar().setTitle("Home");
    }

    public void callToCustomerCare() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:0123456789"));
        startActivity(intent);
    }
}
