package com.peppertap.caravan.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peppertap.caravan.R;
import com.peppertap.caravan.adapters.SearchResultsAdapter;
import com.peppertap.caravan.views.DividerDecoration;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by samvedana on 19/12/15.
 */
public class ShopFragment extends BaseFragment {
    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    SearchResultsAdapter mAdapter;

    @Override
    protected void setUpContentView() {
        fragment_layout_res_id = R.layout.shop_fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new SearchResultsAdapter(globalApplication, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerDecoration(getResources().getDrawable(R.drawable.item_divider)));
        return view;
    }

}
