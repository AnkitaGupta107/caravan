package com.peppertap.caravan.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.JsonObject;
import com.peppertap.caravan.CaravanApp;
import com.peppertap.caravan.R;
import com.peppertap.caravan.cartHelpers.PrimaryCartHelper;
import com.peppertap.caravan.data.ProductHelper;
import com.peppertap.caravan.fragments.ShopFragment;
import com.peppertap.caravan.network.Urls;
import com.peppertap.caravan.network.volley.MyJsonObjectRequest;
import com.peppertap.caravan.network.volley.ResponseListener;
import com.peppertap.caravan.utils.JsonHelper;
import com.peppertap.caravan.utils.RetryCallback;
import com.peppertap.caravan.viewHolders.LineItemHolder;
import com.peppertap.caravan.viewHolders.ProductHolder;
import com.peppertap.caravan.viewHolders.SearchBarHolder;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pepperTap.LineItem;
import timber.log.Timber;

/**
 * Created by samvedana on 19/12/15.
 */
public class CartItemAdapter extends RecyclerView.Adapter {
    CaravanApp globalApplication;

    private List<LineItem> data = new ArrayList<>();

    public CartItemAdapter(CaravanApp app) {
        globalApplication = app;
        data = PrimaryCartHelper.getInstance(globalApplication).getLineItems();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shop_line_item, parent, false);
        LineItemHolder holder = new LineItemHolder(listItemView, globalApplication);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((LineItemHolder) holder).bindData(data.get(position));
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }
}
