package com.peppertap.caravan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peppertap.caravan.R;


public class CartFragment extends BaseFragment{
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
