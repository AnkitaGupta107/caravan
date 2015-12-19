package com.peppertap.caravan.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.LinearLayout;

import com.peppertap.caravan.CaravanApp;
import com.peppertap.caravan.R;

import java.util.Stack;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by samvedana on 05/11/15.
 */
public abstract class BaseActivity extends MinimalActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    /*
    * Inspiration - http://mateoj.com/2015/06/21/adding-toolbar-and-navigation-drawer-all-activities-android/
    */
    private Stack<String> fragmentTagsStack = new Stack<String>();

    @InjectView(R.id.toolbar)
    protected android.support.v7.widget.Toolbar toolbar;
    @InjectView(R.id.navigationView)
    protected NavigationView navigationView;
    @InjectView(R.id.drawer_layout)
    protected DrawerLayout mDrawerLayout;
    @InjectView(R.id.tabs)
    protected TabLayout mTabLayout;
    @InjectView(R.id.app_bar)
    protected AppBarLayout appBarLayout;

    @InjectView(R.id.fab_button)
    protected FloatingActionButton mFabButton;
    @InjectView(R.id.coordinator_layout)
    protected CoordinatorLayout coordinatorLayout;

    protected static final int NONE = 0;

    protected int activity_layout_res_id = NONE;
    protected int fragment_replacement_content_view_id = NONE;
    protected ActionBarDrawerToggle mDrawerToggle;

    @Override
    public void setContentView(int layoutResID) {
        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);

        if (layoutResID != NONE) {
            LinearLayout activityContainer = (LinearLayout) fullView.findViewById(R.id.activity_content);
            getLayoutInflater().inflate(layoutResID, activityContainer, true);
            activity_layout_res_id = layoutResID;
        }

        super.setContentView(fullView);

        ButterKnife.inject(this);

        if (!useNavDrawer()) {
            fullView.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, 0, 0);
            mDrawerLayout.setDrawerListener(mDrawerToggle);
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            setUpNavigationView();
        }

        //sanity check
        if (appBarInUse()) {
            if (useToolbar()) {
                setSupportActionBar(toolbar);
                configureToolbar();
            }
            else {
                toolbar.setVisibility(View.GONE);
            }
            if (useTabInAppBar()) {
                mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                configureAppBarTabs();
            }
            else {
                mTabLayout.setVisibility(View.GONE);
            }
        }
        else {
            appBarLayout.setVisibility(View.GONE);
        }



        if(useFabButton()) {
            mFabButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onFabButtonClick();
                }
            });
        }else{
            mFabButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mDrawerToggle != null)
            mDrawerToggle.syncState();
    }

    private boolean appBarInUse() {
        return ((useToolbar() || useTabInAppBar()) && useAppBar());
    }

    protected boolean useAppBar() {
        return true;
    }

    protected boolean useFabButton(){
        return true;
    }
    protected boolean useToolbar() {
        return true;
    }

    protected boolean useNavDrawer() {
        return true;
    }

    protected boolean useTabInAppBar() {
        return true;
    }

    abstract protected void configureToolbar();

    abstract protected void configureAppBarTabs();

    abstract protected void setUpNavigationView();

    abstract protected void onFabButtonClick();

    protected void setFragmentReplacementViewId(int fragmentReplacementViewId) {
        fragment_replacement_content_view_id = fragmentReplacementViewId;
    }

    protected void replaceContentFragment(Fragment frag, String tag, boolean addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (fragment_replacement_content_view_id == NONE) {
            ft.replace(R.id.activity_content, frag, tag);
        } else {
            ft.replace(fragment_replacement_content_view_id, frag, tag);
        }

        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
        pushToFragmentStack(tag);
    }

    protected void setContentFragment(Fragment frag, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentById(R.id.activity_content) == null) {
            FragmentTransaction ft = fm.beginTransaction();
            if (fragment_replacement_content_view_id == NONE) {
                ft.add(R.id.activity_content, frag, tag);
            } else {
                ft.add(fragment_replacement_content_view_id, frag, tag);
            }
            ft.commit();
            pushToFragmentStack(tag);
        }
    }

    /* fragment tag stack related methods */
    protected String getCurrentTag() {
        String tag = null;
        if (!fragmentTagsStack.isEmpty()) {
            tag = fragmentTagsStack.peek();
        }
        return tag;
    }

    protected void clearFragmentStack() {
        fragmentTagsStack.clear();
    }

    protected void pushToFragmentStack(String tag) {
        fragmentTagsStack.push(tag);
    }

    protected String popFragmentStack() {
        if (!fragmentTagsStack.isEmpty()) {
            return fragmentTagsStack.pop();
        } else {
            return null;
        }
    }

    public CoordinatorLayout getCoordinatorLayout() {
        return coordinatorLayout;
    }

    public CaravanApp getGlobalApplication() {
        return globalApplication;
    }

}
