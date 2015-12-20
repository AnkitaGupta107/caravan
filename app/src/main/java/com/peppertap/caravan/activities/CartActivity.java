package com.peppertap.caravan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.peppertap.caravan.R;
import com.peppertap.caravan.adapters.CartItemAdapter;
import com.peppertap.caravan.cartHelpers.PrimaryCartHelper;
import com.peppertap.caravan.events.PrimaryCartDbEvents;
import com.peppertap.caravan.utils.MoneyHelper;
import com.peppertap.caravan.views.DividerDecoration;

import org.w3c.dom.Text;

import butterknife.InjectView;

/**
 * Created by KhushbooGupta on 12/19/15.
 */
public class CartActivity extends BaseActivity {

    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @InjectView(R.id.cart_body_subtotal)
    TextView subTotal;
    @InjectView(R.id.items)
    TextView items;

    CartItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        PrimaryCartHelper helper = PrimaryCartHelper.getInstance(globalApplication);
        subTotal.setText(MoneyHelper.toMoneyWithoutDecimals(helper.getSubTotal()));
        items.setText(String.valueOf(helper.getLineItems().size()));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CartItemAdapter(globalApplication);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerDecoration(getResources().getDrawable(R.drawable.item_divider)));

    }

    @Override
    protected void configureToolbar() {

    }

    public void sendMessage(View view)
    {
        Intent intent = new Intent(this, CheckoutDetailActivity.class);
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
