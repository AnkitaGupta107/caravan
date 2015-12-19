package com.peppertap.caravan.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.peppertap.caravan.activities.TabbedActivity;
import com.peppertap.caravan.data.TabTitleMap;
import com.peppertap.caravan.fragments.DashboardFragment;
import com.peppertap.caravan.fragments.ShopFragment;

import java.util.HashMap;
import java.util.List;

import timber.log.Timber;

/**
 * Created by Shine on 19/11/15.
 */
public class CaravanFragPagerAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;
    private List<String> tabTitles;
    TabbedActivity.ActivityType screenType;
    HashMap<Integer, Fragment> mPageReferenceMap = new HashMap<>();
    /* note - I don't precisely know why this is needed and a simple getItem(selectedItemPosition) from
    * main activity mPagerAdapter ought to have worked
    * but for some reason the lastRequestFiredUrl for ProductFragment was coming as null in that case even though
    * o inspecting CaravanFragPagerAdapter, the fragments had correct expected values for it
     *
     * For soln/approach referred - http://androidprofessionals.blogspot.in/2013/06/get-current-visible-fragment-page-in.html
     */

    public CaravanFragPagerAdapter(Context context, FragmentManager fragmentManager, TabbedActivity.ActivityType currentScreenType) {
        super(fragmentManager);
        screenType = currentScreenType;
        tabTitles = TabTitleMap.getTabTitleMap().get(currentScreenType);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (screenType) {
            //todo - add cases as needed
            case HOME:
                return getHomeScreenFragment(position);
            case CART:
                //return getCartScreenFragment(position);
                return new Fragment();
            default:
                return null;
        }
    }

    private Fragment getHomeScreenFragment(int position) {
        switch (position) {
            //todo
            case 0:
                //return DashboardFragment.getInstance();
                return new Fragment();
            case 1:
                return new ShopFragment();
            default:
                return null;
        }
    }

    private Fragment getCartScreenFragment(int position) {
        switch (position) {
            //todo
            case 0:
                //return DashboardFragment.getInstance();
            case 1:
                //return ShopFragment.getInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position >= 0 && position < getCount()) {
            return tabTitles.get(position);
        }
        else {
            Timber.e("position out of bounds [" + position +"], tabTitles = " + tabTitles.toString());
            return "";
        }
    }
}
