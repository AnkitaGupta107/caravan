package com.peppertap.caravan.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.peppertap.caravan.R;

public class OrderDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details);
        final Button modify = (Button) findViewById(R.id.orderDetail_modifyBtn);
        final Context context = getApplicationContext();
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast =Toast.makeText(context, "Your order has been paused. Come back later to restart.", Toast.LENGTH_LONG);
                toast.show();
                modify.setText(R.string.resumeBtn);
            }
        });
        final Button stopBtn = (Button) findViewById(R.id.orderDetail_cancelBtn);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast =Toast.makeText(context, "Your order has been stopped.", Toast.LENGTH_LONG);
                toast.show();
                modify.setEnabled(false);
                modify.setTextColor(getResources().getColor(R.color.white));
            }
        });
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

    protected boolean useTabInAppBar() {
        return false;
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
