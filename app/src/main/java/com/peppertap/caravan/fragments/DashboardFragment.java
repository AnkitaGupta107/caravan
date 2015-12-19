package com.peppertap.caravan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.peppertap.caravan.R;

import butterknife.InjectView;

/**
 * Created by samvedana on 19/12/15.
 */
public class DashboardFragment extends BaseFragment {
/*
    @InjectView(R.id.order_item_order_id)
    TextView order_id;
    @InjectView(R.id.order_item_order_date)
    TextView order_date;
    @InjectView(R.id.order_list)
    ListView orderList;
    @InjectView(R.id.payment_due)
    TextView paymentsDue;
*/
    @Override
    protected void setUpContentView() {
        fragment_layout_res_id = R.layout.dashboard;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initiate();
        return view;
    }

    private void initiate() {
        //initiate the data, adapter etc here
    }
}
